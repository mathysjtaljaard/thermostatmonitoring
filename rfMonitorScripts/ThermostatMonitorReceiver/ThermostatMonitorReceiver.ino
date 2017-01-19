/* 
 * NOTE: This starter code is from https://github.com/TMRh20/RF24/blob/master/examples/GettingStarted/GettingStarted.ino
 * Getting Started example sketch for nRF24L01+ radios
 * This is a very basic example of how to send data from one node to another
 * Updated: Dec 2014 by TMRh20
 */

#include <SPI.h>
#include "RF24.h"

bool isDebug = false;

//TODO: add this to the startup script to track the startup and shotdown times
struct DataCollection {
 int fanOn;
 int heatOn;
 int coolOn;
 int auxHeatOn;
 float temp;
 int statusCode;
 int valuesLogged;
};

/* Hardware configuration: Set up nRF24L01 radio on SPI bus plus pins 7 & 8 */
RF24 radio(7,8);
/**********************************************************/

byte moduleAddresses[][6] = {"send1","recd1"};

// Used to control whether this node is sending or receiving
bool role = 0;

void setup() {
  Serial.begin(115200);

  radio.begin();

  // Set the PA Level low to prevent power supply related issues since this is a
  // getting_started sketch, and the likelihood of close proximity of the devices. RF24_PA_MAX is default.
  radio.setPALevel(RF24_PA_LOW);

  // Open a writing and reading pipe on each radio, with opposite addresses
  radio.openWritingPipe(moduleAddresses[0]);
  radio.openReadingPipe(1,moduleAddresses[1]);

  // Start the radio listening for data
  radio.startListening();
  Serial.println("Receiving Module Config completed");
}

void loop() {

  /****************** Receive and consume data ***************************/

  if ( role == 0 ) {
    
    if( radio.available()){
      // Variable for the received timestamp
      DataCollection receivedData = readData();
      sendResponse(receivedData);     
    }
  }

} // Loop

struct DataCollection readData() {
  DataCollection receivedData;
  
  while (radio.available()) {                                   // While there is data ready
    radio.read( &receivedData, sizeof(receivedData) );          // Get the payload
  }
     
  printData(receivedData);
  return receivedData;
}

void sendResponse(struct DataCollection receivedData) {
  DataCollection sendData;
  radio.stopListening();                                    // First, stop listening so we can talk  
  
  sendData.fanOn = receivedData.fanOn;
  sendData.heatOn = receivedData.heatOn;
  sendData.coolOn = receivedData.coolOn;
  sendData.auxHeatOn = receivedData.auxHeatOn;
  sendData.temp = receivedData.temp;
  sendData.statusCode = 200;
  sendData.valuesLogged = 1;

  if (isDebug) {
    printData(sendData);
  }
  
  radio.write( &sendData, sizeof(sendData) );              // Send the final one back.      
  radio.startListening();                                  // Now, resume listening so we catch the next packets.
}

void printData(struct DataCollection data) {
    String pipe = String(" | ");
    String fanStatusString = String("Fan On Status -> "); 
    String fanStatus= String(data.fanOn, DEC); 
    String fanCombo = String(fanStatusString + fanStatus + pipe);
    String HeatStatusString = String("Heat On Status -> ");
    String HeatStatus = String(data.heatOn, DEC);
    String HeatCombo = String(HeatStatusString + HeatStatus + pipe);
    String ACStatusString = String("Cooling On Status -> ");
    String ACStatus = String(data.coolOn, DEC);
    String ACCombo = String(ACStatusString + ACStatus + pipe);
    String AuxStatusString = String("Aux Heat On Status -> ");
    String AuxStatus = String(data.auxHeatOn);
    String AuxCombo = String(AuxStatusString + AuxStatus + pipe); 
    String TempReadingString = String("Temperature (F) -> ");
    String TempReading = String(data.temp, 3); 
    String TempCombo = String(TempReadingString + TempReading);
    String dataToWrite = String(fanCombo + HeatCombo +  ACCombo + AuxCombo + TempCombo);
    char *p = const_cast<char*>(dataToWrite.c_str());
    Serial.write(p);
}



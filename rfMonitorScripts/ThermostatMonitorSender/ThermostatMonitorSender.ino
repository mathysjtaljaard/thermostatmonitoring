
/* 
* NOTE: This starter code is from https://github.com/TMRh20/RF24/blob/master/examples/GettingStarted/GettingStarted.ino
* Getting Started example sketch for nRF24L01+ radios
* This is a very basic example of how to send data from one node to another
* Updated: Dec 2014 by TMRh20
*/

#include <SPI.h>
#include "RF24.h"
#include <OneWire.h>
#include <DallasTemperature.h>

#define ONE_WIRE_BUS 2

OneWire ourWire(ONE_WIRE_BUS);
DallasTemperature sensors(&ourWire);

const int fanOnPin = A0;
const int heatOnPin = A1;
const int acOnPin = A2;
const int auxHeatOnPin = A3;
const int SEND_STATUS_CODE = 100;

const boolean isDebug = true;

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
//TODO create include for the payload class to stop copying stuff over


byte moduleAddresses[][6] = {"send1","recd1"};

// Used to control whether this node is sending or receiving
bool role = 1;

void setup() {
  Serial.begin(115200);

  radio.begin();

  // Set the PA Level low to prevent power supply related issues since this is a
  // getting_started sketch, and the likelihood of close proximity of the devices. RF24_PA_MAX is default.
  radio.setPALevel(RF24_PA_LOW);
  
  // Open a writing and reading pipe on each radio, with opposite addresses
  
  radio.openWritingPipe(moduleAddresses[1]);
  radio.openReadingPipe(1,moduleAddresses[0]);  
  
  // Start the radio listening for data
  radio.startListening();
  sensors.begin();
  Serial.println("Sending Module Config completed");
}

void loop() {
  
/****************** Ping Out Role ***************************/  
   
  DataCollection dataToSend;
  dataToSend = collectData();
  sendData(dataToSend);
  delay(1500);
  
} // Loop

int isOn(byte pinNumber) {
  
  int sensorValue = analogRead(pinNumber);
  
  float voltage = sensorValue * (5.0/1023.0);   
  if (voltage > 4 ) {
    Serial.print("voltage on for Pin "); 
        Serial.println(pinNumber);
    return 1;
  } else {
    Serial.print("voltage off for Pin "); 
        Serial.println(pinNumber);
    return 0;
  }
}

struct DataCollection collectData() {
  DataCollection collectedData;
  
  if (isDebug) {
    Serial.println();
    Serial.print("Requesting temperature...");
  }
  
  sensors.requestTemperatures(); // Send the command to get temperatures
  collectedData.temp = sensors.getTempFByIndex(0);
  if (isDebug) {
    Serial.println("DONE");
    Serial.print("Device 1 (index 0) = ");
    Serial.print(sensors.getTempCByIndex(0));
    Serial.println(" Degrees C");
    Serial.print("Device 1 (index 0) = ");
    Serial.print(collectedData.temp);
    Serial.println(" Degrees F");
  }
    
  collectedData.fanOn = isOn(fanOnPin);
  collectedData.heatOn = isOn(heatOnPin);
  collectedData.coolOn = isOn(acOnPin);
  collectedData.auxHeatOn = isOn(auxHeatOnPin);   

  return collectedData;
}

void sendData(struct DataCollection dataToSend) {
  DataCollection receivedPayload;
  
  if (isDebug) {
    Serial.print("data sent to method");
    printData(dataToSend);  
  }  
  
  radio.stopListening();                                    // First, stop listening so we can talk.
          
  unsigned long start_time = micros();                             // Take the time, and send it.  This will block until complete
  dataToSend.statusCode = SEND_STATUS_CODE;
  
  if (!radio.write( &dataToSend, sizeof(dataToSend) )){
    Serial.println(F("failed"));
  } 
            
  radio.startListening();                                    // Now, continue listening
    
  unsigned long started_waiting_at = micros();               // Set up a timeout period, get the current microseconds
  boolean timeout = false;                                   // Set up a variable to indicate if a response was received or not
      
  while ( ! radio.available() ){                             // While nothing is received
    if (micros() - started_waiting_at > 500000 ){            // If waited longer than 200ms, indicate timeout and exit while loop
        timeout = true;
        break;
    }      
  }
          
  if ( timeout ){                                             // Describe the results
    Serial.println(F("Failed, response timed out."));
  } else {
    unsigned long got_time;                                 // Grab the response, compare, and send to debugging spew
    radio.read( &receivedPayload, sizeof(receivedPayload) );
    unsigned long end_time = micros();
    
    if (isDebug) {
      Serial.print(F("Sent "));
      Serial.print(start_time);
      Serial.print(F(", Round-trip delay "));
      Serial.print(end_time-start_time);
      Serial.println(F(" microseconds"));
      Serial.println(F(", Got response -> "));
      printData(receivedPayload);
    }
  }    
}

void printData(struct DataCollection data) {
  Serial.println("Printing Data");
  Serial.print("Is Fan on: ");
  Serial.println(data.fanOn);
  Serial.print("Is Heat on: ");
  Serial.println(data.heatOn);
  Serial.print("Is AC on: ");
  Serial.println(data.coolOn);
  Serial.print("Is Aux Heat on: ");
  Serial.println(data.auxHeatOn);
  Serial.print("Temperature (F): ");
  Serial.println(data.temp);
  Serial.print("Status Code: ");
  Serial.println(data.statusCode);
  Serial.print("Did Values get logged: ");
  Serial.println(data.valuesLogged);       
}

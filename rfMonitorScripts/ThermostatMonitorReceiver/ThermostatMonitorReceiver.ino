/* 
 * NOTE: This starter code is from https://github.com/TMRh20/RF24/blob/master/examples/GettingStarted/GettingStarted.ino
 * Getting Started example sketch for nRF24L01+ radios
 * This is a very basic example of how to send data from one node to another
 * Updated: Dec 2014 by TMRh20
 */

#include <SPI.h>
#include "RF24.h"

//TODO: Need to utilize this... but refactoring is your friend
//typedef enum {RECEIVER_MODULE = 0, SENDER_MODULE} moduleType; 

//TODO: add this to the startup script to track the startup and shotdown times
struct ModuleStatus {
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
    
    ModuleStatus receivedPayload, sendPayload;
    unsigned long got_time;

    if( radio.available()){
      // Variable for the received timestamp
      while (radio.available()) {                                   // While there is data ready
        radio.read( &receivedPayload, sizeof(receivedPayload) );             // Get the payload
      }
      Serial.println("Data read. Recieved data was:");
      Serial.print("Fan On Status -> ");
      Serial.println(receivedPayload.fanOn);
      Serial.print("Heat On Status -> ");    
      Serial.println(receivedPayload.heatOn);
      Serial.print("Cooling On Status -> ");
      Serial.println(receivedPayload.coolOn);
      Serial.print("Aux Heat On Status -> ");
      Serial.println(receivedPayload.auxHeatOn);
      Serial.print("Temperature (F) -> ");
      Serial.println(receivedPayload.temp);
      Serial.print("Status Code Received -> ");
      Serial.println(receivedPayload.statusCode);
      Serial.print("Values Logged Status-> ");
      Serial.println(receivedPayload.valuesLogged);   
 
      radio.stopListening();             // First, stop listening so we can talk  
      sendPayload = receivedPayload;
      sendPayload.statusCode=200;
      sendPayload.valuesLogged=1;
      radio.write( &sendPayload, sizeof(sendPayload) );              // Send the final one back.      
      radio.startListening();                                       // Now, resume listening so we catch the next packets.     

    }
  }

} // Loop




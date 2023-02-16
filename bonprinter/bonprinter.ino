#include "Adafruit_Thermal.h"
#include "SoftwareSerial.h"

#define TX_PIN 6 // Arduino TX pin -- RX on printer
#define RX_PIN 5 // Arduino RX pin -- TX on printer

SoftwareSerial printer_connection(RX_PIN, TX_PIN);
Adafruit_Thermal printer(&printer_connection);

void setup()
{  
  printer_connection.begin(9600);
  printer.begin();

  printer.setFont('B');
  printer.println("Hello, World!");
  printer.setFont('A');
  printer.println("Hello, World!");

  printer.inverseOn();
  printer.println(F("Good Bye, World!"));
  printer.inverseOff();

  printer.doubleHeightOn();
  printer.println(F("Large Text"));
  printer.doubleHeightOff();
}

void loop() { }

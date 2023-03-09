#include "Adafruit_Thermal.h"
#include "bonlogo.h"

#include "SoftwareSerial.h"
#define TX_PIN 6 // Arduino TX pin -- RX on printer
#define RX_PIN 5 // Arduino RX pin -- TX on printer

SoftwareSerial printer_connection(RX_PIN, TX_PIN);
Adafruit_Thermal printer(&printer_connection);

void setup()
{
  // This line is for compatibility with the Adafruit IotP project pack,
  // which uses pin 7 as a spare grounding point.  You only need this if
  // wired up the same way (w/3-pin header into pins 5/6/7):
  pinMode(7, OUTPUT); digitalWrite(7, LOW);

  printer_connection.begin(19200);
  printer.begin();

  // Print the 75x75 pixel logo in adalogo.h:
  printer.printBitmap(bonlogo_width, bonlogo_height, bonlogo_data);
  //  printer.setFont('A');
  //  printer.justify('C');
  //  printer.setSize('L');
  //  printer.boldOn();
  //  printer.doubleHeightOn();
  //  printer.println(F("    \nBinG\n"));
  //  printer.doubleHeightOff();
  //  printer.boldOff();
  //  printer.setSize('S');
  //  printer.println(F("Wijnhaven 107, Rotterdam"));
  //  printer.println(F("--------------------------------"));
  //  printer.justify('L');
  //  printer.println(F("Opgenomen bedrag: "));
  //  printer.println(F("IBAN: XXXXXXXXXXXXXXX-0979"));
  //  printer.println(F("Pasnummer: "));
  //  printer.println(F("Datum: \nTijd: "));
  //  printer.justify('C');
  //  printer.println(F("--------------------------------"));
  //  printer.setSize('L');
  //  printer.boldOn();
  //  printer.doubleHeightOn();
  //  printer.println(F("     \nBedankt!\n Tot ziens! :)\n   \n   \n"));
}

void loop() { }

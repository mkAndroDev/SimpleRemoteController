#include "DHT.h"
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>

const char* ssid = "INVISIBLE";
const char* password = "Zgadnij3006saM";

#define DHTPIN D1
#define DHTTYPE DHT22

DHT dht(DHTPIN, DHTTYPE);
ESP8266WebServer server(80);

float setTeperature = 0;
float setHumidity = 0;

void handleRoot() {
  server.send(200, "text/plain", "hello from esp8266!");
}

void handleNotFound(){
  String message = "File Not Found\n\n";
  message += "URI: ";
  message += server.uri();
  message += "\nMethod: ";
  message += (server.method() == HTTP_GET)?"GET":"POST";
  message += "\nArguments: ";
  message += server.args();
  message += "\n";
  for (uint8_t i=0; i<server.args(); i++){
    message += " " + server.argName(i) + ": " + server.arg(i) + "\n";
  }
  server.send(404, "text/plain", message);
}

void setup(void){
  Serial.begin(9600);
  WiFi.begin(ssid, password);
  Serial.println("");
  dht.begin();

  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  if (MDNS.begin("esp8266")) {
    Serial.println("MDNS responder started");
  }

  server.on("/", handleRoot);

  server.on("/getCurrentWeather", [](){
    float h = dht.readHumidity();
    float t = dht.readTemperature();
    server.send(200, "text/plain", "{temperature: " + String(t) + ", humidity: " + String(h) + "}");
  });

  server.on("/getCurrentSet", [](){
    server.send(200, "text/plain", "{temperature: " + String(setTeperature) + ", humidity: " + String(setHumidity) + "}");
  });

  server.on("/setTemperature/", [](){
    setTeperature = temperature;
    server.send(200, "text/plain", "{temperature: " + String(setTeperature) + ", humidity: " + String(setHumidity) + "}");
  });

  server.onNotFound(handleNotFound);

  server.begin();
  Serial.println("HTTP server started");
}

void loop(void){
  server.handleClient();
}


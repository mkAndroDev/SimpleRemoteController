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

int AIRING_PIN = 4;
int FURNACE_PIN = 3;

float temperatureToLaunch = 0;
float humidityToLaunch = 0;
boolean autoMode = false;
boolean airing = false;
boolean furnace = false;

void handleRoot() {
  server.send(200, "text/plain", "hello from esp8266!");
}

void handleNotFound() {
  String message = "File Not Found\n\n";
  message += "URI: ";
  message += server.uri();
  message += "\nMethod: ";
  message += (server.method() == HTTP_GET) ? "GET" : "POST";
  message += "\nArguments: ";
  message += server.args();
  message += "\n";
  for (uint8_t i = 0; i < server.args(); i++) {
    message += " " + server.argName(i) + ": " + server.arg(i) + "\n";
  }
  server.send(404, "text/plain", message);
}

void handlePutCurrentSet() {

  if (server.hasArg("plain") == false) {

    server.send(200, "text/plain", "Body not received");
    return;

  }

  autoMode = true;

  String message = "Body received:\n";
  message += server.arg("plain");
  message += "\n";

  server.send(200, "text/plain", message + " autoMode: " + String(autoMode));
}

void setup(void) {
  Serial.begin(9600);
  pinMode(AIRING_PIN, OUTPUT);
  pinMode(FURNACE_PIN, OUTPUT);
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

  server.on("/getCurrentWeather", []() {
    float currentHumidity = dht.readHumidity();
    float currentTemperature = dht.readTemperature();
    server.send(200, "text/plain", "{temperature: " + String(currentTemperature) + ", humidity: " + String(currentHumidity) + "}");
  });

  server.on("/putCurrentSet", handlePutCurrentSet);

  server.on("/getCurrentSet", []() {
    server.send(200, "text/plain", "{temperature: " + String(temperatureToLaunch) + ", humidity: " + String(humidityToLaunch) + "}");
  });

  server.on("/getAiring", []() {
    server.send(200, "text/plain", "{furnace: " + String(airing) + "}" );
  });

  server.on("/putAiring", []() {
    autoMode = false;
    airing = !airing;

    if (airing) {
      digitalWrite(AIRING_PIN, HIGH);
    } else {
      digitalWrite(AIRING_PIN, LOW);
    }

    server.send(200, "text/plain", "{furnace: " + String(airing) + "}");
  });

  server.on("/getFurnace", []() {
    server.send(200, "text/plain", "{furnace: " + String(furnace) + "}" );
  });

  server.on("/putFurnace", []() {
    autoMode = false;
    furnace = !furnace;

    if (furnace) {
      digitalWrite(FURNACE_PIN, HIGH);
    } else {
      digitalWrite(FURNACE_PIN, LOW);
    }

    server.send(200, "text/plain", "{furnace: " + String(furnace) + "}");
  });

  server.onNotFound(handleNotFound);

  server.begin();
  Serial.println("HTTP server started");
}

void loop(void) {
  server.handleClient();

  float currentHumidity = dht.readHumidity();
  float currentTemperature = dht.readTemperature();

  if (autoMode) {
    if (currentTemperature < temperatureToLaunch) {
      furnace = true;
      digitalWrite(FURNACE_PIN, HIGH);
    } else {
      furnace = false;
      digitalWrite(FURNACE_PIN, LOW);
    }

    if (currentHumidity > humidityToLaunch) {
      airing = true;
      digitalWrite(AIRING_PIN, HIGH);
    } else {
      airing = false;
      digitalWrite(AIRING_PIN, LOW);
    }
  }

}


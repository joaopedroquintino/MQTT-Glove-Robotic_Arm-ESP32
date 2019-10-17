#include <WiFi.h>
#include <PubSubClient.h>
#include <Servo.h>

#define ledState  LED_BUILTIN // Led para sinalizacao de dado recebido
#define ledWiFi   5  // Led para sinalizacao da conexao WiFi
#define ledBroker 18  // Led para sinalizacao da conexao com Broker MQTT
#define ledReceive   19  // Led para sinalizacao de recebimento de dado
#define servo1Pin    14  // Servo da base
#define servo2Pin    27  // Servo 1 do braco
#define servo3Pin    26  // Servo de alcar braco
#define servo4Pin    25  // Servo da garra
#define ID_MQTT "jpsq062"         // Id do dispositivo
#define TOPIC_SUBSCRIBE "b_1jpsq" // Id do pacote assinado

unsigned long delay_receive;
//Credenciais do WiFi
const char* SSID = "Link_One_420240";
const char* PASSWORD = "quintino";
WiFiClient wifiClient;

//Endereco e porta do MQTT Server
const char* BROKER_MQTT = "broker.hivemq.com";
int         BROKER_PORT = 1883;

PubSubClient MQTT(wifiClient);

Servo servo1, servo2, servo3, servo4;

//declaracao de funcoes utilizadas
void mantemConexoes();
void conectaWiFi();
void conectaMQTT();
void recebePacote(char* topic, byte* payload, unsigned int length);
void moveArm(int servo, int pos);


void setup() {
  pinMode(ledState, OUTPUT);
  pinMode(ledWiFi,OUTPUT);
  pinMode(ledBroker,OUTPUT);
  pinMode(ledReceive,OUTPUT); 
  servo1.attach(servo1Pin);
  servo2.attach(servo2Pin);
  servo3.attach(servo3Pin);
  servo4.attach(servo4Pin);
  Serial.begin(115200);

  conectaWiFi();
  MQTT.setServer(BROKER_MQTT, BROKER_PORT);
  MQTT.setCallback(recebePacote);

}

void loop() {
  mantemConexoes();
  MQTT.loop();
  if((millis() - delay_receive) > 100) digitalWrite(ledReceive, LOW);
}

void mantemConexoes(){
  if(!MQTT.connected()){
    conectaMQTT();
  }
  conectaWiFi();  // Se nao ha conexao WiFi, a conexao e refeita
}

void conectaWiFi(){
  if(WiFi.status() == WL_CONNECTED){
    digitalWrite(ledWiFi, HIGH);
    return;
  }


  WiFi.begin(SSID, PASSWORD);
  while(WiFi.status() != WL_CONNECTED){
    digitalWrite(ledWiFi, LOW);
    delay(200);
  }
}

void conectaMQTT(){
  while(!MQTT.connected()){
    digitalWrite(ledBroker, LOW);
    if(MQTT.connect(ID_MQTT)){
      MQTT.subscribe(TOPIC_SUBSCRIBE, 1);
      digitalWrite(ledBroker, HIGH);
    }
    else{
      digitalWrite(ledBroker,LOW);
      delay(5000);
    }
  }
}
void recebePacote( char* topic, byte* payload, unsigned int length){
  char msg[4];
  digitalWrite(ledReceive, HIGH);
  delay_receive = millis();
  for(int i = 0; i<length;i++)
  {
    msg[i] = (char)payload[i];
  }
  Serial.print("theta_x: ");
  Serial.print((int)msg[0]);Serial.print("\t");
  Serial.print("theta_y: ");
  Serial.print((int)msg[1]);Serial.print("\t");
  Serial.print("theta_z: ");
  Serial.print((int)msg[2]);Serial.print("\t");
  Serial.print("garra: ");
  Serial.println((int)msg[3]);
 
  moveArm(1,(int)msg[2]);
  moveArm(2,(int)msg[1]);
  moveArm(3,(int)msg[0]);
  moveArm(4,(int)msg[3]);
 
}

void moveArm(int servo,int pos){
  switch(servo)
  {
    case 1:
      servo1.write(map(pos, 45, 135, 15, 170));
      break;
    case 2:
      servo2.write(map(pos, 45, 135, 45, 135));
      break;
    case 3:
      servo3.write(map(pos, 45, 135, 30, 125));
      break;
    case 4:
      servo4.write(map(pos, 0, 127, 15, 45));
      break;
    default:
      break;
  } 
}

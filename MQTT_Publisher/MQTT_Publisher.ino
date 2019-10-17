#include <WiFi.h>
#include <PubSubClient.h>
#include <MPU6050.h>
#include <Wire.h>
#include <I2Cdev.h>

#define pinBotao1 12  // Pino do botao que sera lido 
#define ledState  LED_BUILTIN // Led para sinalizacao de leitura do botao
#define ledWiFi   14  // Led para sinalizacao da conexao WiFi
#define ledBroker 27  // Led para sinalizacao da conexao com Broker MQTT
#define ledSend   26  // Led para sinalizacao de publicacao de dado
#define flex      34  // Pino para sensor flex
  
#define ID_MQTT "jpsq_061"        // Id do dispositivo
#define TOPIC_PUBLISH "b_1jpsq"   // Topico do pacote enviado
#define CLOUDMQTT_USER   "iatrqott"    // Usuario para o broker cloudmqtt
#define CLOUDMQTT_PASSWORD  "gW7u8bMQzjOm"// Senha para o broker cloudmqtt
// Macros para filtragem dos dados do MPU6050
#define SCALE_GYRO  131         // Escala de sensibilidade do giroscopio  | 250-> 131  | 500-> 65.5|1000-> 32.8|2000->16.4|
#define SCALE_ACCEL 16384       // Escala de sensibilidade do acelerometro| 2g -> 16384| 4g -> 8192| 8g -> 4096| 16g->2048|
#define ALPHA_X     0.08
#define X_OFFSET    0.00
#define ALPHA_Y     0.08
#define Y_OFFSET_ACCEL    0.0
#define Y_OFFSET_GYRO     0.0
#define ALPHA_Z     0.008
#define Z_OFFSET_GYRO     0.0

//Credenciais para conexao WiFi
const char* SSID = "Link_One_420240";
const char* PASSWORD = "quintino";
WiFiClient wifiClient;

//Endereco e porta do MQTT Server
const char* BROKER_MQTT = "broker.hivemq.com";
int         BROKER_PORT = 1883;

PubSubClient MQTT(wifiClient);

//declaracao de funcoes utilizadas
void mantemConexoes();
void conectaWiFi();
void conectaMQTT();
void enviaPacote();

MPU6050 mpu;
int16_t ax, ay, az, gx, gy, gz;
int flexvalue, flexvalue_old=0, flex_prev=0;
unsigned long time_prev = 0;
float ax_deg, ay_deg,   // Valores do acelerometro em angulo(graus).
      ax_deg_old,ay_deg_old,az_deg_old,
      gx_dps, gy_dps, gz_dps, // Valores do giroscopio em grau/s
      gx_old, gy_old, gz_old, // Valores da medida anterior de g_dps
      gx_deg, gy_deg, gz_deg, // Valores do giroscopio em angulo(graus).
      gx_deg_old, gy_deg_old, gz_deg_old, // Valores da medida anterior de g_deg
      ang_x, ang_y, ang_z, dt,  // Valores de angulo apos filtro complementar
      ang_x_old, ang_y_old, ang_z_old; // Valores da medida anterior de ang_x            

void setup() {
  pinMode(pinBotao1, INPUT_PULLUP);
  pinMode(ledState, OUTPUT);
  pinMode(ledWiFi,OUTPUT);
  pinMode(ledBroker,OUTPUT);
  pinMode(ledSend,OUTPUT);
  pinMode(flex, INPUT);
  Serial.begin(115200);
  Wire.begin();

  conectaWiFi();
  MQTT.setServer(BROKER_MQTT, BROKER_PORT);

  mpu.initialize();

}

void loop() {
  mpu.getMotion6(&ax,&ay,&az,&gx,&gy,&gz);
  
  flexvalue = analogRead(flex); // Leitura do sensor flex
  delay(10);
  flexvalue = 0.2*flexvalue + 0.8*flexvalue_old; // Filtragem do sensor flex
  flexvalue_old = flexvalue;
  
  gx_dps = (float)gx/SCALE_GYRO;  // Conversao dos dados do giroscopio para grau/s
  gy_dps = (float)gy/SCALE_GYRO;             
  gz_dps = (float)gz/SCALE_GYRO;             

  dt = (float)(micros() - time_prev)/1000000.0; // calculo de tempo entre medidas em segundos
// Inicio do calculo dos angulos pelo giroscopio
  gx_deg += 0.5*(gx_dps + gx_old)*dt; // Calculo integral de posicao angular no eixo x;
  gx_old = gx_dps;                                
  gx_deg = (1*gx_deg - ALPHA_X*gx_deg_old);// + X_OFFSET; // Filtro Passa-Altas
  gx_deg_old = gx_deg;                            

  gy_deg += 0.5*(gy_dps + gy_old)*dt;    // Calculo integral de posicao angular no eixo y;
  gy_old = gy_dps;                                
  gy_deg = (1*gy_deg - ALPHA_Y*gy_deg_old) + Y_OFFSET_GYRO; // Filtro Passa-Altas
  gy_deg_old = gy_deg;                            
    
  gz_deg += 0.5*(gz_dps + gz_old)*dt;  // Calculo integral de posicao angular no eixo z;
  gz_old = gz_dps;  
  gz_deg = (1*gz_deg - ALPHA_Z*gz_deg_old) + Z_OFFSET_GYRO;
  gz_deg_old = gz_deg;
  
  time_prev = micros(); // Atualizacao da referencia de tempo para calculo de dt
// Fim do calculo dos angulos pelo giroscopio
// Inicio do calculo dos angulos pelo acelerometro
  ax_deg = atan(ay/sqrt(pow(ax,2) + pow(az,2)))*(180.0/3.14); // Calculo dos angulos do acelerometro por arcotangente
  ay_deg = atan(-ax/sqrt(pow(ay,2) + pow(az,2)))*(180.0/3.14);        

  ax_deg = (ALPHA_X*ax_deg + (1.0 - ALPHA_X)*ax_deg_old);  // Filtro Passa-Baixas
  ay_deg = (ALPHA_Y*ay_deg + (1.0 - ALPHA_Y)*ay_deg_old) + Y_OFFSET_ACCEL;  // Filtro Passa-Baixas
  ax_deg_old = ax_deg;
  ay_deg_old = ay_deg;
//  Valores definitivos de angulo
  ang_x = ax_deg + gx_deg; // Complemento 
  ang_y = ay_deg + gy_deg; // Complemento 
  ang_z = gz_deg; // Angulo no eixo z nao possui complemento
// Fim do calculo dos anguloa pelo acelerometro 

  
  delay(90);
  mantemConexoes();
  enviaPacote();
  MQTT.loop();  
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

  if(MQTT.connect(ID_MQTT)){//, MQTT_USER, MQTT_PASSWORD)){
    digitalWrite(ledBroker, HIGH);
  }
  else{
    digitalWrite(ledBroker,LOW);
    delay(3000);
   }
  }
}

void enviaPacote(){
  static unsigned long delay_send;
  int ang_x_i = 0, ang_y_i = 0, ang_z_i = 0;
  int flexao = (int)((float)(flexvalue - 1900)*127.0/200.0);// map(flexvalue, 1900,2100,0,127);
  if(flexao < 1)  flexao = 0;
  else if(flexao > 127) flexao = 127;
  
  char angulo[4] = { 0,0,0,0 };
  
  if( ((int)ang_y != (int)ang_y_old)||((int)ang_x != (int)ang_x_old)||((int)ang_z != (int)ang_z_old)||(flexao != flex_prev)){  
    ang_x_i = ang_x;
    angulo[0] = char(ang_x_i + 90);
    ang_x_old = ang_x;
    ang_y_i = ang_y;
    angulo[1] = char(ang_y_i + 90);
    ang_y_old = ang_y;
    ang_z_i = ang_z;
    angulo[2] = char(ang_z_i + 90);
    ang_z_old = ang_z;
    angulo[3] = char(flexao); // Sensor flex
    flex_prev = flexao;

    for(int i = 0;i < 3; i++){
      if(angulo[i] > 135 ) angulo[i] = 135;
      else if ( angulo[i] < 45 ) angulo[i] = 45;
    }
    
    if(MQTT.publish(TOPIC_PUBLISH, angulo)){
      Serial.print("X: ");Serial.print((int)angulo[0]);
      Serial.print("\tY: ");Serial.print((int)angulo[1]);
      Serial.print("\tZ: ");Serial.print((int)angulo[2]);
      Serial.print("\tFlex: ");Serial.print((int)angulo[3]);
      Serial.print("\tLido: ");Serial.println((int)flexvalue);
      digitalWrite(ledSend, HIGH);
      delay_send = millis();
    }
  }
  if((millis()-delay_send) > 100)
  {
    digitalWrite(ledSend,LOW);
    delay_send = millis();  
  }
}

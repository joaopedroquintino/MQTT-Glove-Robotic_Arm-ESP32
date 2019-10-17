# MQTT-Glove-Robotic_Arm-ESP32
Movimentação de um braço robótico articulado com 4 graus de liberdade através de comandos de uma luva de captação de movimentos que envia os dados por uma conexão via MQTT.
Estes códigos foram desenvolvidos para um Trabalho de Conclusão de Curso do curso de Engenharia de Computação na Universidade Federal de Sergipe. 
## MQTT_Publisher
Este programa, desenvolvido na IDE do Arduino para o microcontrolador ESP32, faz a leitura de um módulo MPU-6050 e de um sensor flexível. O MPU-6050 contém um acelerômetro e um giróscópio, ambos de 3 eixos, e é feita a filtragem utilizando o filtro complementar para corrigir o ruído.
Além disso, é feita uma conexão com uma rede WiFi para que possa ser feita uma conexão via MQTT ao Broker HiveMQ, para onde serão enviados os dados de ângulo lidos do MPU-6050 e de flexão do sensor flexível.

## MQTT_Subscriber
Este programa, desenvolvido na IDE do Arduino para o microcontrolador ESP32, faz uma conexão WiFi, a assinatura de um tópico em um Broker MQTT de onde são lidos valores de ângulo e flexão. Estas informações são convertidas em PWM para 4 servomotores contidos em um braço robótico articulado de 4 graus de liberdade.

## Software_PC
Este programa, desenvolvido em Java utilizando o NetBeans, faz uma conexão MQTT para recebimento de dados de ângulo e flexão. Além disso, renderiza um objeto 3D em tela para validação da movimentação realizada e abre o programa CMSClient, que é um software de visualização de câmeras.

## Hardware utilizado
Para a implementação deste projeto são necessários os seguintes itens:
- 2 ESP32 (Foi utilizado a versão DevKit V1)
- 1 MPU-6050
- 1 Sensor Flexível (Foi utilizado o sensor flex da sparkfun de 2.2")
- 1 Roteador (Podendo ser 2, se forem utilizados os sistemas em ambientes diferentes)
- 1 Computador
- 1 Braço Robótico articulado de 4 graus de liberdade (Foi utilizado um em MDF com Hardware livre)
- Jumpers diversos
- 1 Câmera IP (Utilizada para envio das imagens do braço robótico em um ambiente externo, pode ser descartada se for utilizado em um mesmo ambiente)

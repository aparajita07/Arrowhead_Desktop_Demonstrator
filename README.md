# Arrowhead Desktop Demonstrator (Java Spring-Boot)
The Desktop demonstrator is a SOS with several temperature sensors and one servo motor whích will behave as service providers in the arrowhead framework and the consumer has the algorithm which relates the temperature sensors and servo motor.The consumer is able to change orchestration at run time so that it can switch between temperature sensors.

## Setting up the hardware

### Hardware used

- Raspberry Pi 4 B/B+
- Two 1-wire temperature sensors
- Servo motor
- Breadboard
- 4.7k Ohm resistor

### Connecting the wires

The connections we made are listed below. Refer to Figure 1 to find what pins to connect to.

- Temperature sensor power to pin 1, 3.3 VDC Power
- Temperature sensor Ground to pin 6, Ground
- Temperature sensor Data to pin 7, GPIO 7
- Resistor between Temperature sensor power and Data
- Servo motor Ground to pin 14, Ground
- Servo motor Power to pin 4, 5.0 VDC Power
- Servo motor Control to pin 32, GPIO 26



## UML of the SOS
![Authorization_crosscheck](uml.PNG)

## Application Systems in the Arrowhead Cloud:
### demo-car-consumer
This is the Consumer in the SOS.
### demo-energy-forecast-indoor-provider
This is the provider which fetches temperature from temperature sensor 1
### demo-car-provider
This is the provider which fetches temperature from temperature sensor 2
### demo-energy-forecast-outdoor-provider
This is the provider which receive POST request from the consumer for setting up the servo position.


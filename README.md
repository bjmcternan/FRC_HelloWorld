# FRC_HelloWorld
FRC Robot Hello World code
This code is used as a simple "Hello World" application that controls the balance of a robot\
Requires a First Robotics Competition RoboRio equipped with two Talon SRX motors and a joystick\
User can press "7" on the joystick to enable manual mode, where the joystick X-axis will determine the power to each motor\
User can press "8" on the joystick to enable automatic mode, where the fake g-sensor determines the power to each motor

Motors must have CAN addresses set to:\
Left motor - 16\
Right motor - 17

This can be changed in balance.java in the constructor

// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc293.FRC_HelloWorld.subsystems;

import org.usfirst.frc293.FRC_HelloWorld.Robot;
import org.usfirst.frc293.FRC_HelloWorld.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogGyro;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class balance extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonSRX talonLeft;
    private WPI_TalonSRX talonRight;
    private AnalogGyro analogGyro;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    ////Motors Constants
    private final float LEFT_DEADZONE = -0.25f;
    private final float RIGHT_DEADZONE = 0.25f;
    private final double LEFT_TILT_MAX = -1.0f;
    private final double RIGHT_TILT_MAX = 1.0f;
    ////
    
    private double testTiltChange = -0.01f;
    private double testValuesXAxis = 0.0f;

    private boolean autoMode = false;

    public balance() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        talonLeft = new WPI_TalonSRX(16);
        
        
        
        talonRight = new WPI_TalonSRX(17);
        
        
        
        analogGyro = new AnalogGyro(0);
        addChild("AnalogGyro",analogGyro);
        analogGyro.setSensitivity(0.007);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        testValuesXAxis = 0.0f; //Initialize testValuesYAxis
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

        //Update fake balance G sensor
        testValuesXAxis += testTiltChange; //Update testValue

        ////Test and verify tilt values
        if(testValuesXAxis <= LEFT_TILT_MAX) {//Check left tilt isn't maxed
            testValuesXAxis = LEFT_TILT_MAX; //Set to max
            testTiltChange *= -1; //Flip testTiltChange to rotate right
        } 
        else if(testValuesXAxis >= RIGHT_TILT_MAX) {//Check right tilt isn't maxed
            testValuesXAxis = RIGHT_TILT_MAX; //Set to max
            testTiltChange *= -1; //Flip testTiltChange to rotate left
        }

        //Check what balance mode
        if(autoMode) { //If auto balance mode is on use fake sensor
            driveMotors(testValuesXAxis); //Get G Sensor Y axis vlaue
        } else { //if auto balance is off use the joystick
            driveMotors(Robot.oi.joystickMain.getX()); //Get value from joystick
        }
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private void driveMotors(double gSensorY) {
        ////Adjust motor speeds depending on G sensors
        //if sensor is negative rotate robot left else rotate right
        if(gSensorY < LEFT_DEADZONE ) { //Rotate left
            System.out.println("Rotating left at " + Math.abs(gSensorY));
            talonRight.set(0);
            talonLeft.set(Math.abs(gSensorY));
        } else if(gSensorY > RIGHT_DEADZONE ) { //Rotate right
            System.out.println("Rotating right at " + Math.abs(gSensorY));
            talonRight.set(Math.abs(gSensorY));
            talonLeft.set(0);
        } else { //Sensor is in deadzone, turn off motors
            talonLeft.set(0);
            talonRight.set(0);
        }
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void toggleAutoMode() {
        if(autoMode == true) {
            System.out.println("Setting Auto mode to true");
            autoMode = false;
        }else if (autoMode == false) {
            System.out.println("Setting Auto mode to false");
            autoMode = true;
        }
    }

    public void autoBalance() {
        autoMode = true;
        System.out.println("Running Auto Balance");
    }

    public void manualBalance() {
        autoMode = false;
        System.out.println("Running Manual Balance");
    }
}


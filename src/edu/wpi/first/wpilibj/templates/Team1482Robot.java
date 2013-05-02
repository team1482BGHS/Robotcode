/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team1482Robot extends IterativeRobot {


    //initialize loop counters
    int m_disabledPeriodicLoops;
    int m_autoPeriodicLoops;
    int m_telePeriodicLoops;
    
    int m_dsPacketsReceivedInCurrentSecond;
    
    RobotDrive drive = new RobotDrive(1, 2);
    
    //Set up joystick and driving refrence
    Joystick drivestick = new Joystick(1);
    Joystick shootstick = new Joystick(2);
    public static int NUM_JOYSTICK_BUTTONS = 16;
    
    //Set up air compressor
    Compressor airCompressor = new Compressor(1,1);
    Solenoid lift      = new Solenoid(1);
    Solenoid liftreset = new Solenoid(2);
    Solenoid drop      = new Solenoid(3);
    Solenoid dropreset = new Solenoid(4);
    
    //************Initalize************
    //Any code in this section will run once when the robot is turned on.
    public void robotInit() {
        System.out.println("RobotInit() completed. \n");

    }

    //************Disabled************
    public void disabledInit() {
        m_disabledPeriodicLoops = 0; //resets loop counter on disabling
        Timer.delay(0.002);
        getWatchdog().feed();
    }
    
	//************Autonomous************
	/**
	* This function is called once when entering autonomous
	*/
    public void autonomousInit() {
        m_autoPeriodicLoops = 0; //resets loop counter on entering auto
        getWatchdog().setEnabled(false);
        getWatchdog().setExpiration(0.5);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
	// insert code here
    }

    //************Teleop************
	/**
	* This function is called once when entering teleop
	*/
    public void teleopInit() {
        m_telePeriodicLoops = 0; //resets loop counter on entering tele
        getWatchdog().setEnabled(true);
        airCompressor.start();
        //Retract all solenoids
    }
	/**
	* This function is called periodically during teleop
	*/
    public void teleopPeriodic() {
    }
	/**
	* This function runs continuously during teleop
	*/
    public void teleopContinuous() {
        if (isEnabled()) {
            drive.arcadeDrive(drivestick);
            getWatchdog().feed();
            System.out.println("Feed Watchdog");
            Timer.delay(0.005);
        }
		else {
            Timer.delay(0.01);
            getWatchdog().feed();
        }
    }

    //************Test Mode************
    public void testPeriodic() {
        //Periodically feed the Watchdog
        getWatchdog().feed();
    }

    
    //************Functions************
    /* Example code!! Modify! */
    public void DemonstrateJoystickButtons(Joystick currStick,
            boolean[] buttonPreviouslyPressed,
            String stickString,
            Solenoid solenoids[]) {

        boolean outputGenerated = false;	//flag for whether or not output is generated for a button
        int numOfButtonPressed = 0;		//0 if no buttons pressed, -1 if multiple buttons pressed
        int buttonNum;

        /* Iterate over all the buttons on the joystick, checking to see if each is pressed
         * If a button is pressed, check to see if it is newly pressed; if so, print out a
         * message on the console
         */
        for (buttonNum = 1; buttonNum <= NUM_JOYSTICK_BUTTONS; buttonNum++) {
            if (currStick.getRawButton(buttonNum)) {
                //the current button is pressed, now act accordingly...
                if (!buttonPreviouslyPressed[buttonNum]) {
                    //button newly pressed; print out a message
                    if (!outputGenerated) {
                        //print out a heading if no other button pressed this cycle
                        outputGenerated = true;
                        System.out.println("button pressed:" + stickString);
                        
                    }
                    System.out.println(" " + buttonNum);
                }
                //remember that this button is pressed for the next iteration
                buttonPreviouslyPressed[buttonNum] = true;

                //set numOfButtonPressed appropriately
                if (numOfButtonPressed == 0) {
                    //no button pressed yet this time through, set the number correctly
                    numOfButtonPressed = buttonNum;
                }
				else {
                    //another button (or buttons) must have already been pressed, set appropriately
                    numOfButtonPressed = -1;
                }
            }
			else {
                buttonPreviouslyPressed[buttonNum] = false;
            }
        }

        //after iterating through all the buttons, add a newline to output if needed
        if (outputGenerated) {
            System.out.println("\n");
        }

        if (numOfButtonPressed == -1) {
            //multiple buttons were pressed, display as if button 15 was pressed
            //DisplayBinaryNumberOnSolenoidLEDs(15, solenoids);
        }
		else {
            //display the number of the button pressed on the solenoids;
            //note that if no button was pressed (0), the solenoid display will be cleared (set to 0)
            //DisplayBinaryNumberOnSolenoidLEDs(numOfButtonPressed, solenoids);
        }
    }
}

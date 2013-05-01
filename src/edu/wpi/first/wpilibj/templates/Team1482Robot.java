/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team1482Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
     
     
     int m_dsPacketsReceivedInCurrentSecond;
    
    RobotDrive drive = new RobotDrive(1, 2, 3, 4);
    
    Joystick driveJoystick = new Joystick(1);
    Joystick shootJoystick = new Joystick(2);
    public static int NUM_JOYSTICK_BUTTONS = 16;
    
    
    
    public void robotInit() {
        System.out.println("RobotInit() completed. \n")

    }
    
    //Do when the robot is disabled
    public void disabledInit() {
        m_disabledPeriodicLoops = 0 //resets loop counter on disabling
        
    }
    //*************Autonomous*************
    
    public void autonomousInit() {
        m_autoPeriodicLoops = 0 //resets loop counter on entering auto
        getWatchdog().setEnabled(false);
        getWatchdog().setExpiration(0.5);
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    //*************TELE OP*************
    public void teleopInit(){
        m_telePeriodicLoops = 0 //resets loop counter on entering tele
        getWatchdog().setEnabled(true);
    }
    
    public void teleopPeriodic() {
    }
    public void teleopContinuous() {
        drive.arcadeDrive(driveJoystick);
        getWatchdog().feed();
        System.out.println("Fead watchdog");
        Timer.delay(0.005);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        getWatchdog().feed();
    }
    
    
    /* Example code!! modify! */
    public void DemonstrateJoystickButtons(Joystick currStick,
    								boolean[] buttonPreviouslyPressed,
									String stickString,
									Solenoid solenoids[]) {

		boolean outputGenerated = false;		// flag for whether or not output is generated for a button
		int numOfButtonPressed = 0;		// 0 if no buttons pressed, -1 if multiple buttons pressed

		/* Iterate over all the buttons on the joystick, checking to see if each is pressed
		 * If a button is pressed, check to see if it is newly pressed; if so, print out a
		 * message on the console
		 */
		for (buttonNum = 1; buttonNum <= NUM_JOYSTICK_BUTTONS; buttonNum++) {
			if (currStick.getRawButton(buttonNum)) {
				// the current button is pressed, now act accordingly...
				if (!buttonPreviouslyPressed[buttonNum]) {
					// button newly pressed; print out a message
					if (!outputGenerated) {
						// print out a heading if no other button pressed this cycle
						outputGenerated = true;
						System.out.println("button pressed:" + stickString);
					}
					System.out.println(" " + buttonNum);
				}
				// remember that this button is pressed for the next iteration
				buttonPreviouslyPressed[buttonNum] = true;

				// set numOfButtonPressed appropriately
				if (numOfButtonPressed == 0) {
					// no button pressed yet this time through, set the number correctly
					numOfButtonPressed = buttonNum;
				} else {
					// another button (or buttons) must have already been pressed, set appropriately
					numOfButtonPressed = -1;
				}
			} else {
				buttonPreviouslyPressed[buttonNum] = false;
			}
		}

		// after iterating through all the buttons, add a newline to output if needed
		if (outputGenerated) {
			System.out.println("\n");
		}

		if (numOfButtonPressed == -1) {
			// multiple buttons were pressed, display as if button 15 was pressed
			//DisplayBinaryNumberOnSolenoidLEDs(15, solenoids);
		} else {
			// display the number of the button pressed on the solenoids;
			// note that if no button was pressed (0), the solenoid display will be cleared (set to 0)
			//DisplayBinaryNumberOnSolenoidLEDs(numOfButtonPressed, solenoids);
		}
	}
}

}

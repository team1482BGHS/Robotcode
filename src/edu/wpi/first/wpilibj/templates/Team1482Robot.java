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
import edu.wpi.first.wpilibj.Talon;
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
    int m_teleContinuousLoops;

    int m_dsPacketsReceivedInCurrentSecond;
    
    int m_liftstate;
    //Set up arcade drive
    RobotDrive drive = new RobotDrive(1, 2);
    
    //Set up Talons to do whatever (uncomment as needed)
    //Talon pwm3_motor = new Talon(3);
    //Talon pwm4_motor = new Talon(4);
    //Talon pwm5_motor = new Talon(5);
    //Talon pwm6_motor = new Talon(6);
    
    //Set up joystick
    Joystick drivestick = new Joystick(1);
    Joystick shootstick = new Joystick(2);
    public static int NUM_JOYSTICK_BUTTONS = 16;
    
    //Phase next two out
    boolean[] m_rightStickButtonState = new boolean[(NUM_JOYSTICK_BUTTONS+1)];
    boolean[] m_leftStickButtonState = new boolean[(NUM_JOYSTICK_BUTTONS+1)];
    
    //Integrate
    boolean[] m_shootStickButtonState = new boolean[(NUM_JOYSTICK_BUTTONS+1)];    
        
    //Set up air compressor
    Compressor airCompressor = new Compressor(1,1);
    Solenoid lift      = new Solenoid(1);
    Solenoid liftreset = new Solenoid(2);
    Solenoid drop      = new Solenoid(3);
    Solenoid dropreset = new Solenoid(4);
    

    public Team1482Robot() {
        System.out.println("BuiltinDefaultCode Constructor Started\n");
        
        
        int buttonNum = 1;
        for (buttonNum = 1; buttonNum <= NUM_JOYSTICK_BUTTONS; buttonNum++) {
            m_rightStickButtonState[buttonNum] = false;
            m_leftStickButtonState[buttonNum] = false;
            m_shootStickButtonState[buttonNum] = false;
        }        
    }
    //************Initalize************
    //Any code in this section will run once when the robot is turned on.
    public void robotInit() {
        System.out.println("RobotInit() completed. \n");

    }

    //************Disabled************
    public void disabledInit() {
    	System.out.println("Robot disabled");
        m_disabledPeriodicLoops = 0; //resets loop counter on disabling

    }
    public void disabledPeriodic() {
    	m_disabledPeriodicLoops++;
        Timer.delay(0.002);
        getWatchdog().feed();
    }
    
	//************Autonomous************
	/**
	* This function is called once when entering autonomous
	*/
    public void autonomousInit() {
    	System.out.println("Autonomous started");
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
    	System.out.println("Starting Teleop");
        m_telePeriodicLoops = 0;
        m_teleContinuousLoops = 0; //resets loop counters on entering tele 
        getWatchdog().setEnabled(true);
        airCompressor.start(); //start compressor
        //Set up solonide
        lift.set(false);
        liftreset.set(true);
        m_liftstate = 0;
    }
	/**
	* This function is called periodically during teleop
	*/
    public void teleopPeriodic() {
    	m_telePeriodicLoops++;
    }
	/**
	* This function runs continuously during teleop
	*/
    public void teleopContinuous() {
        if (isEnabled()) {
            m_teleContinuousLoops++;
            drive.arcadeDrive(drivestick);


            if (ButtonToggle(shootstick, m_shootStickButtonState, 1) == "held") {
                System.out.println("Button 1 held");
            } else if (ButtonToggle(shootstick, m_shootStickButtonState, 1) == "pressed") {
                System.out.println("Button 1 just pressed");
                //When pressed
                
                //If retracted extend
                if(m_liftstate == 0){
                    lift.set(true);
                    liftreset.set(false);
                    m_liftstate = 1;
                }
                //If is not retracted retract
                else{
                    lift.set(false);
                    liftreset.set(true);
                    m_liftstate = 0;
                }
            }

            getWatchdog().feed();
            Timer.delay(0.005);
        } else {
            Timer.delay(0.01);
            getWatchdog().feed();
        }
    }

    //************Test Mode************
    public void testPeriodic() {
    	System.out.println("Starting Test Mode");
        //Periodically feed the Watchdog
        getWatchdog().feed();
    }

    
    //************Functions************
    public String ButtonToggle(Joystick currStick, boolean[] buttonPreviouslyPressed, int buttonNum) {


        if (currStick.getRawButton(buttonNum)) {  //Is button pressed?
            if (!buttonPreviouslyPressed[buttonNum]) {   //Was this button pressed last cycle
                //Set button to now pressed
                buttonPreviouslyPressed[buttonNum] = true;
                return "pressed";

            } else {
                //Button is pressed and was also pressed last cycle
                return "held";
            }
        } //Button not pressed at all
        else {
            //button is not currentally pressed
            buttonPreviouslyPressed[buttonNum] = false;

            return null;
        }
    }
}

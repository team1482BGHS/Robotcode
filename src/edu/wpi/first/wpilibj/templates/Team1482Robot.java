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
        getWatchdog().feed();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        getWatchdog().feed();
    }
    
}

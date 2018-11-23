/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Auto.CommandGroupAuto;
import frc.robot.Enumeration.Auto;
import frc.robot.Subsystems.SubsystemElevator;
import frc.robot.Subsystems.SubsystemReceiver;
import frc.robot.Subsystems.SubsystemShooter;
import frc.robot.Subsystems.SubsystemTurret;

//        _____   _____   ____     ______
//       |__  /  / ___/  / __ \   / ____/
//        /_ <  / __ \  / /_/ /  /___ \
//      ___/ / / /_/ /  \__, /  ____/ /
//     /____/  \____/  /____/  /_____/
//
//     2018 (Fall Challenge) - [Unnamed Bot]

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends IterativeRobot {

  /**
   * Instantiate the auto chooser and the string values
   * that you'll use as objects when populating the chooser
   */
  SendableChooser<Auto> autoChooser;

  /**
   * Instantiate Subsystems
   */
  public static SubsystemElevator SUB_ELEVATOR;
  public static SubsystemReceiver SUB_RECEIVER;
  public static SubsystemShooter  SUB_SHOOTER;
  public static SubsystemTurret   SUB_TURRET;

  public static OI oi;

  private CommandGroupAuto auto;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    DriverStation.reportWarning("ROBOT STARTED", false);
    DriverStation.reportWarning("GOOD LUCK, HAVE FUN", false);
    DriverStation.reportWarning("AIM FOR THE FRESHMAN", false);
    
    /**
     * Set up the choosers
     */
    autoChooser = new SendableChooser<>();
    autoChooser.addDefault(Auto.NOTHING.toString(), Auto.NOTHING);
    autoChooser.addObject(Auto.SEARCH_AND_DESTROY.toString(), Auto.SEARCH_AND_DESTROY);
    SmartDashboard.putData("Auto", autoChooser);
    DriverStation.reportWarning("CHOOSERS INSTANTIATED", false);


    /**
     * Set up the subsystems
     */
    SUB_ELEVATOR = new SubsystemElevator();
    SUB_RECEIVER = new SubsystemReceiver();
    SUB_SHOOTER  = new SubsystemShooter();
    SUB_TURRET   = new SubsystemTurret();

    SmartDashboard.putData("SUB_ELEVATOR", SUB_ELEVATOR);
    SmartDashboard.putData("SUB_RECEIVER", SUB_RECEIVER);
    SmartDashboard.putData("SUB_SHOOTER", SUB_SHOOTER);
    SmartDashboard.putData("SUB_TURRET", SUB_TURRET);

    DriverStation.reportWarning("SUBSYSTEMS INSTANTIATED", false);
    

    oi = new OI();

    
    DriverStation.reportWarning("ROBOT INIT COMPLETE", false);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SUB_ELEVATOR.publishSwitches();

    SmartDashboard.putNumber("Flywheel %", SUB_SHOOTER.getPercentOutput() * 100d);
    SmartDashboard.putNumber("Flywheel RPM", SUB_SHOOTER.getFlywheelRPM());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    DriverStation.reportWarning("STARTING AUTONOMOUS", false);
    auto = new CommandGroupAuto(autoChooser.getSelected());
    auto.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  }


  /**
   * This function is called once when the robot is disabled.
   */
  public void disabledInit() {
    DriverStation.reportWarning("TELEOP IS DISABLED", false);
    
  }

  /**
   * This function is called periodically while the robot is disabled.
   */
  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called once when the robot enters teleop mode.
   */
  public void teleopInit() {
    DriverStation.reportWarning("TELEOP IS ENABLED", false);
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called when the robot enters test mode.
   */
  @Override
  public void testInit() {
    DriverStation.reportWarning("TEST MODE IS ENABLED", false);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

}

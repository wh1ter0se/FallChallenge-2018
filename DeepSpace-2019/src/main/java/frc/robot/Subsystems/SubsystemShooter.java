/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Util.JoystickController;

/**
 * Controls the flywheel motor
 */
public class SubsystemShooter extends Subsystem {

  TalonSRX shooter;

  @Override
  public void initDefaultCommand() {
  }
  
  public SubsystemShooter() {
    shooter = new TalonSRX(Constants.ShooterID);
      shooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
  }

  /**
   * Sets the shooter's percent output based on the 
   * axis value of the given joystick's scalar
   * @param joy The joystick that controls the flywheel speed
   */
  public void shootByJoystick(Joystick joy) {
    double speed = JoystickController.SCALAR(joy);
    shooter.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Sets the shooter's percent ouput to a given double
   * @param percent the percent output of the shooter motor
   */
  public void shootByPercent(double percent) {
    shooter.set(ControlMode.PercentOutput, percent);
  }

  /**
   * Sets the flywheel speed to zero
   */
  public void stopShooting() {
    shooter.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Gets the percent output of the flywheel motor
   * @return percent output of the flywheel motor
   */
  public double getPercentOutput() {
    return shooter.getMotorOutputPercent();
  }

   /**
   * Returns the RPM of the flywheel
   * @return flywheel RPM
   */
  public int getFlywheelRPM() {
    return (int) (shooter.getSelectedSensorVelocity(0) / (2 * Math.PI));
  }

}

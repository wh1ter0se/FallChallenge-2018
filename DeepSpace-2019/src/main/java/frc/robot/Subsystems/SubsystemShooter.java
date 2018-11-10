/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
  }

  /**
   * Sets the flywheel based on the axis value
   * of the given joystick's scalar
   * 
   * @param joy The joystick that controls the flywheel speed
   */
  public void shoot(Joystick joy) {
    double speed = JoystickController.SCALAR(joy);
    shooter.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Sets the flywheel speed to zero
   */
  public void stopShooting() {
    shooter.set(ControlMode.PercentOutput, 0);
  }

}

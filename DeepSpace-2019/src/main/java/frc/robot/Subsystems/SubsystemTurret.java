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
import frc.robot.Commands.ManualCommandTurn;
import frc.robot.Util.JoystickController;

/**
 * Controls the motor that rotates the shooter
 */
public class SubsystemTurret extends Subsystem {

  TalonSRX turret;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualCommandTurn());
  }

  public SubsystemTurret() {
    turret = new TalonSRX(Constants.TurretID);
  }

  /**
   * Sets the rotation speed equal to the X-axis value
   * of the given joystick
   * @param joy The joystick that controls the rotation
   */
  public void spinByJoystick(Joystick joy) {
    turret.set(ControlMode.PercentOutput, JoystickController.X_AXIS(joy));
  }

  /**
   * Sets the rotational speed equal to a given double
   * @param percent the percent output sent to the talon
   */
  public void spinByPercentOutput(double percent) {
    turret.set(ControlMode.PercentOutput, percent);
  }

  /**
   * Sets the Talon control mode to position and sets
   * the value equal to the given position
   * @param encoderTicks the desired position (signed) in encoder ticks
   */
  public void spinByPosition(int encoderTicks) {
    turret.set(ControlMode.Position, encoderTicks);
  }

  /**
   * Sets the spin speed to 0
   */
  public void stopSpinning() {
    turret.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Sets the turret talon's PIDF values
   */
  public void setPIDF(double P, double I, double D, double F) {
    turret.config_kF(Constants.PIDLoopID, P, Constants.timeoutMs);
		turret.config_kP(Constants.PIDLoopID, I, Constants.timeoutMs);
		turret.config_kI(Constants.PIDLoopID, D, Constants.timeoutMs);
		turret.config_kD(Constants.PIDLoopID, F, Constants.timeoutMs);
  }

  /**
   * Receives the units away the talon is from its target
   */
  public int getClosedLoopError() {
    return turret.getClosedLoopError(Constants.PIDLoopID);
  }
  
}

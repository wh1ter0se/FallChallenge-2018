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
import frc.robot.Util.Util;

/**
 * Controls the motor that rotates the shooter
 */
public class SubsystemTurret extends Subsystem {

  TalonSRX turret;

  int lastSeenPosition;

  private double P;
  private double I;
  private double D;
  private double F;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualCommandTurn());
  }

  public SubsystemTurret() {
    turret = new TalonSRX(Constants.TurretID);
    turret.configOpenloopRamp(0, 100);
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

  public void spinByVelocity(double speed) {
    turret.set(ControlMode.Velocity, speed);
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
      this.P = P;
      this.I = I;
      this.D = D;
      this.F = F;
  }

  public double[] getPIDF() {
    double[] PIDF = new double[4];
    PIDF[0] = P;
    PIDF[1] = I;
    PIDF[2] = D;
    PIDF[3] = F;
    return PIDF;
  }

  /**
   * Receives the units away the talon is from its target
   */
  public int getClosedLoopError() {
    return (turret.getControlMode() == ControlMode.Position ? turret.getClosedLoopError(Constants.PIDLoopID) : -1);
  }

  /**
   * Gets the position of the turret encoder
   * @return turret position in encoder ticks
   */
  public int getEncoderPosition() {
    return turret.getSensorCollection().getQuadraturePosition();
  }

  /**
   * Gets the target of the turret encoder's PID loop
   * @return target on turret PID loop 0
   */
  public int getEncoderTarget() {
    return (turret.getControlMode() == ControlMode.Position ? turret.getClosedLoopTarget(0) : -1);
  }

  /**
   * Returns the percent output going to the turret
   * @return turret percent output
   */
  public double getPercentOutput() {
    return turret.getMotorOutputPercent();
  }

  public void setLastSeenPosition() {
    this.lastSeenPosition = getEncoderPosition();
  }

  public int getLastSeenPosition() {
    return lastSeenPosition;
  }

  public int getVelocityTarget() {
    return (turret.getControlMode() == ControlMode.Velocity ? turret.getClosedLoopTarget(0) : -1);
  }

  public int getVelocity() {
    return (int) Util.nativeToRPM(turret.getSensorCollection().getQuadratureVelocity());
  }

  public int getVelocityError() {
    return (turret.getControlMode() == ControlMode.Velocity ? turret.getClosedLoopError(0) : -1);
  }
  
}

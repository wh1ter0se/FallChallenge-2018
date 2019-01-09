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
import frc.robot.Util.Util;

/**
 * Controls the flywheel motor
 */
public class SubsystemShooter extends Subsystem {

  TalonSRX shooter;

  private Boolean firing;

  private double P;
  private double I;
  private double D;
  private double F;

  @Override
  public void initDefaultCommand() {
  }
  
  public SubsystemShooter() {
    firing = false;
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
    return (int) Util.nativeToRPM(shooter.getSelectedSensorVelocity(0)); //converts u/100ms to rpm
  }

  /**
   * Returns whether or not the flywheel talon is diabled
   * @return flywheel talon IS in control mode other than Disabled
   */
  public Boolean getFiring() {
    return firing;
  }

  /**
   * Sets the firing boolean to a value
   * @param firing the value that 'firing' should be
   */
  public void setFiring(Boolean firing) {
    this.firing = firing;
  }

  /**
   * Sets the shooter talon's PIDF values
   */
  public void setPIDF(double P, double I, double D, double F) {
    shooter.config_kF(Constants.PIDLoopID, P, Constants.timeoutMs);
		shooter.config_kP(Constants.PIDLoopID, I, Constants.timeoutMs);
		shooter.config_kI(Constants.PIDLoopID, D, Constants.timeoutMs);
    shooter.config_kD(Constants.PIDLoopID, F, Constants.timeoutMs);
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

}

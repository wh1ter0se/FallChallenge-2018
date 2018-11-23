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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Commands.ManualCommandRise;
import frc.robot.Util.JoystickController;

/**
 * Controls the motor that adjusts the shooter's pitch
 */
public class SubsystemElevator extends Subsystem {

  public static TalonSRX elevator;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualCommandRise());
  }


  public SubsystemElevator() {
    elevator = new TalonSRX(Constants.ElevatorID);

    setAllInverts();
  }

  /**
   * Sets the motor inverts of the subsystem
   */
  private void setAllInverts() {
    elevator.setInverted(true);
  }

  /**
   * Sets the speed at which the elevator motor moves
   * equal to the Y-axis value of the given joystick
   *
   * NOTE: Movement currently is relative, not absolute;
   * the joystick controls the velocity at which the
   * elevator motor moves, not the position it moves to      
   * 
   * @param joy The joystick that controls the pitch
   */
  public void riseByJoystick(Joystick joy, double inhibitor) {
    double speed = JoystickController.Y_AXIS(joy) * inhibitor;
    elevator.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Sets the speed at which the elevator 
   * moves equal to a given double
   * 
   * @param percent the percent output sent to the talon
   */
  public void riseByPercentOutput(double percent) {
    elevator.set(ControlMode.PercentOutput, percent);
  }

  /**
   * Sets the Talon control mode to position and sets
   * the value equal to the given position
   * @param encoderTicks the desired position (signed) in encoder ticks
   */
  public void riseByPosition(int encoderTicks) {
    elevator.set(ControlMode.Position, encoderTicks);
  }

  /**
   * Sets the rise speed to 0
   */
  public void stopRising() {
    elevator.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Lowers the elevator at maximum speed with complete disregard.
   * The limit switch mechanically stops it, so this method doesn't
   * need to. The command calling this method should stop when
   * this method returns true.
   * 
   * @return the state of the lower limit switch
   */
  public Boolean lowerUntilSwitch() {
    elevator.set(ControlMode.PercentOutput, -.1);
    return elevator.getSensorCollection().isFwdLimitSwitchClosed();
  }

  /**
   * Sets the elevator encoder value to zero
   */
  public void zeroEncoder() {
    elevator.getSensorCollection().setQuadraturePosition(0, 5000);
  }

  /**
   * Retrieves the state of the lower limit switch
   * @return the state of the lower limit switch
   */
  public Boolean getLowerSwitch() {
    return elevator.getSensorCollection().isFwdLimitSwitchClosed();
  }

  /**
   * Retrieves the state of the upper limit switch
   * @return the state of the upper limit switch
   */
  public Boolean getUpperSwitch() {
    return elevator.getSensorCollection().isRevLimitSwitchClosed();
  }
  
  /**
   * Sends the boolean value of the limit switches to
   * the SmartDashboard
   */
  public void publishSwitches() {
    SmartDashboard.putBoolean("Lower Pitch", elevator.getSensorCollection().isFwdLimitSwitchClosed());
    SmartDashboard.putBoolean("Upper Pitch", elevator.getSensorCollection().isRevLimitSwitchClosed());
  }

  /**
   * Sets the elevator talon's PIDF values
   */
  public void setPIDF(double P, double I, double D, double F) {
    elevator.config_kF(Constants.PIDLoopID, P, Constants.timeoutMs);
		elevator.config_kP(Constants.PIDLoopID, I, Constants.timeoutMs);
		elevator.config_kI(Constants.PIDLoopID, D, Constants.timeoutMs);
		elevator.config_kD(Constants.PIDLoopID, F, Constants.timeoutMs);
  }

  /**
   * Receives the units away the talon is from its target
   */
  public int getClosedLoopError() {
    return elevator.getClosedLoopError(Constants.PIDLoopID);
  }
  
}

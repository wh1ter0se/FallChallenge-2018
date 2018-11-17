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
  public void rise(Joystick joy, double inhibitor) {
    double speed = JoystickController.Y_AXIS(joy) * inhibitor;
    elevator.set(ControlMode.PercentOutput, speed);
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
   * Sends the boolean value of the limit switches to
   * the SmartDashboard
   */
  public void publishSwitches() {
    SmartDashboard.putBoolean("Lower Pitch", elevator.getSensorCollection().isFwdLimitSwitchClosed());
    SmartDashboard.putBoolean("Upper Pitch", elevator.getSensorCollection().isRevLimitSwitchClosed());
  }
  
}

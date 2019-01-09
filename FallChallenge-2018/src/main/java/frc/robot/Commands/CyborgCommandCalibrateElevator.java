/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Util.Util;

/**
 * Zeroes the elevator's encoder by lowering it until
 * it hits the switch and then resetting the encoder
 */
public class CyborgCommandCalibrateElevator extends Command {

  private static Boolean isFinished;
  private static Boolean upperLimitSet;

  public CyborgCommandCalibrateElevator() {
    requires(Robot.SUB_ELEVATOR);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isFinished = false;
    upperLimitSet = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!upperLimitSet) { //zeroing out the lower encoder
      if (Robot.SUB_ELEVATOR.raiseUntilSwitch(Util.getAndSetDouble("Elevator Calibrate Speed", Constants.ElevatorCalibrateSpeed))) {
        Robot.SUB_ELEVATOR.zeroEncoder();
        upperLimitSet = true;
      }
    } else { //setting the upper limit's encoder position
      if (Robot.SUB_ELEVATOR.lowerUntilSwitch(Util.getAndSetDouble("Elevator Calibrate Speed", Constants.ElevatorCalibrateSpeed))) {
        isFinished = true;
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.SUB_ELEVATOR.setLowerLimitPosition();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Util.JoystickController;

public class ManualCommandRiseByPosition extends Command {

  private int position;

  public ManualCommandRiseByPosition() {
    requires(Robot.SUB_ELEVATOR);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    position = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    position = (int)((Robot.SUB_ELEVATOR.getUpperLimitPosition() - Robot.SUB_ELEVATOR.getLowerLimitPosition()) * ((JoystickController.Y_AXIS(OI.DRIVER) + 1) / 2));
    Robot.SUB_ELEVATOR.riseByPosition(position); //position = encoder range (max - min) times the absolute value of the y-axis
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}

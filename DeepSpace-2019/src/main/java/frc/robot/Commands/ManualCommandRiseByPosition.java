/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Util.JoystickController;
import frc.robot.Util.Util;

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
    Robot.SUB_ELEVATOR.setPIDF(
      Util.getAndSetDouble("Elevator Position kP", Constants.elevatorPositionP),
      Util.getAndSetDouble("Elevator Position kI", Constants.elevatorPositionI),
      Util.getAndSetDouble("Elevator Position kD", Constants.elevatorPositionD),
      Util.getAndSetDouble("Elevator Position kF", Constants.elevatorPositionF));
    position = (int)(Math.abs(Robot.SUB_ELEVATOR.getLowerLimitPosition() - Robot.SUB_ELEVATOR.getUpperLimitPosition()) * ((JoystickController.Y_AXIS(OI.DRIVER) + 1) / 2));
    Robot.SUB_ELEVATOR.riseByPosition(position); //position = encoder range (max - min) times the absolute value of the y-axis
    //the -1 is there because encoder counting is not inverted
    DriverStation.reportWarning("Position:" + position, false);
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

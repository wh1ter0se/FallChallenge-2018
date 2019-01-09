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

public class CyborgCommandOpeGoBack extends Command {
  public CyborgCommandOpeGoBack() {
    requires(Robot.SUB_TURRET);
  }

  private int target;

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    target = Robot.SUB_TURRET.getLastSeenPosition();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.SUB_TURRET.spinByPosition(target);
    Robot.SUB_TURRET.setPIDF(
      Util.getAndSetDouble("Turret Position kP", Constants.turretPositionP),
      Util.getAndSetDouble("Turret Position kI", Constants.turretPositionI),
      Util.getAndSetDouble("Turret Position kD", Constants.turretPositionD),
      Util.getAndSetDouble("Turret Position kF", Constants.turretPositionF));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.SUB_TURRET.getClosedLoopError() < 100 && Robot.SUB_RECEIVER.getLastKnownLocation()[0] != -1 && Robot.SUB_RECEIVER.getLastKnownLocation()[1] !=1;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.SUB_TURRET.stopSpinning();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}

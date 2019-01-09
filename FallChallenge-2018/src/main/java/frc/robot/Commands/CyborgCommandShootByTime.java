/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CyborgCommandShootByTime extends Command {

  long initTimeMs;
  long timeMs;

  public CyborgCommandShootByTime(int timeMs) {
    requires(Robot.SUB_SHOOTER);
    this.timeMs = timeMs;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    DriverStation.reportWarning("FIRING", false);
    initTimeMs = System.currentTimeMillis();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.SUB_SHOOTER.shootByPercent(1.0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return initTimeMs + timeMs < System.currentTimeMillis();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    DriverStation.reportWarning("DISENGAGING TARGET", false);
    Robot.SUB_SHOOTER.stopShooting();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}

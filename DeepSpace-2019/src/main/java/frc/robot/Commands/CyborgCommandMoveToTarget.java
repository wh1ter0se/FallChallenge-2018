/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Util.Util;

/**
 * Rotates the turret and adjusts the elevator to put the target
 * with a pixel tolerance of the center-most pixel coordinate
 */
public class CyborgCommandMoveToTarget extends Command {

  private static Boolean isFinished;

  private static int[] centerpoint;
  private static int[] target;

  private static double horizontalTicksPerPixel;
  private static double verticalTicksPerPixel;

  private static int horizontalTicksAway;
  private static int verticalTicksAway;

  public CyborgCommandMoveToTarget() {
    requires(Robot.SUB_ELEVATOR);
    requires(Robot.SUB_TURRET);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    DriverStation.reportWarning("ENGAGING TARGET", false);

    isFinished = false;

    centerpoint = new int[]{Constants.CameraWidth / 2, Constants.CameraHeight / 2};
    target = Robot.SUB_RECEIVER.getLastKnownLocation();

    //pixelsPerTick = pixelsPerRotation / ticksPerRotation
    horizontalTicksPerPixel = ((360 / Constants.HorizontalFOV) * Constants.CameraWidth) / Constants.EncoderTicksPerRotation;
    verticalTicksPerPixel   = ((360 / Constants.VerticalFOV) * Constants.CameraHeight) / Constants.EncoderTicksPerRotation;

    horizontalTicksAway = -1 * (int) ((centerpoint[0] - target[0]) * horizontalTicksPerPixel);
    verticalTicksAway   = -1 * (int) ((centerpoint[0] - target[0]) * verticalTicksPerPixel);

    // the -1 is because encoder positions aren't inverted
    
    Robot.SUB_ELEVATOR.setPIDF(
      Util.getAndSetDouble("Elevator Position kP", Constants.elevatorPositionP),
      Util.getAndSetDouble("Elevator Position kI", Constants.elevatorPositionI),
      Util.getAndSetDouble("Elevator Position kD", Constants.elevatorPositionD),
      Util.getAndSetDouble("Elevator Position kF", Constants.elevatorPositionF));
    Robot.SUB_TURRET.setPIDF(
      Util.getAndSetDouble("Turret Position kP", Constants.turretPositionP),
      Util.getAndSetDouble("Turret Position kI", Constants.turretPositionI),
      Util.getAndSetDouble("Turret Position kD", Constants.turretPositionD),
      Util.getAndSetDouble("Turret Position kF", Constants.turretPositionF));

    Robot.SUB_ELEVATOR.riseByPosition(Robot.SUB_ELEVATOR.getEncoderPosition() + verticalTicksAway);
    Robot.SUB_TURRET.spinByPosition(Robot.SUB_TURRET.getEncoderPosition() + horizontalTicksAway);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    isFinished = Robot.SUB_ELEVATOR.getClosedLoopError() < Constants.allowablePositionError &&
                 Robot.SUB_TURRET.getClosedLoopError() < Constants.allowablePositionError;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    DriverStation.reportWarning("TARGET ENGAGED", false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}

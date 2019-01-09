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
import frc.robot.Robot;
import frc.robot.Util.Util;

public class CyborgCommandFindTarget extends Command {

  public CyborgCommandFindTarget() {
    requires(Robot.SUB_ELEVATOR);
    requires(Robot.SUB_TURRET);
  }

  private Boolean isFinished;
  private Boolean rising;

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    DriverStation.reportWarning("FINDING TARGET", false);

    isFinished = false;
    rising = true;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.SUB_TURRET.spinByPercentOutput(Util.getAndSetDouble("Turret Scan Speed", Constants.TurretScanSpeed));
    Robot.SUB_TURRET.spinByVelocity(Util.RPMToNative(Util.getAndSetDouble("Turret Scan RPM", Constants.TurretScanVelocity)));
    Robot.SUB_TURRET.setPIDF(
      Util.getAndSetDouble("Turret Velocity kP", Constants.elevatorPositionP),
      Util.getAndSetDouble("Turret Velocity kI", Constants.elevatorPositionI),
      Util.getAndSetDouble("Turret Velocity kD", Constants.elevatorPositionD),
      Util.getAndSetDouble("Turret Velocity kF", Constants.elevatorPositionF));
    
    /**
     * Makes the elevator go up and down repeatedly
     */
    double elevatorScanSpeed = Math.abs(Util.getAndSetDouble("Elevator Scan Speed", Constants.ElevatorScanSpeed));
    if (rising && !Robot.SUB_ELEVATOR.getUpperSwitch()) { //SWITCH BACK WHEN THE LIMIT SWITCHES ARE RIGHT
      // move up if rising and not at the top
      Robot.SUB_ELEVATOR.riseByPercentOutput(elevatorScanSpeed);
    } else if (!rising && Robot.SUB_ELEVATOR.getLowerSwitch()) { //SWITCH BACK WHEN THE LIMIT SWITCHES ARE RIGHT
      // move up and set rising true if not rising but at the bottom
      Robot.SUB_ELEVATOR.riseByPercentOutput(elevatorScanSpeed);
      rising = true;
    } else {
      // move down and set rising false if rising but at the top
      Robot.SUB_ELEVATOR.riseByPercentOutput(-1 * elevatorScanSpeed);
      rising = false;
    }

    if (Robot.SUB_RECEIVER.getLastKnownLocation()[0] != -1 && Robot.SUB_RECEIVER.getLastKnownLocation()[1] !=1) {
      DriverStation.reportWarning("coord[0] = " + Robot.SUB_RECEIVER.getLastKnownLocation()[0], false);
      DriverStation.reportWarning("coord[1] = " + Robot.SUB_RECEIVER.getLastKnownLocation()[1], false);
      isFinished = true;
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isFinished;
    // return (Robot.SUB_RECEIVER.getLastKnownLocation() != new int[]{-1, -1});
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.SUB_ELEVATOR.stopRising();
    Robot.SUB_TURRET.stopSpinning();

    Robot.SUB_TURRET.setLastSeenPosition();
    DriverStation.reportWarning("ENEMY SPOTTED", false); //I couldn't help myself
    DriverStation.reportWarning("TURRET POSITION: " + Robot.SUB_TURRET.getEncoderPosition(), false); //I couldn't help myself
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}

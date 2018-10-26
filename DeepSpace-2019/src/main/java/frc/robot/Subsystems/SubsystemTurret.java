/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class SubsystemTurret extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public SubsytemTurret() {
    turret = new TalonSRX(Constants.TurretID);
  }

  public void spin(Joystick joy) {
    turret.set(ControlMode.PercentOutput, Util.Joystick.X_AXIS(joy));
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Commands.CyborgCommandFindTarget;
import frc.robot.Commands.CyborgCommandMoveToTarget;
import frc.robot.Commands.CyborgCommandShootByTime;
import frc.robot.Enumeration.Auto;

/**
 * Controls the autonomous seqences
 */
public class CommandGroupAuto extends CommandGroup {
  public CommandGroupAuto(Auto selectedAuto) {

    switch (selectedAuto) {
      case NOTHING:

        break;
      case SEARCH_AND_DESTROY:
        addSequential(new CyborgCommandFindTarget());
        addSequential(new CyborgCommandMoveToTarget());
        addSequential(new CyborgCommandShootByTime(7500));
        break;
    }
  }
}

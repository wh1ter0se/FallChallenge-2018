package frc.robot;

import frc.robot.Commands.ButtonCommandShoot;
import frc.robot.Commands.CyborgCommandCalibrateElevator;
import frc.robot.Commands.CyborgCommandFindTarget;
import frc.robot.Commands.CyborgCommandMoveToTarget;
import frc.robot.Commands.CyborgCommandShootByTime;
import frc.robot.Util.JoystickController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sets all controls beside ManualCommands (which
 * are controlled by the scheduler, since they're
 * InitDefaultCommands)
 * 
 * For each control, create the button and link an
 * event to it for use by the scheduler
 */
public class OI {

    /**
     * Instantiate the controllers
     */
    public static final Joystick DRIVER = new Joystick(0);
    public static final Joystick OPERATOR = new Joystick(1);
    
    public OI() {
        /**
         * Spins the flywheel if the trigger while the trigger is held
         */
        Button spinShooter = new JoystickButton(DRIVER, JoystickController.TRIGGER);
            spinShooter.toggleWhenPressed(new ButtonCommandShoot());


        SmartDashboard.putData("Calibrate Elevator", new CyborgCommandCalibrateElevator());
        SmartDashboard.putData("Find Target", new CyborgCommandFindTarget());
        SmartDashboard.putData("Move To Target", new CyborgCommandMoveToTarget());
        SmartDashboard.putData("Shoot by Time (10s)", new CyborgCommandShootByTime(10000));

        DriverStation.reportWarning("OI INSTANTIATED", false);
    }

}
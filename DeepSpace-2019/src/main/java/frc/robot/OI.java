package frc.robot;

import frc.robot.Util.JoystickController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    public static final Joystick DRIVER = new Joystick(0);
    public static final Joystick OPERATOR = new Joystick(1);
    
    Button spinShooter = new JoystickButton(DRIVER, JoystickController.TRIGGER);
        spinShooter.whileHeld(new ButtonCommandShoot());


}
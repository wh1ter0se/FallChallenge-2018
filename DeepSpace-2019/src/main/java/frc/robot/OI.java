package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {

    public static final Joystick DRIVER = new Joystick(0);
    public static final Joystick OPERATOR = new Joystick(1);
    
    Button shoot = new JoystickButton(Joystick.TRIGGER);
        shoot.whileHeld(new ButtonCommandShoot());


}
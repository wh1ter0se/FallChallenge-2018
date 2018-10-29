package frc.robot.Util;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickController {

    public static int 
        TRIGGER = 0;
        
    public static double X_AXIS(Joystick joy) {return joy.getRawAxis(0);}
    public static double Y_AXIS(Joystick joy) {return joy.getRawAxis(1);}
    public static double SCALAR(Joystick joy) {return joy.getRawAxis(2);}
        
}
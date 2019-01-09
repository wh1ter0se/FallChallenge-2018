package frc.robot.Util;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Contains all variables and methods needed to access
 * joystick values without magic numbers
 */
public class JoystickController {

    public static final double DEADZONE = .15;

    /**
     * Magic numbers to access all buttons on the joystick
     */
    public static int 
        TRIGGER = 1;
        //TODO add the rest of the magic numbers in

    /**
     * Simplifies any value within the deadzone to 0 to
     * avoid movement from insignificant nudges
     * 
     * @param rawAxis the unmodified joystick axis
     * @return        the modified joystick axis
     */    
    private static double deadzone(double rawAxis) {
        boolean positive = rawAxis > 0.0;
        rawAxis *= (positive ? 1.0 : -1.0); //flip if needed
        rawAxis -= DEADZONE; //clip dead zone
        if(rawAxis < 0.0) rawAxis = 0.0; //trim if less than 0
        rawAxis /= (1.0 - DEADZONE); //scale back to 1.0
        rawAxis *= (positive ? 1.0 : -1.0); //flip back
        return rawAxis;
    }    

    private static double signedToUnsigned(double rawAxis) {
        return (rawAxis + 1) / 2d;
    }

    public static double X_AXIS(Joystick joy) {return deadzone(joy.getRawAxis(0));}
    public static double Y_AXIS(Joystick joy) {return deadzone(joy.getRawAxis(1));}
    public static double SCALAR(Joystick joy) {return signedToUnsigned(joy.getRawAxis(2));}
        
}
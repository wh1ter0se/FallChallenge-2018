package frc.robot;

public class Constants {

    /**
     * Camera Dimensions
     */
    public static final int
        CameraHeight = 720,
        CameraWidth  = 1280;

    /**
     * Camera FOVs (in degrees)
     */
    public static final double
        HorizontalFOV = 62.8,
        VerticalFOV   = 37.9; //TODO verify these (lifecam HD-3000?)

    /**
     * Encoder Stuff
     */
    public static final int
        EncoderTicksPerRotation = 0;

    /**
     * Inhibitor Backup Values
     */
    public static final double
        ElevatorInhibitor = 0.6;
    
    /**
     * Limit Switch IDs
     */
    public static final int
        LowerPitchID = 0,
        UpperPitchID = 1;

    /**
     * PIDF Backup Values
     */
    public static double
        elevatorPositionP = 0,
        elevatorPositionI = 0,
        elevatorPositionD = 0,
        elevatorPositionF = 0,
        turretPositionP   = 0,
        turretPositionI   = 0,
        turretPositionD   = 0,
        turretPositionF   = 0;

    /**
     * PIDF-Related Values
     */
    public static final int
        allowablePositionError = 100,
        PIDLoopID              = 0,
        timeoutMs              = 30;

    /**
     * Autonomous Backup Values
     */
    public static final double
        ElevatorCalibrateSpeed = 0.6,
        ElevatorScanSpeed      = 0.6,
        TurretScanSpeed        = 0.5;

    /**
     * Talon IDs
     */
    public static final int 
        ElevatorID = 0,
        TurretID   = 1,
        ShooterID  = 2;

}
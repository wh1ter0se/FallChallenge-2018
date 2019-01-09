package frc.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import frc.robot.Constants.VisionConstants;

/**
 * Contains methods used for anything vision
 */
public class Vision extends IterativeRobot {

    private UsbCamera cameraFrame;

    private Mat failImage;

    public Vision(){
        Size camSize = new Size(VisionConstants.CAM_WIDTH, VisionConstants.CAM_HEIGHT);
        failImage = Mat.zeros(camSize, 0);
    }

    public void startFrameCameraThread(){
    	new Thread(this::frameCameraStream).start();
    }
    
    private void frameCameraStream(){
        cameraFrame = CameraServer.getInstance().startAutomaticCapture("Frame", VisionConstants.HOOK_ID);
        // cameraFrame = new UsbCamera("frame", 0);
        // cameraFrame.setFPS(30);
        // cameraFrame.setResolution(480,270);
    	CvSink cvsinkFrame = new CvSink("frameSink");
    	cvsinkFrame.setSource(cameraFrame);
    	cvsinkFrame.setEnabled(true);
    	
    	Mat streamImages = new Mat();

    	CvSource outputFrame = CameraServer.getInstance().putVideo("Frame", VisionConstants.CAM_WIDTH, VisionConstants.CAM_HEIGHT);
    	 while (!Thread.interrupted()){
             try {
                 cvsinkFrame.grabFrame(streamImages);
                 outputFrame.putFrame(streamImages);
             } catch (CvException cameraFail){
                 DriverStation.reportWarning("Frame Camera: " + cameraFail.toString(), false);
                 outputFrame.putFrame(failImage);
             }
    	 }
    }
}
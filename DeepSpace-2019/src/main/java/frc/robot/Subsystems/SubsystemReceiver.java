/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Util.Util;

/**
 * The receiver code that runs on the Rio to listen for UDP data
 */
public class SubsystemReceiver extends Subsystem {

  private static String latestSegment;

  private static DatagramSocket serverSocket;
  private static byte[]         receiveData;

  private static long latestTime;

  @Override
  public void initDefaultCommand() {
  }

  public SubsystemReceiver() {
    latestSegment = "-1,-1";

    try {
      serverSocket = new DatagramSocket(3695);
      receiveData  = new byte[1024];
    } catch (SocketException e) { //thrown when a socket cannot be created
      DriverStation.reportError("SOCKET EXCEPTION", true);
    }

    Thread listener = new Thread(() -> {
      while(!Thread.interrupted()) {
        try {
          DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); //create a new packet for the receiving data 
          serverSocket.receive(receivePacket); //receive the packet from the Socket
          String segment = new String(receivePacket.getData()).replaceAll("\\s+",""); //remove whitespace and place data in 'segment'
          latestSegment = segment;
          latestTime = System.currentTimeMillis();
          SmartDashboard.putString("THE PI SAYS:", segment); 
        } catch (IOException e) { //thrown when the socket cannot receive the packet
          DriverStation.reportError("IO EXCEPTION", true);
        }
      }
    });
    
    listener.start();

  }

  /**
   * Retrieves the last known pixel coordinates of the target
   * @return -1,-1 for no known location, or an int[] with the last coordinates
   */
  public int[] getLastKnownLocation() {
      int[] coord = new int[2];
      
      try {
        coord[0] = Integer.parseInt(latestSegment.split(",")[0]);
        coord[1] = Integer.parseInt(latestSegment.split(",")[1]);
        if (coord[0] < 0 || coord[1] < 1) { //just because we were having that weird -1,-1xx error
          coord[0] = -1;
          coord[1] = -1;
        }
      } catch (NumberFormatException e) {
        DriverStation.reportError("NUMBER FORMAT EXCEPTION", true); 
        DriverStation.reportError("coord[0] = " + coord[0], false); 
        DriverStation.reportError("coord[1] = " + coord[1], false); 
      }

      return coord;
  }

  /**
   * Returns the miliseconds since the pi sent the LastKnownLocation
   * @return ms since last received UDP packet
   */
  public double getSecondsSinceUpdate() {
    return Util.roundTo((double) ((System.currentTimeMillis() - latestTime) / 1000), 3);
  }
}

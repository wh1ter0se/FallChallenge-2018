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

/**
 * The receiver code that runs on the Rio to listen for UDP data
 */
public class SubsystemReceiver extends Subsystem {

  private static String latestSegment;

  private static DatagramSocket serverSocket;
  private static byte[]         receiveData;

  @Override
  public void initDefaultCommand() {
  }

  public SubsystemReceiver() {
    latestSegment = "-1,-1";

    try {
      serverSocket = new DatagramSocket(3695);
      receiveData  = new byte[1024];
    } catch (SocketException e) { //thrown when a socket cannot be created
      DriverStation.reportWarning("SOCKET EXCEPTION", false);
    }

    Thread listener = new Thread(() -> {
      while(!Thread.interrupted()) {
        try {
          DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); //create a new packet for the receiving data 
          serverSocket.receive(receivePacket); //receive the packet from the Socket
          String segment = new String(receivePacket.getData()); //create string with data to output
          latestSegment = segment;
          System.out.println("THE PI SAYS:" + segment); 
        } catch (IOException e) { //thrown when the socket cannot receive the packet
          DriverStation.reportWarning("IO EXCEPTION", false);
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
      coord[0] = Integer.parseInt(latestSegment.substring(0, latestSegment.indexOf(",")));
      coord[1] = Integer.parseInt(latestSegment.substring(latestSegment.indexOf(",")));
      } catch (NumberFormatException e) {
        System.out.println("NUMBER FORMAT EXCEPTION"); 
        System.out.println("coord[0] = " + coord[0]); 
        System.out.println("coord[1] = " + coord[1]); 
      }

      return coord;
  }
}

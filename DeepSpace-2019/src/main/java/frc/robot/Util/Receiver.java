package frc.robot.Util;

import java.io.IOException;
import java.net.*;

public class Receiver
{
   private static String latestSegment;

   // @mark I'll leave it commented here for now, but while(true)'s and threading are
   // big danger zones for iterative robot. we can try it if iterative calling doesn't
   // work, but if we can call it the nice way (iteratively), then we probably should.
   // the method below uses your code, but allows for iterative calling (called by Robot.robotPeriodic())

   // public static void main(String args[]) throws Exception {
	//    		//create/assign the socket
	//    		DatagramSocket serverSocket = new DatagramSocket(3695);
   //          byte[] receiveData = new byte[1024];

   //          while(true) {
   //             DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); //create a new packet for the receiving data 
   //             serverSocket.receive(receivePacket); //receive the packet from the Socket
   //             String segment = new String(receivePacket.getData()); //create string with data to output

   //             latestSegment = segment;
   //             System.out.println("THE PI SAYS:" + segment);   
   //          }
   // }



   /**
   * Called iteratively to read data sent to the Rio through UDP
   *
   * @throws SocketException thrown when a socket cannot be created
   * @throws IOException     thrown when the socket cannot receive the packet
   */
   public static void retrievePiData() throws SocketException, IOException {
      // create and assign the socket
      DatagramSocket serverSocket = new DatagramSocket(3695);
      byte[] receiveData = new byte[1024];

      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); //create a new packet for the receiving data 
      serverSocket.receive(receivePacket); //receive the packet from the Socket
      String segment = new String(receivePacket.getData()); //create string with data to output

      // output the segment
      if (segment != "") { latestSegment = segment; } //might need fixing: does no sent data read null or ""?
      System.out.println("THE PI SAYS:" + segment); 
   }

   /**
    * Retrieves the last known pixel coordinates of the target

    * @return -1,-1 for no known location, or a double[] with the last coordinates
    */
   public static double[] getLastKnownLocation() {
      double[] coord = new double[] {-1, -1};
      
      //TODO once we have the format of the segments, read them and store them here

      return coord;
   }
}
   
	   
   

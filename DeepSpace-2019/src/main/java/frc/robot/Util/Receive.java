import java.net.*;

public class Receive
{
   public static void main(String args[]) throws Exception
      {
	   		// Create/Assign the Socket
	   		DatagramSocket serverSocket = new DatagramSocket(3695);
            byte[] receiveData = new byte[1024];
            while(true)
               {
                // Create a new Packet for the receiving data  
            	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            	// Receive the packet from the Socket
                serverSocket.receive(receivePacket);
                // Create string with data to output
                String sentence = new String(receivePacket.getData());
                // Output data to console
                System.out.println("RECEIVED: " + sentence);
                  
               }
      }
   }
   
	   
   

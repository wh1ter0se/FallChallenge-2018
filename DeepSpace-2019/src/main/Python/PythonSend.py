import socket

#RoboRio's IP
UDP_IP = "10.39.65.100"
UDP_PORT = 3695
#Uncomment to send test messages v
#MESSAGE = "Test"

#Printing Data for Debugging Purposes
print "UDP target IP:", UDP_IP
print "UDP target port:", UDP_PORT
print "message:", MESSAGE

sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))
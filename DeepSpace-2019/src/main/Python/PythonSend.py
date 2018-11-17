import socket

UDP_IP = "10.36.95.100"
UDP_PORT = 50000
MESSAGE = "Test"

#print "UDP target IP:", UDP_IP
#print "UDP target port:", UDP_PORT
print "message:", MESSAGE


sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))
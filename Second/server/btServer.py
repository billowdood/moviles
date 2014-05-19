#!/usr/bin/python

"""
A simple Python script to receive messages from a client over
Bluetooth using Python sockets (with Python 3.3 or above)
"""

import socket

hostMACAddress = "88:1F:A1:0E:9A:07"
port = 3
backlog = 1
size = 1024
s = socket.socket(31, socket.SOCK_STREAM,3)
s.bind((hostMACAddress,port))
s.listen(backlog)
try:
    client,address = s.accept()
    while 1:
        data = client.recv(size)
        if data:
            print(data)
            client.send(data)
except:
    print("Closing socket")
    client.close()
    s.close()

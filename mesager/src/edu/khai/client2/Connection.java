package edu.khai.client2;

import java.io.IOException;
import java.net.DatagramSocket;

import edu.khai.core2.Packet;

public class Connection {
	DatagramSocket server = null;
	public void Send(Packet packet) {
		try {
			server.send(packet.toUDPPacket());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		server.disconnect();
		server.close();
	}
}

package edu.khai.core2;

import java.net.DatagramPacket;

public class PSay extends Packet {
	String message;
	@Override
	public DatagramPacket toUDPPacket() {
		byte[] data = message.getBytes();
		DatagramPacket p = new DatagramPacket(data, data.length);
		return null;
	}
	
}

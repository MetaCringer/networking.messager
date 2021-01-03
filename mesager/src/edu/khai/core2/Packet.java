package edu.khai.core2;

import java.net.DatagramPacket;

public abstract class Packet {
	public abstract DatagramPacket toUDPPacket();
	
//	public static byte[] stringToBytes(String text) {
//		return text.getBytes();
//	}
}

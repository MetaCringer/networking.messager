package edu.khai.core.packet;

import edu.khai.core.Connection;
import edu.khai.server.Packets;

public abstract class Packet {
	/**
	 * for input
	 * @param InputData
	 */
	byte[] in;
	public Packet(byte[] InputData){
		
	}
	/**
	 * for out
	 */
	protected Packet() {
		
	}
	
	public abstract String[] toArgs();
	
	protected abstract void handle();
	
	public void Handle(Connection c) {
		if(c.isLegalForInputPacket(this))
			handle();
	}
	
	public int getType() {
		return Packets.getFromObject(this).ordinal();
	}
	
	
}

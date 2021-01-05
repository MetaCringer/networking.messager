package edu.khai.core.packet;

import javax.management.InvalidAttributeValueException;

import edu.khai.core.Connection;

public abstract class Packet {
	/**
	 * for input
	 * @param InputData
	 */
	byte[] in;
	public Packet(String[] InputData) throws InvalidAttributeValueException {
		
	}
	
	//public static abstract Packet makePacket();
	
	/**
	 * for out
	 */
	public Packet() {
		
	}
	public abstract void init(String[] data)  throws InvalidAttributeValueException;
	
	public abstract String[] toArgs();
	
	protected abstract void handle(Connection c);
	
	public void Handle(Connection c) {
		if(c.isLegalForInputPacket(this))
			handle(c);
	}
	
	public int getType() {
		return Packets.getFromObject(this).ordinal();
	}
	
	
}

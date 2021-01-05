package edu.khai.core.packet;

import javax.management.InvalidAttributeValueException;

import edu.khai.client.Client;
import edu.khai.core.Connection;

public class PSNicknameIsBusy extends Packet {
	public PSNicknameIsBusy() {
	}
	@Override
	public String[] toArgs() {
		return new String[] {};
	}

	@Override
	protected void handle(Connection c) {
		Client.Disconnect("Nickname is busy");
	}
	@Override
	public void init(String[] data) throws InvalidAttributeValueException {
		
	}

}

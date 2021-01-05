package edu.khai.core.packet;

import javax.management.InvalidAttributeValueException;

import edu.khai.client.Client;
import edu.khai.core.Connection;

public class PSSay extends Packet {
	String nickname,message;
	public PSSay(String nickname, String message) {
		this.nickname = nickname;
		this.message = message;
	}
	public PSSay() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] toArgs() {
		return new String[] {nickname,message};
	}

	@Override
	protected void handle(Connection c) {
		System.out.println("from server"+nickname+" "+message);
		System.out.println(Client.gui);
		Client.gui.println(">"+nickname+": "+message);
	}
	@Override
	public void init(String[] data) throws InvalidAttributeValueException {
		if( (data == null) || (data.length < 2) ) 
			throw new InvalidAttributeValueException();
		nickname = data[0];
		message = data[1];
	}

}

package edu.khai.core.packet;

import javax.management.InvalidAttributeValueException;

import edu.khai.core.Connection;
import edu.khai.server.Server;
import edu.khai.server.ServerConnection;

public class PCSay extends Packet {
	String message;
	public PCSay(String message){
		super();
		this.message=message;
	}
	public PCSay(String[] args) throws InvalidAttributeValueException {
		init(args);
	}
	public PCSay() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String[] toArgs() {
		return new String[]{message};
	}

	@Override
	protected void handle(Connection c) {
		System.out.println("from client "+message);
		if(!(c instanceof ServerConnection)) return;
		String name = ((ServerConnection) c).getName();
		if(name == null)
			return;
		Server.sendBroadcast(new PSSay(name,message));
	}
	@Override
	public void init(String[] data) throws InvalidAttributeValueException {
		if(data == null || data.length <1)
			throw new InvalidAttributeValueException();
		message = data[0];
		
	}
}

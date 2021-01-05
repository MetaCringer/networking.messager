package edu.khai.core.packet;

import javax.management.InvalidAttributeValueException;

import edu.khai.core.Connection;
import edu.khai.server.Server;
import edu.khai.server.ServerConnection;

public class PChangeNickname extends Packet {
	private String name;
	public PChangeNickname(String name) {
		this.name = name;
	}
	public PChangeNickname(String[] args) throws InvalidAttributeValueException{
		super(args);
		init(args);
	}
	
	public PChangeNickname() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String[] toArgs() {
		
		return new String[] {name};
	}

	@Override
	protected void handle(Connection c) {
		System.out.println("from client"+name);
		if(!(c instanceof ServerConnection)) return;
		
		ServerConnection sc = (ServerConnection) c;
		if(Server.connections.containsKey(name)) {
			c.send(new PSNicknameIsBusy());
			c.Close();
			return;
		}
		sc.setAuth(name);
		sc.send(new PSSay("Server", name+" is connected"));
	}
	@Override
	public void init(String[] data) throws InvalidAttributeValueException {
		if( (data == null) || (data.length < 1) ) 
			throw new InvalidAttributeValueException();
		name = data[0];
		
	}

}

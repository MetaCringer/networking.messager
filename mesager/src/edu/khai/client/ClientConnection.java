package edu.khai.client;

import java.io.IOException;
import java.net.UnknownHostException;

import edu.khai.core.Connection;
import edu.khai.core.packet.Packet;

public class ClientConnection extends Connection{
	public ClientConnection(String ip, int port) throws UnknownHostException, IOException {
		super(ip, port);
		
	}

	@Override
	public void send(Packet message) {
		Send(message);
		
	}

	@Override
	protected void recieve(Packet message) {
		message.Handle(this);
		
	}

	@Override
	protected void errorHandler(Exception e) {
		DefaultErrorHandler(e);
		
	}
	
}

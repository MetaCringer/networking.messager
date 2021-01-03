package edu.khai.server;

import java.io.IOException;
import java.net.UnknownHostException;

import edu.khai.core.Connection;
import edu.khai.core.packet.Packet;

public class ServerConnection extends Connection {
	public ServerConnection(String ip, int port) throws UnknownHostException, IOException {
		super(ip, port);
	}

	@Override
	public void send(Packet message) {
		Send(message);
	}

	@Override
	public void recieve(Packet message) {
		message.Handle(this);
	}

	@Override
	protected void errorHandler(Exception e) {
		DefaultErrorHandler(e);
	}

	

}

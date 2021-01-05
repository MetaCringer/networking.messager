package edu.khai.server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.khai.core.Connection;
import edu.khai.core.packet.Packet;

public class ServerConnection extends Connection {
	
	private String nickname = null;
	
	private Thread auth;
	
	public String getName() {
		return nickname;
	}
	public ServerConnection(Socket s) throws IOException{
		super(s);
		
	}
//	public ServerConnection(String ip, int port) throws UnknownHostException, IOException {
//		super(ip, port);
//	}

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
	
	public void waitForNickname() {
		auth = new Thread(new Auth(this));
		auth.start();
		System.out.println("waitForNickname");
	}
	
	public void setAuth(String name) {
		System.out.println("setAuth "+name);
		nickname = name;
		auth.interrupt();
		Server.connections.put(nickname, this);
		System.out.println("setAuth "+name);
	}

	@Override
	public void Close() {
		try {
			close();
		} catch (IOException e) {
			
		}
		if(nickname == null)
			return;
		Server.connections.remove(nickname);
		
	}
	
}

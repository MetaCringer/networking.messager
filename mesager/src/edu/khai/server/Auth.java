package edu.khai.server;

public class Auth implements Runnable {
	ServerConnection s;
	public Auth(ServerConnection s) {
		this.s=s;
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			
		}
	}
	
}

package edu.khai.core;

import edu.khai.client.Client;
import edu.khai.server.Server;

public class TEST implements Runnable{

	public static void main(String[] args) {
		Thread c = new Thread(new TEST());
		c.start();
		Client.main(args);

	}

	@Override
	public void run() {
		Server.main(new String[] {});
		
	}

}

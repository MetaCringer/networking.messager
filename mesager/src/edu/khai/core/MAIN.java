package edu.khai.core;

import edu.khai.client.Client;
import edu.khai.server.Server;

public class MAIN {

	public static void main(String[] args) {
		if(args.length > 0) {
			Server.main(args);
		}
		Client.main(args);
	}
}

package edu.khai.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import edu.khai.core.packet.PSSay;

public class Server implements Runnable {
	public static Map<String,ServerConnection> connections = new ConcurrentHashMap<String,ServerConnection>();
	public static void main(String[] args) {
		Thread connectionCathing = new Thread(new Server());
		connectionCathing.start();
	}
	@Override
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(8001);
			while(true) {
				ServerConnection sc = new ServerConnection(ss.accept());
				System.out.println(sc.toString());
				sc.waitForNickname();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendBroadcast(PSSay mes) {
		for (Entry<String, ServerConnection> entry: connections.entrySet()) {
			entry.getValue().send(mes);
		}
	}
}

package edu.khai.client;

import java.io.IOException;

import edu.khai.core.Connection;

public class Client {
	public static GUI gui;
	private static Connection con;
	public static Connection getConnection() {return con;}
	public static void main(String[] args) {
		gui = new GUI();

	}
	public static boolean connect(String ip,int port) {
		try {
			con = new ClientConnection(ip, port);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
}

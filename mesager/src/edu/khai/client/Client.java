package edu.khai.client;

import java.io.IOException;

import edu.khai.core.Connection;
import edu.khai.core.packet.PCSay;
import edu.khai.core.packet.PChangeNickname;

public class Client {
	public static GUI gui;
	private static Connection con;
	public static Connection getConnection() {return con;}
	public static void main(String[] args) {
		gui = new GUI();
		System.out.println(gui);
	}
	public static boolean connect(String nickname,String ip,int port) {
		try {
			System.out.println("Client.connect1");
			con = new ClientConnection(ip, port);
			System.out.println("Client.connect2");
			con.send(new PChangeNickname(nickname));
			System.out.println("Client.connect3");
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	public static void send(String message) {
		 con.send(new PCSay(message));
	}
	public static void Disconnect(String message) {
		try {
			con.close();
		} catch (IOException e) {}
		con=null;
		gui.setVisible(false);
		gui.authGUI.setVisible(true);
		gui.authGUI.Lerror.setText(message);
	}
	 
}

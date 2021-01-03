package edu.khai.client2;

import java.net.DatagramSocket;

public class Client {
	Connection con = null;
	public String ip="";
	GUI gui;
	Client(){
		gui = new GUI(this);
	}
	public static void main(String[] args) {
		
		Client cl = new Client();
	}
	public void Connect(){
		if(con!=null) {
			con.destroy();
		}
		gui.getInputIP();
		gui.getInputPort();
		con = new Connection();
	}
	public void close() {
		con.destroy();
		gui.dispose();
	}
	

}

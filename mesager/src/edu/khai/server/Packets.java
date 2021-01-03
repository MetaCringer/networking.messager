package edu.khai.server;

import java.lang.reflect.InvocationTargetException;

import edu.khai.client.ClientConnection;
import edu.khai.core.Connection;
import edu.khai.core.packet.PCSay;
import edu.khai.core.packet.PChangeNickname;
import edu.khai.core.packet.PSSay;
import edu.khai.core.packet.Packet;

public enum Packets {
	ClientSay(ServerConnection.class,PCSay.class),
	ServerSay(ClientConnection.class,PSSay.class),
	ClientChangeNickName(ServerConnection.class,PChangeNickname.class);
	
	Class<? extends Connection> c;Class<? extends Packet> p;
	Packets(Class<? extends Connection> forWho, Class<? extends Packet> packet){
		this.c = forWho;
		this.p = packet;
		
	}
	public Packet MakePacket(byte[] data) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return p.getConstructor(byte[].class).newInstance(data);
	}
	/**
	 * null if not found
	 * @param p
	 * @return
	 */
	public static Packets getFromObject(Packet p) {
		for (Packets ep : Packets.values()) {
			if(ep.p.equals(p.getClass())) {
				return ep;
			}
		}
		return null;
	}
	public Class<? extends Connection> getConnectionClass() {
		return c;
	}
	
	
	
}

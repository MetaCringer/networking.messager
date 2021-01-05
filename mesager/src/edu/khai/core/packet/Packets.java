package edu.khai.core.packet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import javax.management.InvalidAttributeValueException;

import edu.khai.client.ClientConnection;
import edu.khai.core.Connection;
import edu.khai.server.ServerConnection;

public enum Packets {
	ClientSay(ServerConnection.class,PCSay.class),
	ServerSay(ClientConnection.class,PSSay.class),
	ClientChangeNickName(ServerConnection.class,PChangeNickname.class),
	NicknameIsBusy(ClientConnection.class, PSNicknameIsBusy.class);
	Class<? extends Connection> c;Class<? extends Packet> p;
	Packets(Class<? extends Connection> forWho, Class<? extends Packet> packet){
		this.c = forWho;
		this.p = packet;
		
	}
	public Packet MakePacket(String[] data) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidAttributeValueException {
		
		System.out.println("data "+data.length);
		Packet packet = p.newInstance();
		packet.init(data);
		return packet;
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

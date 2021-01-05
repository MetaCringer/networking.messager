package edu.khai.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.management.InvalidAttributeValueException;

import edu.khai.core.packet.Packet;
import edu.khai.core.packet.Packets;

public abstract class Connection implements Runnable{
	private Socket s;
	protected Thread inputThread;
	private DataInputStream in;
	private DataOutputStream out;
	public Connection(Socket s) throws IOException {
		this.s = s;
		in = new DataInputStream(s.getInputStream());
		out = new DataOutputStream(s.getOutputStream());
		inputThread = new Thread(this);
		inputThread.start();
	}
	public Connection(String ip, int port) throws UnknownHostException, IOException {
		s = new Socket();
		s.connect(new InetSocketAddress(ip, port));
		in = new DataInputStream(s.getInputStream());
		out = new DataOutputStream(s.getOutputStream());
		inputThread = new Thread(this);
		inputThread.start();
	}
	protected void Send(Packet p) {
		System.out.println("Send");
		Encoder en = Base64.getEncoder();
		String[] args = p.toArgs();
		for(int i = 0; i < args.length; ++i) {
			args[i] = en.encodeToString(args[i].getBytes());
		}
		String message = String.join("-", args);
		byte[] data = message.getBytes();
		try {
			out.writeInt(data.length);
			out.writeInt(p.getType());
			out.write(data);
			System.out.println("sended "+out.size()+" bytes" + new String(data));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String[] Receive(String message) {
		System.out.println("Convert: " +message);
		String[] result = message.split("-");
		System.out.println("частей: " +result.length);
		Decoder de = Base64.getDecoder();
		for(int i = 0; i<result.length; ++i) {
			result[i]=new String(de.decode(result[i].getBytes()));
		}
		System.out.println("Result: " + String.join("-", result));
		return result;
	}
	
	public void close() throws IOException {
		s.close();
		inputThread.interrupt();
		//inputThread.stop();
	}
	
	public abstract void send(Packet message);
	protected abstract void recieve(Packet message);
	protected abstract void errorHandler(Exception e);
	public abstract void Close();
	protected void DefaultErrorHandler(Exception e){
		e.printStackTrace();
		System.out.print(e.getMessage());
		this.Close();
	}
	
	@Override
	public void run() {
		byte[] buffer;
		try {
			while(!Thread.interrupted()) {
				if(in.available()>=8) {
					buffer = new byte[in.readInt()];
					int type = in.readInt();
					int readed=0;
					for(int i =0;i<buffer.length;i+=readed) {
						readed = in.read(buffer, i, buffer.length-i);
						readed = (readed<0)?0:readed;
					}
					System.out.println(buffer.length+" "+ type+" " + new String(buffer));
					Packets[] v = Packets.values();
					if(type>=v.length) continue;
					Packet p = v[type].MakePacket(Receive(new String(buffer)));
					if(p == null) {
						System.out.println("packet is null");
						continue;
					}
					recieve(p);
				}
				
			}
			
		} catch (IOException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InvalidAttributeValueException e) {
			errorHandler(e);
		}
	}
	
	public boolean isLegalForInputPacket(Packet p) {
		if(this.getClass().equals(Packets.getFromObject(p).getConnectionClass()))
			return true;
		return false;
	}
	
}

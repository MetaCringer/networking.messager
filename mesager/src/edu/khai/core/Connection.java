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

import edu.khai.core.packet.Packet;
import edu.khai.server.Packets;

public abstract class Connection implements Runnable{
	private Socket s;
	protected Thread inputThread;
	private DataInputStream in;
	private DataOutputStream out;
	public Connection(String ip, int port) throws UnknownHostException, IOException {
		s = new Socket();
		s.connect(new InetSocketAddress(ip, port));
		
		in = new DataInputStream(s.getInputStream());
		out = new DataOutputStream(s.getOutputStream());
		inputThread = new Thread(this);
	}
	protected void Send(Packet p) {
		Encoder en = Base64.getEncoder();
		String[] args = p.toArgs();
		for(int i = 0; i < args.length; ++i) {
			args[i] = en.encodeToString(args[i].getBytes());
		}
		String message = String.join(".", args);
		byte[] data = message.getBytes();
		try {
			out.writeInt(data.length);
			out.writeInt(p.getType());
			out.write(data);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String[] Receive(String message) {
		String[] result = message.split(".");
		Decoder de = Base64.getDecoder();
		for(int i = 0; i<result.length; ++i) {
			result[i]=new String(de.decode(result[i].getBytes()));
		}
		return result;
	}
	
	protected void close() throws IOException {
		s.close();
		inputThread.interrupt();
	}
	
	public abstract void send(Packet message);
	protected abstract void recieve(Packet message);
	protected abstract void errorHandler(Exception e);
	protected void DefaultErrorHandler(Exception e){
		e.printStackTrace();
		this.inputThread.interrupt();
		this.inputThread = new Thread(this);
		this.inputThread.start();
	}
	
	@Override
	public void run() {
		byte[] buffer;
		try {
			while(!Thread.interrupted()) {
				if(in.available()>8) {
					buffer = new byte[in.readInt()];
					int type = in.readInt();
					int readed=0;
					for(int i =0;i<buffer.length;i+=readed) {
						readed = in.read(buffer, i, buffer.length-i);
						readed = (readed<0)?0:readed;
					}
					Packets[] v = Packets.values();
					if(type>=v.length) continue;
					recieve(v[type].MakePacket(buffer));
				}
				
			}
			
		} catch (IOException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			errorHandler(e);
		}
		
		
		
	}
	
	public boolean isLegalForInputPacket(Packet p) {
		if(this.getClass().equals(Packets.getFromObject(p).getConnectionClass()))
			return true;
		return false;
	}
	
	
}

package edu.khai.core.packet;

public class PCSay extends Packet {
	String message;
	PCSay(String message){
		this.message=message;
	}
	@Override
	public String[] toArgs() {
		return new String[]{message};
	}

	@Override
	protected void handle() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

}

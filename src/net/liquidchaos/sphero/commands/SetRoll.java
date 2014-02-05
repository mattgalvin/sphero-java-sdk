package net.liquidchaos.sphero.commands;

public class SetRoll extends CommandPacket {

	static final byte DID = 0x02;
	static final byte CID = 0x30;

	byte speed;
	short heading;
	byte state;

	public SetRoll(byte speed, short heading, byte state) {
		this(true, speed, heading, state);
	}

	public SetRoll(boolean respond, byte speed, short heading, byte state) {
		this(respond, (byte) 0x00, speed, heading, state);
	}

	public SetRoll(boolean respond, byte sequence, byte speed, short heading, byte state) {
		super(respond, DID, CID, sequence);

		setSpeed(speed);
		setHeading(heading);
		setState(state);
	}

	public void setSpeed(byte speed) {
		this.speed = speed;
		
		setPacketData();
	}
	
	public void setHeading(short heading) {
		this.heading = heading;

		setPacketData();
	}
	
	public void setState(byte state) {
		this.state = state;
		
		setPacketData();
	}

	private void setPacketData() {
		byte[] payload = { speed, (byte)(heading >> 8), (byte)heading, state };

		setData(payload);
	}
}

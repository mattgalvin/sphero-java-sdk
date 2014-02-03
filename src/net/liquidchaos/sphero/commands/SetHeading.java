package net.liquidchaos.sphero.commands;

public class SetHeading extends CommandPacket {

	static final byte DID = 0x02;
	static final byte CID = 0x01;

	short heading;

	public SetHeading(short heading) {
		this(true, heading);
	}

	public SetHeading(boolean respond, short heading) {
		this(respond, (byte) 0x00, heading);
	}

	public SetHeading(boolean respond, byte sequence, short heading) {
		super(respond, DID, CID, sequence);

		setHeading(heading);
	}

	public void setHeading(short heading) {
		this.heading = heading;

		setPacketData();
	}

	private void setPacketData() {
//		byte[] payload = { (byte)heading, (byte) (heading >> 8) };
		byte[] payload = { (byte) (heading >> 8), (byte) heading };

		setData(payload);
	}
}

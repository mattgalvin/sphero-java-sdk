package com.liquidchaos.sphero.commands;

public class SetLEDColor extends CommandPacket {

	static final byte DID = 0x02;
	static final byte CID = 0x20;

	byte red;
	byte green;
	byte blue;
	boolean persist;

	public SetLEDColor(byte red, byte green, byte blue, boolean persist) {
		this(false, red, green, blue, persist);
	}

	public SetLEDColor(boolean respond, byte red, byte green, byte blue, boolean persist) {
		this(respond, (byte) 0x00, red, green, blue, persist);
	}

	public SetLEDColor(boolean respond, byte sequence, byte red, byte green, byte blue, boolean persist) {
		super(respond, DID, CID, sequence);

		setRGB(red, green, blue);
		setPersist(persist);
	}

	public void setRGB(byte red, byte green, byte blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;

		setPacketData();
	}

	public void setPersist(boolean persist) {
		this.persist = persist;

	}

	private void setPacketData() {
		byte[] payload = { this.red, this.green, this.blue, (this.persist ? (byte) 0 : (byte) 1) };

		setData(payload);
	}
}

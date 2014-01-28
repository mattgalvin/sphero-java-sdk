package com.liquidchaos.sphero.commands;

public class Ping extends CommandPacket {

	static final byte DID = 0x00;
	static final byte CID = 0x00;

	public Ping() {
		this(false);
	}

	public Ping(boolean respond) {
		this(respond, (byte) 0x00);
	}

	public Ping(boolean respond, byte sequence) {
		super(respond, DID, CID, sequence);
	}
}

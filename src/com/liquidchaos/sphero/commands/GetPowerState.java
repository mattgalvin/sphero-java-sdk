package com.liquidchaos.sphero.commands;

import java.io.IOException;
import java.io.InputStream;

import com.liquidchaos.sphero.responses.GetPowerStateResponse;
import com.liquidchaos.sphero.responses.ResponsePacket;

public class GetPowerState extends CommandPacket {

	static final byte DID = 0x00;
	static final byte CID = 0x20;

	public GetPowerState() {
		this(false);
	}

	public GetPowerState(boolean respond) {
		this(respond, (byte) 0x00);
	}

	public GetPowerState(boolean respond, byte sequence) {
		super(respond, DID, CID, sequence);
	}

	public ResponsePacket loadResponse(InputStream input) throws IOException {
		return GetPowerStateResponse.load(input);
	}

}

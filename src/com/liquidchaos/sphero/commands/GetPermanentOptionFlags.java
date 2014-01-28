package com.liquidchaos.sphero.commands;

import java.io.IOException;
import java.io.InputStream;

import com.liquidchaos.sphero.responses.GetPermanentOptionFlagsResponse;
import com.liquidchaos.sphero.responses.ResponsePacket;

public class GetPermanentOptionFlags extends CommandPacket {

	static final byte DID = 0x02;
	static final byte CID = 0x36;

	public GetPermanentOptionFlags() {
		this((byte)0x00);
	}

	public GetPermanentOptionFlags(byte sequence) {
		super(true, DID, CID, sequence);
	}

	public ResponsePacket loadResponse(InputStream input) throws IOException {
		return GetPermanentOptionFlagsResponse.load(input);
	}

}

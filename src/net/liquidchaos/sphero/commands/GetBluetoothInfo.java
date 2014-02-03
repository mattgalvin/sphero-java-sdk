package net.liquidchaos.sphero.commands;

import java.io.IOException;
import java.io.InputStream;

import net.liquidchaos.sphero.responses.GetBluetoothInfoResponse;
import net.liquidchaos.sphero.responses.ResponsePacket;

public class GetBluetoothInfo extends CommandPacket {

	static final byte DID = 0x00;
	static final byte CID = 0x11;

	public GetBluetoothInfo() {
		this((byte)0x00);
	}

	public GetBluetoothInfo(byte sequence) {
		super(true, DID, CID, sequence);
	}

	public ResponsePacket loadResponse(InputStream input) throws IOException {
		return GetBluetoothInfoResponse.load(input);
	}

}

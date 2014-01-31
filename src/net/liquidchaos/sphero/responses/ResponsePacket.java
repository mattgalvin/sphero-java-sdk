package net.liquidchaos.sphero.responses;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ResponsePacket {

	protected byte sop1;
	protected byte sop2;
	protected byte mrsp;
	protected byte seq;
	protected byte dlen;
	protected ArrayList<Byte> data;
	protected byte checksum;

	protected ResponsePacket() {
		data = new ArrayList<Byte>();
	}

	private void addData(byte data) {
		this.data.add(new Byte(data));
	}

	protected void loadData(InputStream input) throws IOException {
		sop1 = (byte)input.read();
		
		if (sop1 == 0x0d) {
			sop1 = (byte)input.read();
		}
		
		sop2 = (byte)input.read();
		mrsp = (byte)input.read();
		seq = (byte)input.read();
		dlen = (byte)input.read();
		
		for (int i=1; i<dlen; i++) {
			addData((byte)input.read());
		}
		
		checksum = (byte)input.read();
	}

	public static ResponsePacket load(InputStream input) throws IOException {
		ResponsePacket packet = new ResponsePacket();
		packet.loadData(input);
		
		return packet;
	}
	
	public String toString() {
		return String.format("[SOP1: %02x, SOP2: %02x, MRSP: %02x, SEQ: %02x, DLEN: %02x, DATA: %s, CHK: %02x]", sop1, sop2, mrsp, seq, dlen, data.toString(), checksum);
	}

}

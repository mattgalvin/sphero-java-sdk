package net.liquidchaos.sphero.commands;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import net.liquidchaos.sphero.responses.ResponsePacket;

/**
 * This is the base command packet for Sphero v2.0.
 * @author matt
 *
 */
public class CommandPacket {

	static final byte SOP2_NORESPOND = (byte)0xFE;
	static final byte SOP2_RESPOND = (byte)0xFF;

	/** Start of packet - always 0xFF **/
	protected byte sop1 = (byte)0xFF;

	protected byte sop2;
	protected byte did;
	protected byte cid;
	protected byte seq;
	protected byte dlen = 1;
	protected ArrayList<Byte> data;
	protected boolean respond;
	
	public CommandPacket(boolean respond, byte did, byte cid, byte seq) {
		this.respond = respond;
		sop2 = respond ? SOP2_RESPOND : SOP2_NORESPOND;
		this.did = did;
		this.cid = cid;
		this.seq = seq;
		
		data = new ArrayList<Byte>();
	}
	
	public void setData(byte[] data) {
		this.data.clear();
		for (byte d : data) {
			this.data.add(new Byte(d));
		}
		
		this.dlen = (byte)(this.data.size() + 1);
	}
	
	public void addData(byte data) {
		this.data.add(new Byte(data));
		this.dlen++;
	}
	
	private byte calculateChecksum() {
		
		int sum = did + cid + seq + dlen;
		
		for (byte d : data) {
			sum += d;
		}
		
		sum = ~(sum % 256) & 0xFF;
		
		return (byte)sum;
	}
	
	public byte[] getBytePacket() {
		
		byte[] packet = new byte[6 + (int)dlen];
		
		int idx = 0;
		packet[idx++] = sop1;
		packet[idx++] = sop2;
		packet[idx++] = did;
		packet[idx++] = cid;
		packet[idx++] = seq;
		packet[idx++] = dlen;
		
		for (Byte d : data) {
			packet[idx++] = d.byteValue();
		}

		packet[idx++] = calculateChecksum();
		
		return packet;
	}
	
	public boolean expectsResponse() {
		return respond;
	}
	
	public ResponsePacket loadResponse(InputStream input) throws IOException {
		return ResponsePacket.load(input);
	}
	
	public String toString() {
		return String.format("[SOP1: %02x, SOP2: %02x, DID: %02x, CID: %02x, SEQ: %02x, DLEN: %02x, DATA: %s, CHK: %02x]", sop1, sop2, did, cid, seq, dlen, data.toString(), calculateChecksum());
	}

}

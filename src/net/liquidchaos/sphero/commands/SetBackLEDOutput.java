package net.liquidchaos.sphero.commands;

public class SetBackLEDOutput extends CommandPacket {

	static final byte DID = 0x02;
	static final byte CID = 0x21;

	byte level;

	public SetBackLEDOutput(byte level) {
		this(false, level);
	}
	
	public SetBackLEDOutput(boolean respond, byte level) {
		this(respond, (byte)0x00, level);
	}
	
	public SetBackLEDOutput(boolean respond, byte sequence, byte level) {
		super(respond, DID, CID, sequence);
		
		setLevel(level);
	}
	
	public void setLevel(byte level) {
		this.level = level;
		
		setPacketData();
	}
	
	private void setPacketData() {
		byte[] payload = { this.level };

		setData(payload);
	}
}

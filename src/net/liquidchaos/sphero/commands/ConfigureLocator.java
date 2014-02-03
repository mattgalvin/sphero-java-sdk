package net.liquidchaos.sphero.commands;

public class ConfigureLocator extends CommandPacket {

	static final byte DID = 0x02;
	static final byte CID = 0x13;

	boolean autoCorrectYawTare;
	short x;
	short y;
	short yawTare;

	public ConfigureLocator(boolean autoCorrectYawTare, short x, short y, short yawTare) {
		this(true, autoCorrectYawTare, x, y, yawTare);
	}

	public ConfigureLocator(boolean respond, boolean autoCorrectYawTare, short x, short y, short yawTare) {
		this(respond, (byte) 0x00, autoCorrectYawTare, x, y, yawTare);
	}

	public ConfigureLocator(boolean respond, byte sequence, boolean autoCorrectYawTare, short x, short y, short yawTare) {
		super(respond, DID, CID, sequence);

		setAutoCorrectYawTare(autoCorrectYawTare);
		setPosition(x, y);
		setYawTare(yawTare);
	}

	public void setAutoCorrectYawTare(boolean autoCorrectYawTare) {
		this.autoCorrectYawTare = autoCorrectYawTare;
		
		setPacketData();
	}
	
	public void setPosition(short x, short y) {
		this.x = x;
		this.y = y;
		
		setPacketData();
	}
	
	public void setYawTare(short yawTare) {
		this.yawTare = yawTare;
		
		setPacketData();
	}

	private void setPacketData() {
		
		
		byte[] payload = { autoCorrectYawTare ? (byte)1 : (byte)0,
							(byte)(x >> 8),
							(byte)x,
							(byte)(y >> 8),
							(byte)y,
							(byte)(yawTare >> 8),
							(byte)yawTare };

		setData(payload);
	}
}

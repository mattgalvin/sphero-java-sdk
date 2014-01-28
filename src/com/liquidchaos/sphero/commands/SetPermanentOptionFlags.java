package com.liquidchaos.sphero.commands;

public class SetPermanentOptionFlags extends CommandPacket {

	static final byte DID = 0x02;
	static final byte CID = 0x35;

	private boolean sleepInChargerEnabled;
	private boolean vectorDriveEnabled;
	private boolean chargerSelfLevelEnabled;
	private boolean tailLightAlwaysOn;
	private boolean motionTimeoutEnabled;
	private boolean retailModeEnabled;
	private boolean tapAwakeLight;
	private boolean gyroMaxAsynchMsgEnabled;

	public SetPermanentOptionFlags(boolean sleepInChargerEnabled,
								   boolean vectorDriveEnabled,
								   boolean chargerSelfLevelEnabled,
								   boolean tailLightAlwaysOn,
								   boolean motionTimeoutEnabled,
								   boolean retailModeEnabled,
								   boolean tapAwakeLight,
								   boolean gyroMaxAsynchMsgEnabled) {
		this((byte)0x00, sleepInChargerEnabled,
						 vectorDriveEnabled,
						 chargerSelfLevelEnabled,
						 tailLightAlwaysOn,
						 motionTimeoutEnabled,
						 retailModeEnabled,
						 tapAwakeLight,
						 gyroMaxAsynchMsgEnabled);
	}
	
	public SetPermanentOptionFlags(byte sequence,
								   boolean sleepInChargerEnabled,
								   boolean vectorDriveEnabled,
								   boolean chargerSelfLevelEnabled,
								   boolean tailLightAlwaysOn,
								   boolean motionTimeoutEnabled,
								   boolean retailModeEnabled,
								   boolean tapAwakeLight,
								   boolean gyroMaxAsynchMsgEnabled) {
		super(false, DID, CID, sequence);
		
		this.sleepInChargerEnabled = sleepInChargerEnabled;
		this.vectorDriveEnabled = vectorDriveEnabled;
		this.chargerSelfLevelEnabled = chargerSelfLevelEnabled;
		this.tailLightAlwaysOn = tailLightAlwaysOn;
		this.motionTimeoutEnabled = motionTimeoutEnabled;
		this.retailModeEnabled = retailModeEnabled;
		this.tapAwakeLight = tapAwakeLight;
		this.gyroMaxAsynchMsgEnabled = gyroMaxAsynchMsgEnabled;
		
		setPacketData();
	}
	
	public void setSleepInChargerEnabled(boolean sleepInChargerEnabled) {
		this.sleepInChargerEnabled = sleepInChargerEnabled;
		setPacketData();
	}

	public void setVectorDriveEnabled(boolean vectorDriveEnabled) {
		this.vectorDriveEnabled = vectorDriveEnabled;
		setPacketData();
	}

	public void setChargerSelfLevelEnabled(boolean chargerSelfLevelEnabled) {
		this.chargerSelfLevelEnabled = chargerSelfLevelEnabled;
		setPacketData();
	}

	public void setTailLightAlwaysOn(boolean tailLightAlwaysOn) {
		this.tailLightAlwaysOn = tailLightAlwaysOn;
		setPacketData();
	}

	public void setMotionTimeoutEnabled(boolean motionTimeoutEnabled) {
		this.motionTimeoutEnabled = motionTimeoutEnabled;
		setPacketData();
	}

	public void setRetailModeEnabled(boolean retailModeEnabled) {
		this.retailModeEnabled = retailModeEnabled;
		setPacketData();
	}

	public void setTapAwakeLight(boolean tapAwakeLight) {
		this.tapAwakeLight = tapAwakeLight;
		setPacketData();
	}

	public void setGyroMaxAsynchMsgEnabled(boolean gyroMaxAsynchMsgEnabled) {
		this.gyroMaxAsynchMsgEnabled = gyroMaxAsynchMsgEnabled;
		setPacketData();
	}

	private void setPacketData() {
		byte bits0_7 = 0b00000000;
		byte bits7_15 = 0b00000000;
		byte bits16_23 = 0b00000000;
		byte bits24_31 = 0b00000000;
		
		if (!sleepInChargerEnabled)		{ bits0_7 |= 0b00000001; }
		if (vectorDriveEnabled)			{ bits0_7 |= 0b00000010; }
		if (!chargerSelfLevelEnabled)	{ bits0_7 |= 0b00000100; }
		if (tailLightAlwaysOn)			{ bits0_7 |= 0b00001000; }
		if (motionTimeoutEnabled)		{ bits0_7 |= 0b00010000; }
		if (retailModeEnabled)			{ bits0_7 |= 0b00100000; }
		
		if (tapAwakeLight)				{ bits0_7 |= 0b01000000; }
		else							{ bits0_7 |= 0b10000000; }

		if (gyroMaxAsynchMsgEnabled)	{ bits7_15 |= 0b00000001; }
		
		byte[] payload = { bits24_31, bits16_23, bits7_15, bits0_7 };

		setData(payload);
	}
}

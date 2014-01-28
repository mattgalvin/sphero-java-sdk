package com.liquidchaos.sphero.responses;

import java.io.IOException;
import java.io.InputStream;

public class GetPermanentOptionFlagsResponse extends ResponsePacket {
	
	private boolean sleepInChargerEnabled;
	private boolean vectorDriveEnabled;
	private boolean chargerSelfLevelEnabled;
	private boolean tailLightAlwaysOn;
	private boolean motionTimeoutEnabled;
	private boolean retailModeEnabled;
	private boolean tapAwakeLight;
	private boolean gyroMaxAsynchMsgEnabled;
	
	private GetPermanentOptionFlagsResponse() {
		super();
	}
	
	/** Whether the Sphero will immediately go to sleep when placed in the charger and
	 * connected to bluetooth.
	 */
	public boolean isSleepInChargerEnabled() {
		return sleepInChargerEnabled;
	}

	/** Is Vector Drive enabled, that is, when Sphero is stopped and a new roll command is
	 * issued it achieves the heading before moving along it.
	 */
	public boolean isVectorDriveEnabled() {
		return vectorDriveEnabled;
	}
	
	/**
	 * Whether the Sphero will self level when placed in the charger.
	 */
	public boolean isChargerSelfLevelEnabled() {
		return chargerSelfLevelEnabled;
	}

	/**
	 * Whether the tail light is always on.
	 */
	public boolean isTailLightAlwaysOn() {
		return tailLightAlwaysOn;
	}

	/**
	 * Whether motion timeouts (see (see DID 02h, CID 34h) are enabled.
	 */
	public boolean isMotionTimeoutEnabled() {
		return motionTimeoutEnabled;
	}

	/**
	 * Whether retail demo dmde (when placed in the charger, ball runs
	 * a slow rainbow macro for 60 minutes and then goes to sleep) is enabled.
	 */
	public boolean isRetailModeEnabled() {
		return retailModeEnabled;
	}

	/**
	 * Whether double tap away sensitivity is light or heavy.
	 */
	public boolean isTapAwakeLight() {
		return tapAwakeLight;
	}

	/**
	 * Whether gyro max async message is enabled. (NOT SUPPORTED IN VERSION 1.47)
	 */
	public boolean isGyroMaxAsynchMsgEnabled() {
		return gyroMaxAsynchMsgEnabled;
	}

	public static ResponsePacket load(InputStream input) throws IOException {
		GetPermanentOptionFlagsResponse response = new GetPermanentOptionFlagsResponse();
		response.loadData(input);
		
		if (response.data.size() == 4) {
			// The response is a 32-bit response returned as 4 bytes from the loader.  Since we're just doing bitwise
			// comparisons, there's no need to construct a single integer, we'll just compare each byte.
			// NOTE: Some of the properties in this class are the inverse of the sphero values.  For example, bit 1
			// is set when sleep in charger is disabled but this class reports true if enabled.  So some of the bitwise
			// comparisons will be NOT'd.
			
			// Start with the least significant byte, which is the last in the data collection
			// Bits 0-7
			response.sleepInChargerEnabled = !((response.data.get(3) & 0b00000001) == 0b00000001);
			response.vectorDriveEnabled = (response.data.get(3) & 0b00000010) == 0b00000010;
			response.chargerSelfLevelEnabled = !((response.data.get(3) & 0b00000100) == 0b00000100);
			response.tailLightAlwaysOn = (response.data.get(3) & 0b00001000) == 0b00001000;
			response.motionTimeoutEnabled = (response.data.get(3) & 0b00010000) == 0b00010000;
			response.retailModeEnabled = (response.data.get(3) & 0b00100000) == 0b00100000;
			response.tapAwakeLight = (response.data.get(3) & 0b01000000) == 0b01000000;
			// Skip bit 7 = it's the inverse of bit 6
			
			// Bit 8
			response.gyroMaxAsynchMsgEnabled = (response.data.get(2) & 0b00000001) == 0b00000001;
			
			// Bits 9-31 are not currently used
		}
		
		return response;
	}
	
	public String toString() {
		return String.format("[sleepInChargerEnabled: %s, vectorDriveEnabled: %s, chargerSelfLevelEnabled: %s, tailLightAlwaysOn: %s, motionTimeoutEnabled: %s, retailModeEnabled: %s, tapAwakeLight: %s, gyroMaxAsynchMsgEnabled: %s]", sleepInChargerEnabled, vectorDriveEnabled, chargerSelfLevelEnabled, tailLightAlwaysOn, motionTimeoutEnabled, retailModeEnabled, tapAwakeLight, gyroMaxAsynchMsgEnabled);
	}

}

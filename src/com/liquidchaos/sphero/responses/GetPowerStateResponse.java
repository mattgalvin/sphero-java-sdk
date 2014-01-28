package com.liquidchaos.sphero.responses;

import java.io.IOException;
import java.io.InputStream;

public class GetPowerStateResponse extends ResponsePacket {
	private byte recordVersion; // Record version code
	private byte power; // High-level state of the power system as concluded by the power manager: 01h = Battery Charging, 02h = Battery OK, 03h = Battery Low, 04h = Battery Critical
	private int batteryVoltage; // Current battery voltage scaled in 100ths of a volt; 02EFh would be 7.51 volts (unsigned 16-bit value)
	private int numCharges; // Number of battery recharges in the life of this Sphero (unsigned 16-bit value)
	private int timeSinceCharge; // Seconds awake since last recharge (unsigned 16-bit value)
	
	private GetPowerStateResponse() {
		super();
	}
	
	public byte getRecordVersion() {
		return recordVersion;
	}

	public byte getPower() {
		return power;
	}

	public float getBatteryVoltage() {
		return batteryVoltage/100.f;
	}

	public int getNumCharges() {
		return numCharges;
	}

	public int getTimeSinceCharge() {
		return timeSinceCharge;
	}

	public static ResponsePacket load(InputStream input) throws IOException {
		GetPowerStateResponse response = new GetPowerStateResponse();
		response.loadData(input);
		
		if (response.data.size() == 8) {
			response.recordVersion = response.data.get(0);
			response.power = response.data.get(1);
	
			response.batteryVoltage = response.data.get(2) & 0xFF;
			response.batteryVoltage = response.batteryVoltage << 8;
			response.batteryVoltage |= response.data.get(3) & 0xFF;
	
			response.numCharges = response.data.get(4) & 0xFF;
			response.numCharges = response.numCharges << 8;
			response.numCharges |= response.data.get(5) & 0xFF;
			
			response.timeSinceCharge = response.data.get(6) & 0xFF;
			response.timeSinceCharge = response.timeSinceCharge << 8;
			response.timeSinceCharge |= response.data.get(7) & 0xFF;
		}
		
		return response;
	}
	
	public String toString() {
		return String.format("[recordVersion: %02x, power: %02x, batteryVoltage: %.2f, numCharges: %d, timeSinceCharg: %d]", getRecordVersion(), getPower(), getBatteryVoltage(), getNumCharges(), getTimeSinceCharge());
	}

}

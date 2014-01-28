package com.liquidchaos.sphero;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liquidchaos.sphero.bluetooth.SpheroBT;
import com.liquidchaos.sphero.commands.GetPowerState;
import com.liquidchaos.sphero.commands.Ping;
import com.liquidchaos.sphero.commands.SetBackLEDOutput;
import com.liquidchaos.sphero.commands.SetLEDColor;
import com.liquidchaos.sphero.responses.GetPowerStateResponse;
import com.liquidchaos.sphero.responses.ResponsePacket;

public class Sphero implements SpheroMessageListener {

	final Logger logger = LoggerFactory.getLogger("Sphero");
	
	SpheroBT spheroBT;
	
	GetPowerStateResponse powerState;

	public Sphero(String spheroAddress) throws IOException {
		spheroBT = new SpheroBT(spheroAddress);
		spheroBT.connect();
		
		getPowerState();

		logger.info("Sphero connected");
		logger.info(String.format("Power: %s", powerState));
	}
	
	public void ping() throws IOException {
		spheroBT.sendCommand(new Ping());
	}
	
	public void setLEDColor(byte red, byte green, byte blue, boolean persist) throws IOException {
		spheroBT.sendCommand(new SetLEDColor(red, green, blue, persist));
	}
	
	public void setBackLEDOutput(byte level) throws IOException {
		spheroBT.sendCommand(new SetBackLEDOutput(level));
	}
	
	public void getPowerState() throws IOException {
		powerState = (GetPowerStateResponse) spheroBT.sendCommand(new GetPowerState(true));
	}
	
	@Override
	public void handleSpheroResponse(ResponsePacket response) {
		// TODO Auto-generated method stub
		
	}

}

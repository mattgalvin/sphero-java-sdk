package net.liquidchaos.sphero;

import java.io.IOException;

import net.liquidchaos.sphero.bluetooth.SpheroBT;
import net.liquidchaos.sphero.commands.GetPermanentOptionFlags;
import net.liquidchaos.sphero.commands.GetPowerState;
import net.liquidchaos.sphero.commands.Ping;
import net.liquidchaos.sphero.commands.SetBackLEDOutput;
import net.liquidchaos.sphero.commands.SetLEDColor;
import net.liquidchaos.sphero.commands.SetPermanentOptionFlags;
import net.liquidchaos.sphero.responses.GetPermanentOptionFlagsResponse;
import net.liquidchaos.sphero.responses.GetPowerStateResponse;
import net.liquidchaos.sphero.responses.ResponsePacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sphero implements SpheroMessageListener {

	final Logger logger = LoggerFactory.getLogger("Sphero");
	
	SpheroBT spheroBT;
	
	GetPowerStateResponse powerState;
	GetPermanentOptionFlagsResponse permanentOptions;

	public Sphero(String spheroAddress) throws IOException {
		spheroBT = new SpheroBT(spheroAddress);
		spheroBT.connect();
		
		getPowerState();
		getPermanentOptionFlags();

		logger.info("Sphero connected");
		logger.info(String.format("Power: %s", powerState));
		logger.info(String.format("Options Flags: %s", permanentOptions));
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
		powerState = (GetPowerStateResponse) spheroBT.sendCommand(new GetPowerState());
	}

	public void getPermanentOptionFlags() throws IOException {
		permanentOptions = (GetPermanentOptionFlagsResponse) spheroBT.sendCommand(new GetPermanentOptionFlags());
	}
	
	/**
	 * Null values for values will remain unchanged on the Sphero
	 */
	public void setPermanentOptionFlags(Boolean sleepInChargerEnabled,
										Boolean vectorDriveEnabled,
										Boolean chargerSelfLevelEnabled,
										Boolean tailLightAlwaysOn,
										Boolean motionTimeoutEnabled,
										Boolean retailModeEnabled,
										Boolean tapAwakeLight,
										Boolean gyroMaxAsynchMsgEnabled) throws IOException {
		// First refresh the stored values;
		getPermanentOptionFlags();
		
		SetPermanentOptionFlags command = new SetPermanentOptionFlags(permanentOptions.isSleepInChargerEnabled(),
																	  permanentOptions.isVectorDriveEnabled(),
																	  permanentOptions.isChargerSelfLevelEnabled(),
																	  permanentOptions.isTailLightAlwaysOn(),
																	  permanentOptions.isMotionTimeoutEnabled(),
																	  permanentOptions.isRetailModeEnabled(),
																	  permanentOptions.isTapAwakeLight(),
																	  permanentOptions.isGyroMaxAsynchMsgEnabled());
		
		if (sleepInChargerEnabled != null) { command.setSleepInChargerEnabled(sleepInChargerEnabled.booleanValue()); }
		if (vectorDriveEnabled != null) { command.setVectorDriveEnabled(vectorDriveEnabled.booleanValue()); }
		if (chargerSelfLevelEnabled != null) { command.setChargerSelfLevelEnabled(chargerSelfLevelEnabled.booleanValue()); }
		if (tailLightAlwaysOn != null) { command.setTailLightAlwaysOn(tailLightAlwaysOn.booleanValue()); }
		if (motionTimeoutEnabled != null) { command.setMotionTimeoutEnabled(motionTimeoutEnabled.booleanValue()); }
		if (retailModeEnabled != null) { command.setRetailModeEnabled(retailModeEnabled.booleanValue()); }
		if (tapAwakeLight != null) { command.setTapAwakeLight(tapAwakeLight.booleanValue()); }
		if (gyroMaxAsynchMsgEnabled != null) { command.setGyroMaxAsynchMsgEnabled(gyroMaxAsynchMsgEnabled.booleanValue()); }

		spheroBT.sendCommand(command);
		
		getPermanentOptionFlags();
	}
	
	@Override
	public void handleSpheroResponse(ResponsePacket response) {
		// TODO Auto-generated method stub
		
	}

}

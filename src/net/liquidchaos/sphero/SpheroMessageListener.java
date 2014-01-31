package net.liquidchaos.sphero;

import java.util.EventListener;

import net.liquidchaos.sphero.responses.ResponsePacket;

public interface SpheroMessageListener extends EventListener {

	public void handleSpheroResponse(ResponsePacket response);
}

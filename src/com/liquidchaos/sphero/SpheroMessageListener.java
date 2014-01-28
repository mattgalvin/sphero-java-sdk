package com.liquidchaos.sphero;

import java.util.EventListener;

import com.liquidchaos.sphero.responses.ResponsePacket;

public interface SpheroMessageListener extends EventListener {

	public void handleSpheroResponse(ResponsePacket response);
}

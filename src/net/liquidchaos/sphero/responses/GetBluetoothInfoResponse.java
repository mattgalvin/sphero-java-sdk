package net.liquidchaos.sphero.responses;

import java.io.IOException;
import java.io.InputStream;

public class GetBluetoothInfoResponse extends ResponsePacket {
	
	private String name;
	private String address;
	private String colors;
	
	private GetBluetoothInfoResponse() {
		super();
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getColors() {
		return colors;
	}

	public static ResponsePacket load(InputStream input) throws IOException {
		GetBluetoothInfoResponse response = new GetBluetoothInfoResponse();
		response.loadData(input);
		
		if (response.data.size() == 32) {
			byte[] str = new byte[32];
			for (int i=0; i<32; i++) {
				str[i] = response.data.get(i);
			}
			
			response.name = new String(str, 0, 16).trim();
			response.address = new String(str, 16, 12).trim();
			response.colors = new String(str, 29, 3).trim();
		}
		
		return response;
	}
	
	public String toString() {
		return String.format("[name: %s, address: %s, colors: %s]", name, address, colors);
	}

}

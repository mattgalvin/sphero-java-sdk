package com.liquidchaos.sphero.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.bluetooth.BluetoothConnectionException;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liquidchaos.sphero.SpheroMessageListener;
import com.liquidchaos.sphero.commands.CommandPacket;
import com.liquidchaos.sphero.responses.ResponsePacket;

public class SpheroBT implements DiscoveryListener {

	final Logger logger = LoggerFactory.getLogger("SpheroBT");
	
	private static Object lock = new Object();

	private Vector<RemoteDevice> discoveredDevices = new Vector<RemoteDevice>();
	private String btConnectionUrl;

	private String btAddress;
	private StreamConnection streamConnection;
	private InputStream inStream;
	private OutputStream outStream;
	
	private Vector<SpheroMessageListener> messageListeners = new Vector<SpheroMessageListener>();
	
	public SpheroBT(String btAddress) {
		this.btAddress = btAddress;
	}
	
	public void addMessageListener(SpheroMessageListener listener) {
		messageListeners.add(listener);
	}
	
	public void removeMessageListener(SpheroMessageListener listener) {
		messageListeners.remove(listener);
	}
	
	public void connect() throws BluetoothConnectionException, BluetoothStateException, IOException {
		LocalDevice localDevice = LocalDevice.getLocalDevice();

		// find devices
		DiscoveryAgent agent = localDevice.getDiscoveryAgent();

		logger.debug("Starting device inquiry.");
		agent.startInquiry(DiscoveryAgent.GIAC, this);

		try {
			synchronized (lock) {
				lock.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.debug("Device inquiry completed.");

		// print all devices in vecDevices
		int deviceCount = discoveredDevices.size();

		RemoteDevice remoteDevice = null;
		if (deviceCount <= 0) {
			logger.error("No bluetooth devices found.");
			return;
		} else {
			logger.debug(String.format("Found %d, searching for %s.", deviceCount, btAddress));
			for (int i = 0; i < deviceCount; i++) {
				RemoteDevice rd = (RemoteDevice) discoveredDevices.elementAt(i);
				if (rd.getBluetoothAddress().equals(btAddress)) {
					remoteDevice = rd;
					logger.debug(String.format("Found %s.", remoteDevice.getFriendlyName(true)));
				}
			}
		}
		
		if (remoteDevice == null) {
			logger.error(String.format("Could not find device %s.", btAddress));
			throw new BluetoothConnectionException(SERVICE_SEARCH_DEVICE_NOT_REACHABLE);
		}

		UUID[] uuidSet = new UUID[1];
		uuidSet[0] = new UUID("1101", true);

		logger.debug("Searching for SPP service.");
		agent.searchServices(null, uuidSet, remoteDevice, this);

		try {
			synchronized (lock) {
				lock.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (btConnectionUrl == null) {
			logger.error(String.format("Device %s does not support simple SPP service.", remoteDevice.getFriendlyName(true)));
			throw new BluetoothConnectionException(SERVICE_SEARCH_ERROR, "Could not find Simple SPP service.");
		}

		// connect to the server
		streamConnection = (StreamConnection) Connector.open(btConnectionUrl);
		outStream = streamConnection.openOutputStream();
		inStream = streamConnection.openInputStream();
	}
	
	public ResponsePacket sendCommand(CommandPacket commandPacket) throws IOException {
		if (outStream == null) {
			throw new IOException("Outputstream is not opened.");
		}
		
		logger.debug(String.format("Sending command packet: %s", commandPacket.toString()));
		outStream.write(commandPacket.getBytePacket());
		outStream.flush();

		ResponsePacket responsePkt = null;
		
		if (commandPacket.expectsResponse()) {
			logger.debug("Waiting for response.");
			responsePkt = commandPacket.loadResponse(inStream);
			logger.debug(String.format("Received response packet: %s", responsePkt.toString()));
		}
		
		return responsePkt;
	}

	@Override
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass arg1) {
		if (!discoveredDevices.contains(btDevice)) {
			discoveredDevices.addElement(btDevice);
		}
	}

	@Override
	public void inquiryCompleted(int arg0) {
		synchronized (lock) {
			lock.notify();
		}
	}

	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		synchronized (lock) {
			lock.notify();
		}
	}

	@Override
	public void servicesDiscovered(int transId, ServiceRecord[] svcRecord) {
		if (svcRecord != null && svcRecord.length > 0) {
			btConnectionUrl = svcRecord[0].getConnectionURL(0, false);
		}
		synchronized (lock) {
			lock.notify();
		}
	}

//	private class MessageHandler implements Runnable {
//
//	    private Thread thread;
//	    private SpheroBT spheroBT;
//		
//		public MessageHandler(SpheroBT spheroBT) {
//			this.spheroBT = spheroBT;
//		}
//
//		@Override
//		public void run() {
//	        while (thread != null) {
//	        	
//	        }
//		}
//
//	    public void start() {
//	        thread = new Thread(this);
//	        thread.setName("SpheroBTMessageHandler");
//	        thread.start();
//	    }
//
//	    public void stop() {
//	        if (thread != null) {
//	            thread.interrupt();
//	        }
//	        thread = null;
//	    }
//	}
}

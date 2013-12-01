package bt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import npu.robotcontrol.WebActivity;
import ui.ToastThread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BlueTooth extends Thread {

	private static final UUID uuid = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private static BluetoothAdapter bluetooth;
	private static BluetoothDevice remote;
	private static BluetoothSocket remoteSocket;
	private static OutputStream tmpOut;
	private static InputStream tmpIn;

	public static void connect() {
		bluetooth = BluetoothAdapter.getDefaultAdapter();
		if (bluetooth.isEnabled()) {
			// Bluetooth Address of BT module
			remote = bluetooth.getRemoteDevice("00:06:66:46:CC:F3");
			try {
				bluetooth.cancelDiscovery();
				remoteSocket = null;
				remoteSocket = remote.createRfcommSocketToServiceRecord(uuid);
				remoteSocket.connect();
				tmpOut = remoteSocket.getOutputStream();
				tmpIn = remoteSocket.getInputStream();
			} catch (Exception e) {
				WebActivity.webActivity.runOnUiThread(new ToastThread("BT.connect(): "+e.getMessage()));
				e.printStackTrace();
			}
		} else {
			WebActivity.webActivity.runOnUiThread(new ToastThread(
					"Enable the bluetooth Stupid!"));
		}
		if(remoteSocket.isConnected()){
			WebActivity.webActivity.runOnUiThread(new ToastThread(
					"BT connected"));
			
		}
		else{
			WebActivity.webActivity.runOnUiThread(new ToastThread(
					"BT not connected"));
		}
	}

	
	
	public static void send(String direction) {
		//Thread sendBtThread = new Thread(new sendThread(direction, tmpOut));
		//sendBtThread.start();
		//sendBtThread.run();
		try {
			tmpOut.write(Character.toLowerCase((direction.charAt(0))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

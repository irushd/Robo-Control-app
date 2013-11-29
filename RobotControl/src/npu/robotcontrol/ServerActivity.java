package npu.robotcontrol;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ServerActivity extends Activity {

	TextView textViewIP;

	TextView textViewRx;
	Button buttonStart;
	Button buttonBT;
	ServerThread serverThread;
	Handler updateGui;

	InputStream tmpIn = null;
	OutputStream tmpOut = null;
	BluetoothAdapter bluetooth;
	BluetoothDevice remote;
	BluetoothSocket remoteSocket;
	private static final UUID uuid1 = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private String provider;
	private boolean serverConnection = false;

	class toastThread implements Runnable {
		private String msg;

		public toastThread(String str) {
			this.msg = str;
		}

		public void run() {
			Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
		}
	}

	class signalThread implements Runnable {
		private String msg;

		public signalThread(String str) {
			this.msg = str;
		}

		public void run() {
			try {
				tmpOut.write(msg.charAt(0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class updateUIThread2 implements Runnable {
		private String msg;

		public updateUIThread2(String str) {
			this.msg = str;
		}

		public void run() {
			textViewIP.setText(msg);
		}
	}

	class updateUIThread implements Runnable {
		private String msg;

		public updateUIThread(String str) {
			this.msg = str;
		}

		public void run() {
			textViewRx.setText(msg);

		}
	}

	class ServerThread implements Runnable {
		ServerSocket serverSocket;
		DataInputStream incomingStream;
		String signal;

		public void run() {
			Socket socket = null;
			try {
				serverSocket = new ServerSocket(8989);
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (!Thread.currentThread().isInterrupted()) {
				try {
					// textViewRx.setText("About to start");
					updateGui.post(new signalThread(InetAddress
							.getLocalHost().toString()));
					socket = serverSocket.accept();
					updateGui.post(new updateUIThread(socket.toString()));
					incomingStream = new DataInputStream(
							socket.getInputStream());
					while (true) {
						signal = incomingStream.readUTF();
						updateGui.post(new signalThread(signal));
						updateGui.post(new toastThread(signal));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server);

		updateGui = new Handler();

		textViewIP = (TextView) findViewById(R.id.textViewIP);
		textViewRx = (TextView) findViewById(R.id.textViewReceived);
		buttonStart = (Button) findViewById(R.id.buttonStart);
		buttonBT = (Button) findViewById(R.id.buttonBT);
		buttonBT.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				bluetooth = BluetoothAdapter.getDefaultAdapter();
				if (bluetooth.isEnabled()) {
					String mydeviceaddress = bluetooth.getAddress();
					String mydevicename = bluetooth.getName();
					// Bluetooth Address of BT module
					remote = bluetooth.getRemoteDevice("00:06:66:46:CC:F3");
					try {
						bluetooth.cancelDiscovery();
						remoteSocket = null;
						remoteSocket = remote
								.createRfcommSocketToServiceRecord(uuid1);
						remoteSocket.connect();
						tmpOut = remoteSocket.getOutputStream();
						tmpIn = remoteSocket.getInputStream();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
				}
			}
		});
		buttonStart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				serverThread = new ServerThread();
				Thread threadRunner = new Thread(serverThread);
				threadRunner.start();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.server, menu);
		return true;
	}

}

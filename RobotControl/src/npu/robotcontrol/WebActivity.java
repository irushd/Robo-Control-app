package npu.robotcontrol;

import java.net.MalformedURLException;
import java.net.URL;

import bt.BlueTooth;

import ui.ToastThread;
import web.WebService;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class WebActivity extends Activity {

	public static WebActivity webActivity;
	public Switch switchBT;
	Switch switchWebService;
	public TextView textDirection;
	WebService webServiceThread;
	Thread blueToothThread;
	private BroadcastReceiver mReceiver;
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		context = getApplicationContext();
		webActivity = this;

		switchBT = (Switch) findViewById(R.id.switchBT);
		switchWebService = (Switch) findViewById(R.id.switchWebService);
		textDirection = (TextView) findViewById(R.id.textDirection);
		switchBT.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// Start the webservice thread to start checking and setting
					// ToastThread.Toastit("BT ON");

					BlueTooth.connect();
				//	IntentFilter filter = new IntentFilter(
				//			BluetoothAdapter.ACTION_STATE_CHANGED);
			//		webActivity.registerReceiver(mReceiver, filter);
				} else {
					// ToastThread.Toastit("BT OFF");
					// Stop the webservice thread to stop checking and reset to
					// stop
					webActivity.unregisterReceiver(mReceiver);
				}

			}
		});
		switchWebService
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							// Start the webservice thread to start checking and
							// setting
							// ToastThread.Toastit("WebService ON");
							webServiceThread = new WebService();
							webServiceThread.start();
						} else {
							webServiceThread.interrupt();
							// ToastThread.Toastit("WebService OFF");
							// Stop the webservice thread to stop checking and
							// reset to stop
						}

					}
				});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web, menu);
		return true;
	}

}

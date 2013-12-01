package ui;

import npu.robotcontrol.WebActivity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class DirectionShowThread extends Thread {
	public static WebActivity activity;

	private String msg;

	public DirectionShowThread(String str) {
		this.msg = "" + str;
		activity = WebActivity.webActivity;
	}

	public void run() {

		activity.textDirection.setText(msg);

	}
}

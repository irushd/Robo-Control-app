package ui;


import npu.robotcontrol.WebActivity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;


public class ToastThread extends Thread {
	public static Handler toaster;
	public static void Toastit(String msg){
		if(toaster == null)toaster = new Handler();	
		ToastThread toast = new ToastThread(" "+msg);
		toaster.post(toast);
	}
	
	private String msg;
	private Context context;
	public ToastThread(String str) {
		this.msg = ""+str;
	}

	public void run() {
		Toast.makeText(WebActivity.context, msg, Toast.LENGTH_SHORT).show();
	}
}

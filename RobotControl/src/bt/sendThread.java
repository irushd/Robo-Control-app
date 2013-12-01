package bt;

import java.io.IOException;
import java.io.OutputStream;
import npu.robotcontrol.WebActivity;

import ui.ToastThread;


public class sendThread extends Thread {
	private String msg;
	private static OutputStream tmpOut;

	public sendThread(String str, OutputStream out) {
		this.msg = str;
		tmpOut = out;
	}

	public void run() {
		try {
		//	for(char cmd : msg.toCharArray()){
			tmpOut.write(Character.toLowerCase((msg.charAt(0))));
		//	Thread.sleep(10);
		//	}
		} catch (IOException e) {
			WebActivity.webActivity.runOnUiThread(new ToastThread("BT:sendThread class: "+e.getMessage()));
			e.printStackTrace();
	//	} catch (InterruptedException e) {
			WebActivity.webActivity.runOnUiThread(new ToastThread("BT:sendThread class: "+e.getMessage()));
			e.printStackTrace();
		}
	}
}

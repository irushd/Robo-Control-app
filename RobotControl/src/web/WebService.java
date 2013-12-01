package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bt.BlueTooth;
import npu.robotcontrol.WebActivity;
import ui.DirectionShowThread;
import ui.ToastThread;

public class WebService extends Thread {
	URL url;
	HttpURLConnection webConnection;
	String receivedText = "zzzzzz";
	BufferedReader reader;

	public WebService() {
		try {
			url = new URL(
					"http://stryker.jelastic.servint.net/Robot/RobotController");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		String previous="";
		boolean BTconnection = WebActivity.webActivity.switchBT.isChecked();
		boolean keepRunning = true;
		while (keepRunning) {
			try {				
				webConnection = (HttpURLConnection) url.openConnection();
				reader = new BufferedReader(new InputStreamReader(webConnection.getInputStream()));
				receivedText = reader.readLine();
				if(BTconnection){
					if(!receivedText.equals(previous)){
					BlueTooth.send(receivedText);
					previous = receivedText;
					}
				}
				WebActivity.webActivity.runOnUiThread(new DirectionShowThread(receivedText));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				webConnection = null;
				try {
					if(reader!=null){
					reader.close();
					}
					else{
						WebActivity.webActivity.runOnUiThread(new ToastThread("WebService: reader buffer couldnt initialize"));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			BTconnection = WebActivity.webActivity.switchBT.isChecked();
			if (isInterrupted()){
				keepRunning = false;
			}
		}
	}
}
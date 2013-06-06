package npu.robotcontrol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import android.os.Bundle;
import android.os.ParcelUuid;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
//import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.View;


import android.bluetooth.*;	///Bluetooth API is imported



public class Control extends Activity {

	Button aButton;
	//I roughly tested the buttons with a print statements
	//All "view v" class within public void onClick (...) should be replaced
	// by the bluetooth stuff
	InputStream tmpIn = null;
    OutputStream tmpOut = null;
	BluetoothAdapter bluetooth;
	BluetoothDevice remote;
	BluetoothSocket remoteSocket;
	private static final UUID uuid1 = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // fromString("0x0000000000001000800000805F9B34FB");
//	private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); 00001101-0000-1000-8000-00805F9B34FB
    @Override
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
    
        aButton = (Button) this.findViewById(R.id.button1);
    	aButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v){
    			aButton.setText("Robot moves up");
    			 try {
    				 tmpOut = remoteSocket.getOutputStream();
    	             tmpIn = remoteSocket.getInputStream();
    				 tmpOut.write('f');
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}    	            
    		}
    		}); 
    
    
    
    	aButton = (Button) this.findViewById(R.id.button2);
    	aButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v){
    			//whatever you want to do goes here
    			aButton.setText("Robot moves Left");
    			 try {
				 tmpOut = remoteSocket.getOutputStream();
	             tmpIn = remoteSocket.getInputStream();
				 tmpOut.write('l');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	            
    		}
    		}); 
    
    
    
    	aButton = (Button) this.findViewById(R.id.button3);
    	aButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v){
    			//whatever you want to do goes here
    			aButton.setText("Robot moves Right");
    			 try {
    				 tmpOut = remoteSocket.getOutputStream();
    	             tmpIn = remoteSocket.getInputStream();
    				 tmpOut.write('r');
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    		}); 
    

    	aButton = (Button) this.findViewById(R.id.button6);
    	aButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v){
    			//whatever you want to do goes here
    			aButton.setText("Stopped");
    			try {
					tmpOut = remoteSocket.getOutputStream();
					tmpIn = remoteSocket.getInputStream();
					 tmpOut.write('s');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	             
    		}
    		});
    	
    	
    	
    	aButton = (Button) this.findViewById(R.id.button4);
    	aButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v){
    			//whatever you want to do goes here
    			aButton.setText("Robot moves Down");
    			 try {
    				 tmpOut = remoteSocket.getOutputStream();
    	             tmpIn = remoteSocket.getInputStream();
    				 tmpOut.write('b');
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    		}); 
    	
    	
    	
    	aButton = (Button) this.findViewById(R.id.button5);
    	aButton.setOnClickListener(new OnClickListener() {
    		/* (non-Javadoc)
    		 * @see android.view.View.OnClickListener#onClick(android.view.View)
    		 */
    		public void onClick(View v){
    			//whatever you want to do goes here
    			//////////////////////
    			BluetoothServerSocket x;
    			bluetooth = BluetoothAdapter.getDefaultAdapter();
    			String status;
    			if (bluetooth.isEnabled()) {
    			    String mydeviceaddress = bluetooth.getAddress();
    			    String mydevicename = bluetooth.getName();
    			    //status = mydevicename + " :" + mydeviceaddress;
    			   // remote=bluetooth.getRemoteDevice("90:4C:EF:F7:5C:64");	//Bluetooth Adress of pc
    			   remote=bluetooth.getRemoteDevice("00:06:66:46:CC:F3");	//Bluetooth Address of MODULE
    			   
    			  try {
      			 	bluetooth.cancelDiscovery();
    				 remoteSocket = null;
    			
    				 remoteSocket = remote.createRfcommSocketToServiceRecord(uuid1);
    	    				  remoteSocket.connect();
    	    			
    				 
    				  status= "Socket Created bit";
    				aButton.setText(status);
    			  } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					status = "Socket not created it"+e.toString();
					aButton.setText(status);
    			  }
				  
    			}
    			else
    			{
    			    status = "Bluetooth is not Enabled.";
    			    aButton.setText(status);
    			}
    			
    			
    			
    		
    		}
    		}); 
    	
    	
    	
    	aButton = (Button) this.findViewById(R.id.button6);
    	aButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v){
    			//whatever you want to do goes here
    			aButton.setText("Stopped");
    			try {
					tmpOut = remoteSocket.getOutputStream();
					tmpIn = remoteSocket.getInputStream();
					 tmpOut.write('s');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	             
    		}
    		}); 
    	
    	
    	
    	
    	
    	aButton = (Button) this.findViewById(R.id.button7);
    	aButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v){
    			//whatever you want to do goes here
    			
    			aButton.setText("Robot Disconnected");

    		}
    		}); 
    	
    	
    	
    	aButton = (Button) this.findViewById(R.id.button8);
    	aButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v){
    			//whatever you want to do goes here
    			aButton.setText("Status");

    		}
    		}); 
    	
    	
    	
    	
    	
    	
    }

    
 
    
    
    
    
    
    
    
    
    
    // @Override
    //public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.activity_control, menu);
        //return true;
    //}
}

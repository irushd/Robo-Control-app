package npu.robotcontrol;
//Testing the push
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.bool;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.util.Log;
//import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.View;


import android.bluetooth.*;	///Bluetooth API is imported
import android.content.Context;

public class Control extends Activity implements LocationListener {

	Button aButton;
	//I roughly tested the buttons with a print statements
	//All "view v" class within public void onClick (...) should be replaced
	// by the bluetooth stuff
	InputStream tmpIn = null;
    OutputStream tmpOut = null;
	BluetoothAdapter bluetooth;
	BluetoothDevice remote;
	BluetoothSocket remoteSocket;
	private static final UUID uuid1 = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); 

	 private TextView latituteField;
	 private TextView longitudeField;
	 private LocationManager locationManager;
	 private String provider;
	 private boolean serverConnection=false;
	
	@Override   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        
        latituteField = (TextView) findViewById(R.id.textViewIP);//@+id/textView1
        longitudeField = (TextView) findViewById(R.id.textView2); //@+id/textView2
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
          //  System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
          } else {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");
          }
        
        aButton = (Button) this.findViewById(R.id.buttonStart);
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
    
    	Switch toggle = (Switch) findViewById(R.id.switchWebService); //@+id/switch1
    	toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	        if (isChecked) {
    	            serverConnection = true;
    	        	OpenHttpGETConnection("172.19.2.49:8080/RobotControl/GPS?first="+latituteField.getText()+"&second="+longitudeField.getText());    	        	  	        	
    	        } else {
    	        	serverConnection = false;
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
    			    Thread thread = new Thread(){	
    			    public void run(){
    			    OpenHttpGETConnection("http://192.168.1.1:8080/RobotControlWeb/GPS?first="
    			    		+String.valueOf(lat)+"&second="+String.valueOf(lng));    
    			    }	};  
    			    thread.start();
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

    
	@Override
	protected void onResume() {
		    super.onResume();
		    locationManager.requestLocationUpdates(provider, 400, 1, this);
		  }
	int lat;
	int lng;
	@Override
	protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	}
	
	  public void onLocationChanged(Location location) {
	    lat = (int) (location.getLatitude());
	    lng = (int) (location.getLongitude());
	    latituteField.setText(String.valueOf(lat));
	    longitudeField.setText(String.valueOf(lng));
	    if(serverConnection){
	        	OpenHttpGETConnection("http://172.19.2.49:8080/RobotControlWeb/GPS?first="
	        		+String.valueOf(lat)+"&second="+String.valueOf(lng));    
	        	
	    }
	  }
    
	  public void onProviderDisabled(String provider) {
		    Toast.makeText(this, "Disabled provider " + provider,
		        Toast.LENGTH_SHORT).show();
		  }
	  
	  public void onProviderEnabled(String provider) {
		    Toast.makeText(this, "Enabled new provider " + provider,
		        Toast.LENGTH_SHORT).show();

		  }
	  
	  
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	  }
    
	  public static InputStream OpenHttpGETConnection(String url) {
	        InputStream inputStream = null;
	        try {
	        	URI uri = new URI(url);
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpGet request = new HttpGet();
	            request.setURI(uri);	            
	            HttpResponse httpResponse = httpclient.execute(request);
	            inputStream = httpResponse.getEntity().getContent();
	        } catch (Exception e) {
	            Log.d("InputStream", e.getLocalizedMessage());
	        }
	        return inputStream;
	    }
    
    
    // @Override
    //public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.activity_control, menu);
        //return true;
    //}
}

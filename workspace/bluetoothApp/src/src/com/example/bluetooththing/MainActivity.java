package com.example.bluetooththing;

import android.os.Bundle;
import android.os.PowerManager;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	static final int DISCOVERY_REQUEST = 0;
	private ToggleButton toggleButton1, toggleButton2;
	private ListView myListView1;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        addListenerOnButton();
    }
    
    public void addListenerOnButton() {

		toggleButton1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				 // Is the toggle on?
		        boolean on = ((ToggleButton) v).isChecked();
		        
		        if (on) {
		        	setBluetooth(false);
		            // Enable checker
		        } else {
		        	setBluetooth(true);
		            // Disable checker
		        }

			}

		});
		
		toggleButton2.setOnClickListener(new View.OnClickListener() 
	    {
	        public void onClick(View v) 
	        {
	            ListView lv1 = (ListView) findViewById(R.id.myListView1);
	            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


	            final BroadcastReceiver mReceiver = new BroadcastReceiver() 
	            { 
	                public void onReceive(Context context, Intent intent) 
	                {
	                    String action = intent.getAction(); 
	                    // When discovery finds a device 
	                    if (BluetoothDevice.ACTION_FOUND.equals(action)) 
	                    {
	                    // Get the BluetoothDevice object from the Intent 
	                    BluetoothDevice device = intent.getParcelableExtra(
	                    BluetoothDevice.EXTRA_DEVICE);
	                    Log.v("BlueTooth Testing",device.getName() + "\n"
	                    + device.getAddress()); 
	                    }
	                }    
	            };

	            String aDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;
	            startActivityForResult(new Intent(aDiscoverable),DISCOVERY_REQUEST);
	            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND); 
	            registerReceiver(mReceiver, filter); 
	            mBluetoothAdapter.startDiscovery();
	        }
	    });
    }
    
    public boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable(); 
        }
        else if(!enable && isEnabled && !pm.isScreenOn()) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

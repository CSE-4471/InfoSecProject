package com.example.bluetooththing;

import android.os.Bundle;
import android.os.PowerManager;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    
    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();
        
        if (on) {
        	setBluetooth(false);
            // Enable checker
        } else {
        	setBluetooth(true);
            // Disable checker
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

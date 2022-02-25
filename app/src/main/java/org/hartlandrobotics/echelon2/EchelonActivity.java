package org.hartlandrobotics.echelon2;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.hartlandrobotics.echelon2.blueAlliance.BlueAllianceActivity;
import org.hartlandrobotics.echelon2.bluetooth.BluetoothSyncActivity;
import org.hartlandrobotics.echelon2.configuration.AdminSettings;

public abstract class EchelonActivity extends AppCompatActivity {

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void blueAlliancePressed(MenuItem item) {
        BlueAllianceActivity.launch(this);
    }

    public void settingsPressed(MenuItem item) {
        AdminSettingsActivity.launch(this);
    }
    public void homePressed(MenuItem item) {
        MainActivity.launch(this);
    }
    public void bluetoothPressed(MenuItem item) {
        BluetoothSyncActivity.launch(this);
    }
    public void exportPressed(MenuItem item){
        // launch export activity
    }
}


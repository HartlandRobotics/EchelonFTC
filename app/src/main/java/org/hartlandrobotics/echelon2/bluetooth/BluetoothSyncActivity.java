package org.hartlandrobotics.echelon2.bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.hartlandrobotics.echelon2.EchelonActivity;
import org.hartlandrobotics.echelon2.R;

public class BluetoothSyncActivity extends EchelonActivity {

    public static void launch(Context context){
        Intent intent = new Intent(context, BluetoothSyncActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_sync);

        setupToolbar();
    }
}
package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton startScouting;
    private MaterialButton adminSettings;
    private MaterialButton tbaStatus;

    public static void launch(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupStartScoutingButton();
        setupAdminSettingsButton();
        setupTbaStatusButton();
    }

    private void setupStartScoutingButton(){
        startScouting = this.findViewById(R.id.main_admin_start_scouting);
    }

    private void setupAdminSettingsButton(){
        adminSettings = this.findViewById(R.id.main_admin_settings);
        adminSettings.setOnClickListener( view -> AdminSettingsActivity.launch( MainActivity.this ) );
    }

    private void setupTbaStatusButton(){
        tbaStatus = this.findViewById(R.id.status);
        tbaStatus.setOnClickListener(view -> BlueAllianceActivity.launch(MainActivity.this));
    }
}
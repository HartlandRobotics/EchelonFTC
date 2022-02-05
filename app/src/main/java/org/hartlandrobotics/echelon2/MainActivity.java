package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelon2.TBA.TBAActivity;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

public class MainActivity extends AppCompatActivity {

    private BlueAllianceStatus status;

    private MaterialButton startScouting;
    private MaterialButton pitScouting;
    private MaterialButton adminSettings;
    private MaterialButton tbaStatus;
    private MaterialButton tabTest;

    public static void launch(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status= new BlueAllianceStatus(getApplicationContext());

        setupStartScoutingButton();
        setupPitScoutingButton();
        setupAdminSettingsButton();
        setupTbaStatusButton();

        setupStatus();
    }

    private void setupStartScoutingButton(){
        startScouting = this.findViewById(R.id.main_admin_start_scouting);
    }

    private void setupPitScoutingButton(){
        pitScouting = findViewById(R.id.pit_scout_button);
        pitScouting.setOnClickListener( v -> PitScoutActivity.launch(MainActivity.this ));
    }

    private void setupAdminSettingsButton(){
        adminSettings = this.findViewById(R.id.main_admin_settings);
        adminSettings.setOnClickListener( view -> AdminSettingsActivity.launch( MainActivity.this ) );
    }

    private void setupTbaStatusButton(){
        tbaStatus = this.findViewById(R.id.tba_button);
        tbaStatus.setOnClickListener(view -> TBAActivity.launch(MainActivity.this));
    }

    private void setupStatus(){
        TextInputLayout seasonLayout = findViewById(R.id.season_status_layout);
        seasonLayout.getEditText().setText(status.getSeason());

        TextInputLayout districtLayout = findViewById(R.id.district_status_layout);
        districtLayout.getEditText().setText(status.getDistrictKey());

        TextInputLayout eventLayout = findViewById(R.id.event_status_layout);
        eventLayout.getEditText().setText(status.getEventKey());

    }
}
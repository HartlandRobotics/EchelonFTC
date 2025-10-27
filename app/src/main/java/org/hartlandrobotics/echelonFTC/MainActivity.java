package org.hartlandrobotics.echelonFTC;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelonFTC.charts.ChartsActivity;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.database.entities.Season;
import org.hartlandrobotics.echelonFTC.matchScouting.MatchSelectionActivity;
import org.hartlandrobotics.echelonFTC.models.SeasonViewModel;
//import org.hartlandrobotics.echelonFTC.pitScouting.PitScoutActivity;
import org.hartlandrobotics.echelonFTC.status.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainActivity extends EchelonActivity {

    private ApiStatus status;
    String deviceName;

    private MaterialButton startScouting;
    private MaterialButton pitScouting;
    private MaterialButton matchSchedule;
    private MaterialButton chartsButton;

    private AutoCompleteTextView seasonsAutoComplete;

    public static void launch(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar("Home");

        status= new ApiStatus(getApplicationContext());
        deviceName = Settings.Secure.getString(getContentResolver(), "bluetooth_name");

        setupStartScoutingButton();
        setupPitScoutingButton();
        setupMatchScheduleButton();
        setupChartsButton();

        setupSeasonSelection();

        handlePermissions();
    }

    private void handlePermissions() {
        int REQUEST_REQUIRED_PERMISSIONS = 1;
        String[] REQUIRED_PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        boolean requestNeeded = false;
        for( String permission : REQUIRED_PERMISSIONS ){
            int permissionId = ActivityCompat.checkSelfPermission(this, permission);
            requestNeeded |=  (permissionId != PackageManager.PERMISSION_GRANTED);
        }
        if( requestNeeded ) {
            ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS, REQUEST_REQUIRED_PERMISSIONS );
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        setupStatus();
    }

    private void setupStartScoutingButton(){
        startScouting = this.findViewById(R.id.main_admin_start_scouting);
        startScouting.setOnClickListener(view -> MatchSelectionActivity.launch(MainActivity.this, null));
    }

    private void setupPitScoutingButton(){
        pitScouting = findViewById(R.id.pit_scout_button);
        //pitScouting.setOnClickListener( v -> PitScoutActivity.launch(MainActivity.this ));
    }

    private void setupMatchScheduleButton(){
        matchSchedule = findViewById(R.id.match_schedule_button);
       matchSchedule.setOnClickListener( v -> MatchScheduleActivity.launch(MainActivity.this));
    }

    private void setupChartsButton(){
        chartsButton = findViewById(R.id.charts_button);
        chartsButton.setOnClickListener( v -> ChartsActivity.launch(MainActivity.this));

        if( deviceName.contains("aptain" ) || deviceName.contains("oach")){
            chartsButton.setVisibility(View.VISIBLE);
        }
    }


    private void setupStatus(){
       status.loadSettingsFromPrefs();
        TextInputLayout seasonLayout = findViewById(R.id.season_status_layout);
        seasonLayout.getEditText().setText(status.getSeason());

        TextInputLayout districtLayout = findViewById(R.id.district_status_layout);
        districtLayout.getEditText().setText(status.getRegionCode());

        TextInputLayout eventLayout = findViewById(R.id.event_status_layout);
        eventLayout.getEditText().setText(status.getEventKey());

        AdminSettings adminSettings = AdminSettingsProvider.getAdminSettings(this);
        TextInputLayout deviceLayout = findViewById(R.id.device_status_layout);
        String deviceRole = adminSettings.getDeviceRole();
        deviceLayout.getEditText().setText(deviceRole);
    }

    List<Season> seasons = new ArrayList<>();
    List<String> displaySeasons = new ArrayList<>();
    private void setupSeasonSelection(){
        seasonsAutoComplete = findViewById(R.id.seasonSelectionAutoComplete);

        SeasonViewModel seasonViewModel = new ViewModelProvider(this).get(SeasonViewModel.class);
        seasonViewModel.getSeasons().observe( this, seasonEntities -> {
            seasons.addAll(seasonEntities);
            displaySeasons = seasons.stream()
                    .map(s -> s.getName() + " - " + s.getYear() )
                    .collect(Collectors.toList());

            ArrayAdapter seasonsAdapter = new ArrayAdapter(this, R.layout.dropdown_item, displaySeasons);
            seasonsAutoComplete.setAdapter(seasonsAdapter);
            // get the current season from status
            String currentSeason = status.getSeason();
            String currentYear = status.getYear();
            String currentDisplay = currentSeason + " - " + currentYear;

            Optional<Integer> foundIndex = Optional.empty();
            for(int displayIndex=0; displayIndex< displaySeasons.size(); displayIndex++ ){
                if( displaySeasons.get(displayIndex).equals(currentDisplay)){
                    foundIndex = Optional.of(Integer.valueOf(displayIndex));
                    break;
                }
            }
            if( foundIndex.isPresent() ){
                String selectedText = seasonsAdapter.getItem(foundIndex.get()).toString();

                seasonsAutoComplete.setText(selectedText, false);
            }
            setEnabled(foundIndex.isPresent());

            seasonsAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
                Season selectedSeason = seasons.get(position);
                status.setYear( String.valueOf(selectedSeason.getYear()) );
                status.setSeason( selectedSeason.getName() );
                String selectedText = status.getSeason() + " - " + status.getYear();
                seasonsAutoComplete.setText(selectedText, false);
                setupStatus();
                setEnabled(true);
            });



        });
    }


    private void setEnabled(boolean seasonSelected){
           startScouting.setEnabled(seasonSelected);
           pitScouting.setEnabled(seasonSelected);
    }
}
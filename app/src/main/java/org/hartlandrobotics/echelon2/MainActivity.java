package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelon2.TBA.TBAActivity;
import org.hartlandrobotics.echelon2.database.entities.Season;
import org.hartlandrobotics.echelon2.models.SeasonViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private BlueAllianceStatus status;

    private MaterialButton startScouting;
    private MaterialButton pitScouting;
    private MaterialButton adminSettings;
    private MaterialButton tbaStatus;
    private MaterialButton tabTest;

    private AutoCompleteTextView seasonsAutoComplete;

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

        setupSeasonSelection();
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
                seasonsAutoComplete.setListSelection(foundIndex.get());
            }
            setEnabled(foundIndex.isPresent());

            seasonsAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
                Season selectedSeason = seasons.get(position);
                status.setYear( String.valueOf(selectedSeason.getYear()) );
                status.setSeason( selectedSeason.getName() );
                setupStatus();
                setEnabled(true);
            });

        });
    }

    private void setEnabled(boolean seasonSelected){
           startScouting.setEnabled(seasonSelected);
           tbaStatus.setEnabled(seasonSelected);
           pitScouting.setEnabled(seasonSelected);
    }
}
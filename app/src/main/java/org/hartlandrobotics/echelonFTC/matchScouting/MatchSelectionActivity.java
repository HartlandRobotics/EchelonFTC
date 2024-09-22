package org.hartlandrobotics.echelonFTC.matchScouting;

import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.EchelonActivity;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.database.entities.Match;
import org.hartlandrobotics.echelonFTC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MatchSelectionActivity extends EchelonActivity {
    public static final String TAG = "MatchDropdownActivity";
    List<Match> matches;
    List<String> matchNumbers;

    TextInputLayout selectTextPrompt;
    AutoCompleteTextView matchNumberAutoComplete;
    MaterialButton scoutMatchButton;

    ViewPager2 robotImagePager;

    Integer requestedMatchNumber;
    Match currentMatch;
    String currentTeamKey;

    private static final String MATCH_NUMBER_KEY = "MatchNumber";

    public static void launch(Context context, Integer matchNumber){
        Intent intent = new Intent(context, MatchSelectionActivity.class);
        if( matchNumber != null ){
            Bundle bundle = new Bundle();
            bundle.putString(MATCH_NUMBER_KEY, matchNumber.toString() );
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_selection);

        Bundle bundle = getIntent().getExtras();
        if( bundle != null ) {
            String requestedMatchNumberString = bundle.getString(MATCH_NUMBER_KEY);
            if (!StringUtils.isBlank(requestedMatchNumberString)) {
                requestedMatchNumber = Integer.valueOf(requestedMatchNumberString);
            }
        }

        setupToolbar("Match Selection");

        setupMatchDropdown();

        selectTextPrompt = findViewById(R.id.matchSelection);
        matchNumberAutoComplete = findViewById(R.id.matchSelectionAutoComplete);
        scoutMatchButton = findViewById(R.id.scoutMatch);
        scoutMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matchKey = currentMatch.getMatchKey();
                String teamKey = currentTeamKey;

                MatchScoutingAutoActivity.launch(MatchSelectionActivity.this, matchKey, teamKey);
            }
        });

        robotImagePager = findViewById(R.id.robotImagePager);
    }

    private String getTeamKey( Match match, String deviceRole ){
        switch( deviceRole ){
            case "red1":
                return match.getRed1TeamKey();
            case "red2":
                return match.getRed2TeamKey();
            case "blue1":
                return match.getBlue1TeamKey();
            case "blue2":
                return match.getBlue2TeamKey();
            default:
                throw new IllegalArgumentException("Invalid device role for match drop down");
        }
    }
    private String getTeamNumber( Match match, String deviceRole){
        String teamKey = getTeamKey( match, deviceRole );
        return trimTeamNumber( teamKey );
    }

    private String trimTeamNumber(String teamKey){
        String safeTeamKey = StringUtils.defaultIfBlank(teamKey,StringUtils.EMPTY);

        String teamNumber = safeTeamKey.startsWith("frc") ? safeTeamKey.substring(3) : safeTeamKey;

        return teamNumber;
    }

    public void setupMatchDropdown(){
        Context appContext = this.getApplicationContext();
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(appContext);
        OrangeAllianceStatus status = new OrangeAllianceStatus(appContext);
        String eventKey = status.getEventKey();
        EventRepo eventRepo = new EventRepo(this.getApplication());
        eventRepo.getEventWithMatchs(eventKey).observe(this, event ->{
            if( event == null ){
                matchNumbers = new ArrayList<>();
            }else {
                matches = event.matches.stream()
                        .sorted(Comparator.comparingInt(m -> Integer.parseInt(m.getMatchName().substring(6))))
                        .collect(Collectors.toList());

                matchNumbers = matches.stream()
                        .map(m -> String.valueOf(m.getMatchName()) + " - " + getTeamNumber(m, settings.getDeviceRole()))
                        .collect(Collectors.toList());
            }

            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_item, matchNumbers);
            matchNumberAutoComplete.setAdapter(adapter);
            matchNumberAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Log.i(TAG, "match selection on Item click called with pos " + position);
                    setMatchSelectionPosition( position );

                }
            });
            matchNumberAutoComplete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(TAG, "match selection on Item selected listener called with pos " + position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if( requestedMatchNumber != null ){
                // find that selection in the drop down and selected it
                Log.i(TAG, "match " + requestedMatchNumber + " was requested");

                int nextMatchPosition = requestedMatchNumber - 1;
                if( nextMatchPosition < matches.size() ){
                    String text = matchNumberAutoComplete.getAdapter().getItem(nextMatchPosition).toString();
                    matchNumberAutoComplete.setText(text, false);
                    setMatchSelectionPosition( nextMatchPosition );
                }


            }
        });




    }
    private void setMatchSelectionPosition(int position){
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());
        currentMatch = matches.get(position);

        scoutMatchButton.setEnabled(true);

        currentTeamKey = getTeamKey(currentMatch, settings.getDeviceRole());
        String teamNumber = getTeamNumber( currentMatch, settings.getDeviceRole());
        List<String> imageFileNames = getImageFiles(teamNumber);

        robotImagePager.setAdapter(new RobotImageAdapter(imageFileNames));
    }

    private List<String> getImageFiles(String teamNumber) {
        List<String> fileNames = new ArrayList<>();
        File[] imageFiles = getImageFilePath( teamNumber ).listFiles();
        for( File file : imageFiles ){
            fileNames.add(file.getAbsolutePath());
        }

        return fileNames;
    }

    private File getImageFilePath(String teamNumber) {
        ContextWrapper cw = new ContextWrapper( getApplicationContext() );
        return cw.getExternalFilesDir( "scouting_images/team_" + teamNumber );
    }

}
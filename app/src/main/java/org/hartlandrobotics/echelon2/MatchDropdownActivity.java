package org.hartlandrobotics.echelon2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.configuration.AdminSettings;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelon2.database.entities.EvtWithMatches;
import org.hartlandrobotics.echelon2.database.entities.Match;
import org.hartlandrobotics.echelon2.database.repositories.MatchRepo;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MatchDropdownActivity extends AppCompatActivity {
    public static final String TAG = "MatchDropdownActivity";
    List<Match> matches;
    List<String> matchNumbers;
    TextInputLayout selectTextPrompt;
    AutoCompleteTextView matchNumberAutoComplete;
    Match currentMatch;


    public static void launch(Context context){
        Intent intent = new Intent(context, MatchDropdownActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_dropdown);
        setupMatchDropdown();
        selectTextPrompt = findViewById(R.id.matchSelection);
        matchNumberAutoComplete = findViewById(R.id.matchSelectionAutoComplete);
    }

    private String getTeamNumber( Match match, String deviceRole){
        switch( deviceRole ){
            case "red1":
                return match.getRed1TeamKey();
            case "red2":
                return match.getRed2TeamKey();
            case "red3":
                return match.getRed3TeamKey();
            case "blue1":
                return match.getBlue1TeamKey();
            case "blue2":
                return match.getBlue2TeamKey();
            case "blue3":
                return match.getBlue3TeamKey();
            default:
                throw new IllegalArgumentException("Invalid device role for match drop down");
        }
    }
    public void setupMatchDropdown(){
        Context appContext = this.getApplicationContext();
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(appContext);
        BlueAllianceStatus status = new BlueAllianceStatus(appContext);
        String eventKey = status.getEventKey();
        MatchRepo matchRepo = new MatchRepo(this.getApplication());
        matchRepo.getMatchesByEvent(eventKey).observe(this, ms ->{
            matches = ms.matches;

            matchNumbers = matches.stream()
                    .sorted( Comparator.comparingInt(m->m.getMatchNumber()))
                    .map(m -> String.valueOf(m.getMatchNumber()) + " - " + getTeamNumber(m, settings.getDeviceRole()))
                    .collect(Collectors.toList());

            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_item, matchNumbers);
            matchNumberAutoComplete.setAdapter(adapter);
            matchNumberAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    currentMatch = matches.get(position);
//                    if(hasSelectedMatch()){
//
//                    }

                }
            });
        });




    }
    public boolean hasSelectedMatch(){return currentMatch != null;}
}
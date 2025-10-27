package org.hartlandrobotics.echelonFTC.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.models.MatchResultViewModel;
import org.hartlandrobotics.echelonFTC.status.ApiStatus;

public class MatchScoutingTeleopActivity extends AppCompatActivity {
    private static final String TAG = "MatchScoutingTeleopActivity";
    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    MaterialButton scoutingDoneButton;
    private ImageButton classifiedButton;
    private ImageButton overflowButton;
    private ImageButton motifButton;
    private ImageButton depotButton;


    private ImageButton baseButton;
    private ImageButton twoBotsButton;


    private MaterialTextView classifiedText;
    private MaterialTextView overflowText;
    private MaterialTextView motifText;
    private MaterialTextView depotText;


    private MaterialTextView teamKeyText;

    private ImageButton defensesButton;
    private MaterialTextView defensesText;

    int baseDrawable;
    int twobotsDrawable;
    private int defenseDrawable;

    MatchResultViewModel matchResultViewModel;
    MatchResult matchResult;

    String matchKey;
    String teamKey;

    public static void launch(Context context, String matchKey, String teamKey){
        Intent intent = new Intent(context, MatchScoutingTeleopActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MATCH_KEY, matchKey);
        bundle.putString(TEAM_KEY, teamKey);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_scouting);

        setupColor();
        setupControls();

        Bundle bundle = getIntent().getExtras();
        matchKey = bundle.getString(MATCH_KEY);
        teamKey = bundle.getString(TEAM_KEY);

        ApiStatus orangeAllianceStatus = new ApiStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingTeleopActivity.this, mr->{
                    if( mr == null ){
                        matchResult = matchResultViewModel.getDefault(orangeAllianceStatus.getEventKey(), matchKey, teamKey);
                    } else {
                        matchResult = mr;
                    }

                    populateControlsFromData();
                });
    }


    public void populateControlsFromData(){
        classifiedText.setText(String.valueOf(matchResult.getTeleOpInt1()));
        overflowText.setText(String.valueOf(matchResult.getTeleOpInt2()));
        motifText.setText(String.valueOf(matchResult.getTeleOpInt3()));
        depotText.setText(String.valueOf(matchResult.getTeleOpInt4()));


        if( matchResult.getEndInt6() == 2){
            baseButton.setImageResource(R.drawable.base_green);
        } else if( matchResult.getEndInt6() == 1){
            baseButton.setImageResource(R.drawable.base_green_partial);
        } else {
            baseButton.setImageResource(baseDrawable);
        }

        if( matchResult.getEndFlag1() ){
            twoBotsButton.setImageResource(R.drawable.two_bots_green);
        } else {
            twoBotsButton.setImageResource(twobotsDrawable);
        }
        //defensesText.setText(String.valueOf(matchResult.getDefenseCount()));
    }

    private void setupControls(){
        scoutingDoneButton = findViewById(R.id.summary);
        scoutingDoneButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            MatchScoutingSummaryActivity.launch(MatchScoutingTeleopActivity.this, matchKey, teamKey);
        });

        classifiedText = findViewById(R.id.classified_ball_text);
        classifiedButton = findViewById(R.id.classified_ball);
        classifiedButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt1( matchResult.getTeleOpInt1() + 1 );
            populateControlsFromData();
        });

        overflowText = findViewById(R.id.overflow_ball_text);
        overflowButton = findViewById(R.id.overflow_ball);
        overflowButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt2( matchResult.getTeleOpInt2() + 1 );
            populateControlsFromData();
        });

        motifText = findViewById(R.id.motif_ball_text);
        motifButton = findViewById(R.id.motif_ball);
        motifButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt3( matchResult.getTeleOpInt3() + 1);
            populateControlsFromData();
        });

        baseButton = findViewById(R.id.base_image);
        baseButton.setImageResource(baseDrawable);
        baseButton.setOnClickListener(v -> {
            matchResult.setEndInt6( (matchResult.getEndInt6() + 1)%3 );
            populateControlsFromData();
        });

        twoBotsButton = findViewById(R.id.twobots_image);
        twoBotsButton.setOnClickListener(v -> {
            matchResult.setEndFlag1(!matchResult.getEndFlag1());
            boolean isSelected = matchResult.getEndFlag1();
            populateControlsFromData();
        });

        depotText = findViewById(R.id.depot_ball_text);
        depotButton = findViewById(R.id.depot_ball);
        depotButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt4( matchResult.getTeleOpInt4() + 1);
            populateControlsFromData();
        });

        //defensesButton = findViewById(R.id.teleOpDefenses);
        //defensesButton.setImageResource(defenseDrawable);
        //defensesButton.setOnClickListener( v -> {
            //matchResult.setDefenseCount( matchResult.getDefenseCount() + 1);
            //populateControlsFromData();
        //});

        //defensesText = findViewById(R.id.teleOpDefensesValue);
    }

    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        if (settings.getDeviceRole().startsWith("red")){
            baseDrawable = R.drawable.base_red;
            twobotsDrawable = R.drawable.two_bots_red;
        } else {
            baseDrawable = R.drawable.base_blue;
            twobotsDrawable = R.drawable.two_bots_red;
        }
    }
}
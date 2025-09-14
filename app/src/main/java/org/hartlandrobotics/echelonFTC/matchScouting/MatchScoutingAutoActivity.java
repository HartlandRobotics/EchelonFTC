package org.hartlandrobotics.echelonFTC.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.models.MatchResultViewModel;
import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;

public class MatchScoutingAutoActivity extends AppCompatActivity {
    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    private ImageButton classifiedButton;
    private ImageButton overflowButton;
    private ImageButton motifButton;

    private ImageButton leaveButton;

    private MaterialTextView classifiedText;
    private MaterialTextView overflowText;
    private MaterialTextView motifText;
    private MaterialTextView teamKeyText;

    int leaveDrawable;

    MatchResultViewModel matchResultViewModel;
    MatchResult matchResult;

    String matchKey;
    String teamKey;

    public static void launch(Context context, String matchKey, String teamKey){
        Intent intent = new Intent(context, MatchScoutingAutoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MATCH_KEY, matchKey);
        bundle.putString(TEAM_KEY, teamKey);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_auto_scouting);

        setupColor();
        setupControls();

        Bundle bundle = getIntent().getExtras();
        matchKey = bundle.getString(MATCH_KEY);
        teamKey = bundle.getString(TEAM_KEY);

        teamKeyText = findViewById(R.id.teamKeyText);
        teamKeyText.setText(teamKey);

        OrangeAllianceStatus orangeAllianceStatus = new OrangeAllianceStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingAutoActivity.this, mr->{
                    if( mr == null ){
                        matchResult = matchResultViewModel.getDefault(orangeAllianceStatus.getEventKey(), matchKey, teamKey);
                    } else {
                        matchResult = mr;
                    }

                    populateControlsFromData();
                });
    }

    public void populateControlsFromData(){
        classifiedText.setText(String.valueOf(matchResult.getAutoInt6()));
        overflowText.setText(String.valueOf(matchResult.getAutoInt7()));
        motifText.setText(String.valueOf(matchResult.getAutoInt8()));

        if( matchResult.getAutoFlag1() ){
            leaveButton.setImageResource(R.drawable.leave_green);
        } else {
            leaveButton.setImageResource(leaveDrawable);
        }
    }

    public void setupControls(){
        MaterialButton teleOpButton = findViewById(R.id.teleOp);
        teleOpButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            MatchScoutingTeleopActivity.launch(MatchScoutingAutoActivity.this, matchKey, teamKey );
        });

        classifiedText = findViewById(R.id.classified_ball_text);
        classifiedButton = findViewById(R.id.classified_ball);
        classifiedButton.setOnClickListener(v -> {
            matchResult.setAutoInt6( matchResult.getAutoInt6() + 1);
            populateControlsFromData();
        });

        overflowText = findViewById(R.id.overflow_ball_text);
        overflowButton = findViewById(R.id.overflow_ball);
        overflowButton.setOnClickListener(v -> {
            matchResult.setAutoInt7( matchResult.getAutoInt7() + 1);
            populateControlsFromData();
        });

        motifText = findViewById(R.id.motif_ball_text);
        motifButton = findViewById(R.id.motif_ball);
        motifButton.setOnClickListener(v -> {
            matchResult.setAutoInt8( matchResult.getAutoInt8() + 1);
            populateControlsFromData();
        });

        leaveButton = findViewById(R.id.leave);
        leaveButton.setImageResource(leaveDrawable);
        leaveButton.setOnClickListener(v -> {
            matchResult.setAutoFlag1( !matchResult.getAutoFlag1() );
            populateControlsFromData();
        });
    }

    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        if (settings.getDeviceRole().startsWith("red")){
            leaveDrawable = R.drawable.leave_red;
        } else {
            leaveDrawable = R.drawable.leave_blue;
        }
    }
}
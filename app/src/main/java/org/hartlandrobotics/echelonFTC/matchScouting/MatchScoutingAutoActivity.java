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
//import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;
import org.hartlandrobotics.echelonFTC.ftcapi.status.*;

public class MatchScoutingAutoActivity extends AppCompatActivity {
    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";
    private ImageButton patternLeftButton;
    private ImageButton patternCenterButton;
    private ImageButton patternRightButton;

    private ImageButton missedButton;
    private ImageButton classifiedButton;
    private ImageButton overflowButton;
    private ImageButton motifButton;

    private ImageButton leaveButton;

    private MaterialTextView classifiedText;
    private MaterialTextView overflowText;
    private MaterialTextView motifText;
    private MaterialTextView teamKeyText;

    private MaterialTextView missedText;
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


        Context appContext = this.getApplicationContext();
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(appContext);
        teamKeyText = findViewById(R.id.teamKeyText);
        teamKeyText.setTextColor(settings.getDeviceRole().contains("red") ? getResources().getColor(R.color.redAlliance) : getResources().getColor(R.color.blueAlliance));
        teamKeyText.setText(teamKey);

        FtcApiStatus orangeAllianceStatus = new FtcApiStatus(getApplicationContext());

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
        int ballPattern = matchResult.getAutoInt9();
        if( ballPattern == 0 ){
            patternLeftButton.setImageResource(R.drawable.ball_green);
            patternCenterButton.setImageResource(R.drawable.ball_purple);
            patternRightButton.setImageResource(R.drawable.ball_purple);
        } else if( ballPattern == 1 ) {
            patternLeftButton.setImageResource(R.drawable.ball_purple);
            patternCenterButton.setImageResource(R.drawable.ball_green);
            patternRightButton.setImageResource(R.drawable.ball_purple);
        } else if( ballPattern == 2 ) {
            patternLeftButton.setImageResource(R.drawable.ball_purple);
            patternCenterButton.setImageResource(R.drawable.ball_purple);
            patternRightButton.setImageResource(R.drawable.ball_green);
        }

        classifiedText.setText(String.valueOf(matchResult.getAutoInt6()));
        overflowText.setText(String.valueOf(matchResult.getAutoInt7()));
        motifText.setText(String.valueOf(matchResult.getAutoInt8()));

        missedText.setText(String.valueOf(matchResult.getAutoInt10()));
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

        patternLeftButton = findViewById(R.id.pattern_left);
        patternLeftButton.setOnClickListener(v -> {
            matchResult.setAutoInt9( (matchResult.getAutoInt9() + 1) % 3);
            populateControlsFromData();
        });

        patternCenterButton = findViewById(R.id.pattern_center);
        patternCenterButton.setOnClickListener(v -> {
            matchResult.setAutoInt9( (matchResult.getAutoInt9() + 1) % 3);
            populateControlsFromData();
        });

        patternRightButton = findViewById(R.id.pattern_right);
        patternRightButton.setOnClickListener(v -> {
            matchResult.setAutoInt9( (matchResult.getAutoInt9() + 1) % 3);
            populateControlsFromData();
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

        missedText = findViewById(R.id.missed_ball_text);
        missedButton = findViewById(R.id.missed_ball);
        missedButton.setOnClickListener(view -> {
            matchResult.setAutoInt10(matchResult.getAutoInt10() + 1);
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
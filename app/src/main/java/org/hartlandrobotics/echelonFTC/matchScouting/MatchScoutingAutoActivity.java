package org.hartlandrobotics.echelonFTC.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.models.MatchResultViewModel;
//import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;
import org.hartlandrobotics.echelonFTC.ftcapi.status.*;
import org.hartlandrobotics.echelonFTC.utilities.RoleUtilities;

public class MatchScoutingAutoActivity extends AppCompatActivity {
    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";
    private ImageButton patternLeftButton;
    private ImageButton patternCenterButton;
    private ImageButton patternRightButton;

    private ImageButton missedButton;
    private ImageButton classifiedButton;
    private ImageButton overflowButton;
    private ImageButton leaveButton;

    private MaterialTextView classifiedText;
    private MaterialTextView overflowText;
    private MaterialTextView teamKeyText;

    private MaterialTextView missedText;
    int leaveDrawable;
    private ImageButton ball0;
    private ImageButton ball1;
    private ImageButton ball2;
    private ImageButton ball3;
    private ImageButton ball4;
    private ImageButton ball5;
    private ImageButton ball6;
    private ImageButton ball7;
    private ImageButton ball8;
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
        String alliance = RoleUtilities.deviceColor(settings.getDeviceRole());
        teamKeyText = findViewById(R.id.teamKeyText);
        teamKeyText.setTextColor(settings.getDeviceRole().contains("red") ? getResources().getColor(R.color.redAlliance) : getResources().getColor(R.color.blueAlliance));
        teamKeyText.setText(teamKey);

        FtcApiStatus ftcApiStatus = new FtcApiStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingAutoActivity.this, mr->{
                    if( mr == null ){
                        matchResult = matchResultViewModel.getDefault(ftcApiStatus.getEventKey(), matchKey, teamKey, alliance);
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

        missedText.setText(String.valueOf(matchResult.getAutoInt10()));
        if( matchResult.getAutoFlag1() ){
            leaveButton.setImageResource(R.drawable.leave_green);
        } else {
            leaveButton.setImageResource(leaveDrawable);
        }

        String[] strArr = matchResult.getAutoString11().split(",");
        for(int index=0; index<9; index++){
            String currentValue = strArr[index];
            int currentImage = R.drawable.ball_white;
            switch (currentValue) {
                case "1":
                    currentImage = R.drawable.ball_purple;
                    break;
                case "2":
                    currentImage = R.drawable.ball_green;
                    break;
                default:
                    break;
            }

            switch (index){
                case 0:
                    ball0.setImageResource(currentImage);
                    break;
                case 1:
                    ball1.setImageResource(currentImage);
                    break;
                case 2:
                    ball2.setImageResource(currentImage);
                    break;
                case 3:
                    ball3.setImageResource(currentImage);
                    break;
                case 4:
                    ball4.setImageResource(currentImage);
                    break;
                case 5:
                    ball5.setImageResource(currentImage);
                    break;
                case 6:
                    ball6.setImageResource(currentImage);
                    break;
                case 7:
                    ball7.setImageResource(currentImage);
                    break;
                case 8:
                    ball8.setImageResource(currentImage);
                    break;
            }
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

        ball0 = findViewById(R.id.ball0);
        ball0.setOnClickListener( v -> {
            ButtonClick(v, 0);
        });
        ball1 = findViewById(R.id.ball1);
        ball1.setOnClickListener( v -> {
            ButtonClick(v, 1);
        });
        ball2 = findViewById(R.id.ball2);
        ball2.setOnClickListener( v -> {
            ButtonClick(v, 2);
        });
        ball3 = findViewById(R.id.ball3);
        ball3.setOnClickListener( v -> {
            ButtonClick(v, 3);
        });
        ball4 = findViewById(R.id.ball4);
        ball4.setOnClickListener( v -> {
            ButtonClick(v, 4);
        });
        ball5 = findViewById(R.id.ball5);
        ball5.setOnClickListener( v -> {
            ButtonClick(v, 5);
        });
        ball6 = findViewById(R.id.ball6);
        ball6.setOnClickListener( v -> {
            ButtonClick(v, 6);
        });
        ball7 = findViewById(R.id.ball7);
        ball7.setOnClickListener( v -> {
            ButtonClick(v, 7);
        });
        ball8 = findViewById(R.id.ball8);
        ball8.setOnClickListener( v -> {
            ButtonClick(v, 8);
        });

    }

    private void ButtonClick(View v, int currentIndex ) {
        ImageButton currentButton = (ImageButton) v;
        String ballString = matchResult.getAutoString11();
        String[] ballStringArr = ballString.split(",");
        String currentBallStr = ballStringArr[currentIndex];
        if (StringUtils.isBlank(currentBallStr)) {
            return;
        }
        int newBall = (Integer.parseInt(currentBallStr) + 1) % 3;
        if (newBall == 0) {
            currentButton.setImageResource(R.drawable.ball_white);
        } else if (newBall == 1) {
            currentButton.setImageResource(R.drawable.ball_purple);
        } else if (newBall == 2) {
            currentButton.setImageResource(R.drawable.ball_green);
        }
        ballStringArr[currentIndex] = String.valueOf(newBall);
        matchResult.setAutoString11( String.join(",", ballStringArr) );

        int ballPattern = matchResult.getAutoInt9();
        String[] patternArr = new String[3];
        if( ballPattern == 0 ){
            patternArr[0] = "2";
            patternArr[1] = "1";
            patternArr[2] = "1" ;
        } else if( ballPattern == 1 ) {
            patternArr[0] = "1";
            patternArr[1] = "2";
            patternArr[2] = "1";
        } else if( ballPattern == 2 ) {
            patternArr[0] = "1";
            patternArr[1] = "1";
            patternArr[2] = "2";

        }

        int motifCount = 0;
        for( int index=0; index < 9; index++) {
            String currentBall = ballStringArr[index];
            String currentPattern = patternArr[index%3];
            if( currentBall.equals(currentPattern) ){
                motifCount++;
            }
        }
        matchResult.setAutoInt8(motifCount);

        populateControlsFromData();
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
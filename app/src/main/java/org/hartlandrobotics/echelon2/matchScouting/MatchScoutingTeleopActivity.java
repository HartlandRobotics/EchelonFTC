package org.hartlandrobotics.echelon2.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.configuration.AdminSettings;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.models.MatchResultViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

public class MatchScoutingTeleopActivity extends AppCompatActivity {
    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    MaterialButton scoutingDoneButton;
    private ImageButton topHubButton;
    private MaterialTextView topHubText;

    private ImageButton bottomHubButton;
    private MaterialTextView bottomHubText;

    private MaterialButton lowButton;
    private MaterialButton midButton;
    private MaterialButton highButton;
    private MaterialButton traversalButton;

    private ImageButton defensesButton;
    private MaterialTextView defensesText;


    private int topHubButtonDrawable;
    private int bottomHubButtonDrawable;
    private int buttonColor;
    private int buttonSelectedTextColor;
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

        BlueAllianceStatus blueAllianceStatus = new BlueAllianceStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingTeleopActivity.this, mr->{
                    if( mr == null ){
                        matchResult = matchResultViewModel.getDefault(blueAllianceStatus.getEventKey(), matchKey, teamKey);
                    } else {
                        matchResult = mr;
                    }

                    populateControlsFromData();
                });
    }


    public void populateControlsFromData(){
        topHubText.setText(String.valueOf(matchResult.getTeleOpHighBalls()));
        bottomHubText.setText(String.valueOf(matchResult.getTeleOpLowBalls()));
        defensesText.setText(String.valueOf(matchResult.getDefenseCount()));

        if (matchResult.getEndHangTraverse()){
            traversalButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
            traversalButton.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else {
            traversalButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            traversalButton.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
        }

        if (matchResult.getEndHangHigh()){
            highButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
            highButton.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else {
            highButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            highButton.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
        }
        if (matchResult.getEndHangMid()){
            midButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
            midButton.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else {
            midButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            midButton.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
        }
        if (matchResult.getEndHangLow()){
            lowButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
            lowButton.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else {
            lowButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            lowButton.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
        }
    }

    private void setupControls(){
        scoutingDoneButton = findViewById(R.id.scoutingDone);
        scoutingDoneButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            MatchScoutingSummaryActivity.launch(MatchScoutingTeleopActivity.this, matchKey, teamKey);
        });

        topHubButton = findViewById(R.id.topHub);
        topHubButton.setImageResource(topHubButtonDrawable);
        topHubButton.setOnClickListener(v -> {
            matchResult.setTeleOpHighBalls(matchResult.getTeleOpHighBalls()+1);
            populateControlsFromData();
        });

        bottomHubButton = findViewById(R.id.bottomHub);
        bottomHubButton.setImageResource(bottomHubButtonDrawable);
        bottomHubButton.setOnClickListener(v -> {
            matchResult.setTeleOpLowBalls(matchResult.getTeleOpLowBalls()+1);
            populateControlsFromData();
        });

        traversalButton = findViewById(R.id.traversal);
        traversalButton.setOnClickListener(v -> {
            matchResult.setEndHangTraverse(!matchResult.getEndHangTraverse());
            populateControlsFromData();
        });

        highButton = findViewById(R.id.high);
        highButton.setOnClickListener(v -> {
            matchResult.setEndHangHigh( !matchResult.getEndHangHigh());
            populateControlsFromData();
        });

        midButton = findViewById(R.id.mid);
        midButton.setOnClickListener(v -> {
            matchResult.setEndHangMid( !matchResult.getEndHangMid() );
            populateControlsFromData();
        });

        lowButton = findViewById(R.id.low);
        lowButton.setOnClickListener(v -> {
            matchResult.setEndHangLow( !matchResult.getEndHangLow() );
            populateControlsFromData();
        });

        defensesButton = findViewById(R.id.teleOpDefenses);
        defensesButton.setImageResource(defenseDrawable);
        defensesButton.setOnClickListener( v -> {
            matchResult.setDefenseCount( matchResult.getDefenseCount() + 1);
            populateControlsFromData();
        });

        topHubText = findViewById(R.id.topHubText);
        bottomHubText = findViewById(R.id.bottomHubText);
        defensesText = findViewById(R.id.teleOpDefensesValue);
    }

    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        buttonSelectedTextColor = R.color.primaryDarkColor;

        if (settings.getDeviceRole().startsWith("red")){
            topHubButtonDrawable = R.drawable.frc_hub_top_red;
            bottomHubButtonDrawable = R.drawable.frc_hub_bottom_red;
            defenseDrawable = R.drawable.defense_red;
            buttonColor = R.color.redAlliance;
        } else {
            topHubButtonDrawable = R.drawable.frc_hub_top_blue;
            bottomHubButtonDrawable = R.drawable.frc_hub_bottom_blue;
            defenseDrawable = R.drawable.defense_blue;
            buttonColor = R.color.blueAlliance;
        }
    }
}
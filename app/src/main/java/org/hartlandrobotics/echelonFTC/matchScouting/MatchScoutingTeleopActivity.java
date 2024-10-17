package org.hartlandrobotics.echelonFTC.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.models.MatchResultViewModel;
import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;

public class MatchScoutingTeleopActivity extends AppCompatActivity {
    private static final String TAG = "MatchScoutingTeleopActivity";

    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    MaterialButton scoutingDoneButton;
    private ImageButton topBasketButton;
    private ImageButton midBasketButton;
    private ImageButton lowBasketButton;

    private ImageButton topSpecimenButton;
    private ImageButton lowSpecimenButton;

    private ImageButton observationZoneButton;

    private MaterialButton level1AscentButton;
    private MaterialButton level2AscentButton;
    private MaterialButton level3AscentButton;


    private MaterialTextView topBasketText;
    private MaterialTextView midBasketText;
    private MaterialTextView lowBasketText;
    private MaterialTextView topSpecimenText;
    private MaterialTextView lowSpecimenText;


    private MaterialTextView teamKeyText;

    private ImageButton defensesButton;
    private MaterialTextView defensesText;

    int basketDrawable;

    int specimemDrawable;
    int ascentDrawable;
    int observationZoneDrawable;
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

        OrangeAllianceStatus orangeAllianceStatus = new OrangeAllianceStatus(getApplicationContext());

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
        Log.e(TAG, "populate test");

        topBasketText.setText(String.valueOf(matchResult.getTeleOpInt1()));
        midBasketText.setText(String.valueOf(matchResult.getTeleOpInt2()));
        lowBasketText.setText(String.valueOf(matchResult.getTeleOpInt3()));

        topSpecimenText.setText(String.valueOf(matchResult.getTeleOpInt4()));
        lowSpecimenText.setText(String.valueOf(matchResult.getTeleOpInt5()));

        if( matchResult.getEndFlag1() ){
            observationZoneButton.setImageResource(R.drawable.observation_zone_green);
        } else {
            observationZoneButton.setImageResource(observationZoneDrawable);
        }

        //defensesText.setText(String.valueOf(matchResult.getDefenseCount()));

        if (matchResult.getEndFlag2()){
            level1AscentButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
            level1AscentButton.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else {
            level1AscentButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            level1AscentButton.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
        }

        if (matchResult.getEndFlag3()){
            level2AscentButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
            level2AscentButton.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else {
            level2AscentButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            level2AscentButton.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
        }

        if (matchResult.getEndFlag4()){
            level3AscentButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
            level3AscentButton.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else {
            level3AscentButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            level3AscentButton.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
        }
    }

    private void setupControls(){
        scoutingDoneButton = findViewById(R.id.summary);
        scoutingDoneButton.setOnClickListener(v -> {
            Log.e(TAG, "set for summary");
            matchResultViewModel.upsert(matchResult);
            MatchScoutingSummaryActivity.launch(MatchScoutingTeleopActivity.this, matchKey, teamKey);
        });

        topBasketText = findViewById(R.id.topBasketText);
        topBasketButton = findViewById(R.id.topBasket);
        topBasketButton.setImageResource(basketDrawable);
        topBasketButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt1( matchResult.getTeleOpInt1() + 1 );
            populateControlsFromData();
        });

        midBasketText = findViewById(R.id.midBasketText);
        midBasketButton = findViewById(R.id.midBasket);
        midBasketButton.setImageResource(basketDrawable);
        midBasketButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt2( matchResult.getTeleOpInt2() + 1 );
            populateControlsFromData();
        });

        lowBasketText = findViewById(R.id.lowBasketText);
        lowBasketButton = findViewById(R.id.lowBasket);
        lowBasketButton.setImageResource(basketDrawable);
        lowBasketButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt3( matchResult.getTeleOpInt3() + 1);
            populateControlsFromData();
        });

        topSpecimenText = findViewById(R.id.topSpecimenText);
        topSpecimenButton = findViewById(R.id.topSpecimen);
        topSpecimenButton.setImageResource(specimemDrawable);
        topSpecimenButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt4( matchResult.getTeleOpInt4() + 1);
            populateControlsFromData();
        });

        lowSpecimenText = findViewById(R.id.lowSpecimenText);
        lowSpecimenButton = findViewById(R.id.lowSpecimen);
        lowSpecimenButton.setImageResource(specimemDrawable);
        lowSpecimenButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt5( matchResult.getTeleOpInt5() + 1);
            populateControlsFromData();
        });

        observationZoneButton = findViewById(R.id.observationZone);
        observationZoneButton.setImageResource(observationZoneDrawable);
        observationZoneButton.setOnClickListener(v -> {
            Log.e(TAG, "obs zone check");

            matchResult.setEndFlag1( !matchResult.getEndFlag1() );
            populateControlsFromData();
        });

        level1AscentButton = findViewById(R.id.level1Ascent);
        level1AscentButton.setOnClickListener(v -> {
            matchResult.setEndFlag2(!matchResult.getEndFlag2());
            boolean isSelected = matchResult.getEndFlag2();
            if ( isSelected ){
                matchResult.setEndFlag3(false);
                matchResult.setEndFlag4(false);
            }

            populateControlsFromData();
        });

        level2AscentButton = findViewById(R.id.level2Ascent);
        level2AscentButton.setOnClickListener(v -> {
            matchResult.setEndFlag3(!matchResult.getEndFlag3());
            boolean isSelected = matchResult.getEndFlag3();
            if ( isSelected ){
                matchResult.setEndFlag2(false);
                matchResult.setEndFlag4(false);
            }

            populateControlsFromData();
        });

        level3AscentButton = findViewById(R.id.level3Ascent);
        level3AscentButton.setOnClickListener(v -> {
            matchResult.setEndFlag4(!matchResult.getEndFlag4());
            boolean isSelected = matchResult.getEndFlag3();
            if ( isSelected ){
                matchResult.setEndFlag2(false);
                matchResult.setEndFlag3(false);
            }

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

        buttonSelectedTextColor = R.color.primaryDarkColor;

        if (settings.getDeviceRole().startsWith("red")){
            basketDrawable = R.drawable.sample_red;
            specimemDrawable = R.drawable.specimen_red;
            ascentDrawable = R.drawable.bar_red;
            observationZoneDrawable = R.drawable.observation_zone_red;
            buttonColor = R.color.redAlliance;
        } else {
            basketDrawable = R.drawable.sample_blue;
            specimemDrawable = R.drawable.specimen_blue;
            ascentDrawable = R.drawable.bar_blue;
            observationZoneDrawable = R.drawable.observation_zone_blue;
            buttonColor = R.color.blueAlliance;
        }



    }
}
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
import org.hartlandrobotics.echelon2.status.OrangeAllianceStatus;

public class MatchScoutingAutoActivity extends AppCompatActivity {
    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    private ImageButton topBasketButton;
    private ImageButton midBasketButton;
    private ImageButton lowBasketButton;

    private ImageButton topSpecimenButton;
    private ImageButton lowSpecimenButton;

    private ImageButton observationZoneButton;
    private MaterialButton levelAscentButton;


    private MaterialTextView topBasketText;
    private MaterialTextView midBasketText;
    private MaterialTextView lowBasketText;
    private MaterialTextView topSpecimenText;
    private MaterialTextView lowSpecimenText;
    private MaterialTextView teamKeyText;


    int basketDrawable;
    int specimemDrawable;
    int ascentDrawable;
    int observationZoneDrawable;
    private int buttonColor;

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
        topBasketText.setText(String.valueOf(matchResult.getAutoInt6()));
        midBasketText.setText(String.valueOf(matchResult.getAutoInt7()));
        lowBasketText.setText(String.valueOf(matchResult.getAutoInt8()));

        topSpecimenText.setText(String.valueOf(matchResult.getAutoInt9()));
        topSpecimenText.setText(String.valueOf(matchResult.getAutoInt10()));

        if( matchResult.getAutoFlag1() ){
            observationZoneButton.setImageResource(R.drawable.observation_zone_green);
        } else {
            observationZoneButton.setImageResource(observationZoneDrawable);
        }

        if (matchResult.getAutoFlag2()){
            levelAscentButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
        }
        else {
            levelAscentButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
        }


    }

    public void setupControls(){

        MaterialButton teleOpButton = findViewById(R.id.teleOp);
        teleOpButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            MatchScoutingTeleopActivity.launch(MatchScoutingAutoActivity.this, matchKey, teamKey );
        });

        topBasketText = findViewById(R.id.topBasketText);
        topBasketButton = findViewById(R.id.topBasket);
        topBasketButton.setImageResource(basketDrawable);
        topBasketButton.setOnClickListener(v -> {
            matchResult.setAutoInt6( matchResult.getAutoInt6() + 1);
            populateControlsFromData();
        });

        midBasketText = findViewById(R.id.midBasketText);
        midBasketButton = findViewById(R.id.midBasket);
        midBasketButton.setImageResource(basketDrawable);
        midBasketButton.setOnClickListener(v -> {
            matchResult.setAutoInt7( matchResult.getAutoInt7());
            populateControlsFromData();
        });

        lowBasketText = findViewById(R.id.lowBasketText);
        lowBasketButton = findViewById(R.id.lowBasket);
        lowBasketButton.setImageResource(basketDrawable);
        lowBasketButton.setOnClickListener(v -> {
            matchResult.setAutoInt8( matchResult.getAutoInt8());
            populateControlsFromData();
        });

        topSpecimenText = findViewById(R.id.topSpecimenText);
        topSpecimenButton = findViewById(R.id.topSpecimen);
        topSpecimenButton.setImageResource(basketDrawable);
        topSpecimenButton.setOnClickListener(v -> {
            matchResult.setAutoInt9( matchResult.getAutoInt9());
            populateControlsFromData();
        });

        lowSpecimenText = findViewById(R.id.lowSpecimenText);
        lowSpecimenButton = findViewById(R.id.lowSpecimen);
        lowSpecimenButton.setImageResource(basketDrawable);
        lowSpecimenButton.setOnClickListener(v -> {
            matchResult.setAutoInt9( matchResult.getAutoInt9());
            populateControlsFromData();
        });

        observationZoneButton = findViewById(R.id.observationZone);
        observationZoneButton.setImageResource(observationZoneDrawable);
        observationZoneButton.setOnClickListener(v -> {
            matchResult.setAutoFlag1( !matchResult.getAutoFlag1() );
            populateControlsFromData();
        });

        levelAscentButton = findViewById(R.id.level1Ascent);
        levelAscentButton.setOnClickListener(v -> {
            matchResult.setAutoFlag2(!matchResult.getAutoFlag2());
            populateControlsFromData();
        });


    }

    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        if (settings.getDeviceRole().startsWith("red")){
            basketDrawable = R.drawable.sample_red;
            specimemDrawable = R.drawable.red_specimen;
            ascentDrawable = R.drawable.bar_red;
            observationZoneDrawable = R.drawable.observation_zone_red;
            buttonColor = R.color.redAlliance;
        } else {
            basketDrawable = R.drawable.sample_blue;
            specimemDrawable = R.drawable.blue_specimen;
            ascentDrawable = R.drawable.bar_blue;
            observationZoneDrawable = R.drawable.observation_zone_blue;
            buttonColor = R.color.blueAlliance;
        }
    }

}
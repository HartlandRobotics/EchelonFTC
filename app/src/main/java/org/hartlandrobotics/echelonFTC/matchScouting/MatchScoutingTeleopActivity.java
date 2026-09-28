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

public class MatchScoutingTeleopActivity extends AppCompatActivity {
    private static final String TAG = "MatchScoutingTeleopActivity";
    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    private int nectarDrawable = 0;
    private ImageButton nectarButton;
    private MaterialTextView nectarText;

    private int polledDrawable = 0;
    private ImageButton pollenButton;
    private MaterialTextView pollenText;

    private int missedDrawable = 0;
    private ImageButton missedButton;
    private MaterialTextView missedText;


    private int ownedDrawable = 0;
    private ImageButton ownedButton;
    private MaterialTextView ownedText;

    private int bottomDrawable = 0;
    private ImageButton bottomButton;
    private MaterialTextView bottomText;


    private int hiveDrawable = 0;
    private ImageButton hiveButton;
    private MaterialTextView hiveText;

    private int gardenDrawable = 0;
    private ImageButton gardenButton;
    private MaterialTextView gardenText;


    private int hiveRemainingDrawable = 0;
    private ImageButton hiveRemainingButton;
    private MaterialTextView hiveRemainingText;

    private int parkDrawable = 0;
    private ImageButton parkButton;


    MaterialButton summaryButton;


    private MaterialTextView teamKeyText;

    private ImageButton defensesButton;
    private MaterialTextView defensesText;


    MatchResultViewModel matchResultViewModel;
    MatchResult matchResult;

    String matchKey;
    String teamKey;


    public static void launch(Context context, String matchKey, String teamKey) {
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

        Context appContext = this.getApplicationContext();
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(appContext);
        teamKeyText = findViewById(R.id.teamKeyText);
        teamKeyText.setTextColor(settings.getDeviceRole().contains("red") ? getResources().getColor(R.color.redAlliance) : getResources().getColor(R.color.blueAlliance));
        teamKeyText.setText(teamKey);

        FtcApiStatus ftcApiStatus = new FtcApiStatus(getApplicationContext());
        AdminSettings adminSettings = AdminSettingsProvider.getAdminSettings(getApplicationContext());


        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingTeleopActivity.this, mr -> {
                    if (mr == null) {
                        matchResult = matchResultViewModel.getDefault(
                                ftcApiStatus.getEventKey(),
                                matchKey,
                                teamKey,
                                RoleUtilities.deviceColor(adminSettings.getDeviceRole())
                        );
                    } else {
                        matchResult = mr;
                    }

                    populateControlsFromData();
                });
    }


    public void populateControlsFromData() {
        nectarText.setText(String.valueOf(matchResult.getTeleOpInt6()));
        pollenText.setText(String.valueOf(matchResult.getTeleOpInt7()));
        missedText.setText(String.valueOf(matchResult.getTeleOpInt8()));

        ownedText.setText(String.valueOf(matchResult.getTeleOpInt9()));

        hiveText.setText(String.valueOf(matchResult.getTeleOpInt10()));
        gardenText.setText(String.valueOf(matchResult.getTeleOpInt11()));
        ;

        hiveRemainingText.setText(String.valueOf(matchResult.getTeleOpInt12()));

        if (matchResult.getEndFlag1()) {
            parkButton.setImageResource(R.drawable.park_green);
        } else {
            parkButton.setImageResource(parkDrawable);
        }


        //defensesText.setText(String.valueOf(matchResult.getDefenseCount()));

    }

    private void setupControls() {
        teamKeyText = findViewById(R.id.teamKeyText);
        teamKeyText.setText(teamKey);

        summaryButton = findViewById(R.id.summary);
        summaryButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            MatchScoutingSummaryActivity.launch(MatchScoutingTeleopActivity.this, matchKey, teamKey);
        });

        nectarText = findViewById(R.id.nectar_text);
        nectarButton = findViewById(R.id.nectar_image);
        nectarButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt6(matchResult.getTeleOpInt6() + 1);
            populateControlsFromData();
        });

        pollenText = findViewById(R.id.pollen_text);
        pollenButton = findViewById(R.id.pollen_image);
        pollenButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt7(matchResult.getTeleOpInt7() + 1);
            populateControlsFromData();

        });
        missedText = findViewById(R.id.missed_ball_text);
        missedButton = findViewById(R.id.missed_ball);
        missedButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt8(matchResult.getTeleOpInt8() + 1);
            populateControlsFromData();
        });

        hiveText = findViewById(R.id.hive_text);
        hiveButton = findViewById(R.id.hive_image);
        hiveButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt9(matchResult.getTeleOpInt9() + 1);
            populateControlsFromData();
        });

        gardenText = findViewById(R.id.garden_text);
        gardenButton = findViewById(R.id.garden_image);
        gardenButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt10(matchResult.getTeleOpInt10() + 1);
            populateControlsFromData();
        });

        hiveRemainingText = findViewById(R.id.hive_remaining_text);
        hiveRemainingButton = findViewById(R.id.hive_remaining_image);
        hiveRemainingButton.setOnClickListener(v -> {
            matchResult.setTeleOpInt11(matchResult.getTeleOpInt11() + 1);
            populateControlsFromData();
        });

        parkButton = findViewById(R.id.park_image);
        parkButton.setOnClickListener(v -> {
            matchResult.setEndFlag1(!matchResult.getEndFlag1());
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

        if (settings.getDeviceRole().startsWith("red")) {
            nectarDrawable = R.drawable.nectar_red;
            missedDrawable = R.drawable.missing_scoring_elements_red;

            ownedDrawable = R.drawable.owned_red_flower;
            bottomDrawable = R.drawable.flower_bottom_red;

            hiveDrawable = R.drawable.hive_red;
            gardenDrawable = R.drawable.garden_red;

            hiveRemainingDrawable = R.drawable.hive_red_score;
            parkDrawable = R.drawable.park_red;
        } else if (settings.getDeviceRole().startsWith("blue")) {
            nectarDrawable = R.drawable.nectar_blue;
            missedDrawable = R.drawable.missing_scoring_elements_blue;

            ownedDrawable = R.drawable.owned_blue_flower;
            bottomDrawable = R.drawable.flower_bottom_blue;

            hiveDrawable = R.drawable.hive_blue;
            gardenDrawable = R.drawable.garden_blue;

            hiveRemainingDrawable = R.drawable.hive_blue_score;
            parkDrawable = R.drawable.park_blue;
        }
    }
}
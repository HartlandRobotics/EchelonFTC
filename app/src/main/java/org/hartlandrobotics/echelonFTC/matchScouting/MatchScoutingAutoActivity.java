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


    private ImageButton nectarButton;
    private MaterialTextView nectarText;

    private ImageButton pollenButton;
    private MaterialTextView pollenText;

    private ImageButton missedButton;
    private MaterialTextView missedText;


    private ImageButton hiveButton;
    private MaterialTextView hiveText;



    private int leaveDrawable;
    private ImageButton leaveButton;

    private int parkDrawable;
    private ImageButton parkButton;


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
        MaterialTextView teamKeyText = findViewById(R.id.teamKeyText);
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

        nectarText.setText(String.valueOf(matchResult.getAutoInt6()));
        pollenText.setText(String.valueOf(matchResult.getAutoInt7()));
        missedText.setText(String.valueOf(matchResult.getAutoInt8()));

        hiveText.setText(String.valueOf(matchResult.getAutoInt9()));

        if( matchResult.getAutoFlag1() ){
            leaveButton.setImageResource(R.drawable.leave_green);
        } else {
            leaveButton.setImageResource(leaveDrawable);
        }

        if( matchResult.getAutoFlag2() ){
            parkButton.setImageResource(R.drawable.park_green);
        } else {
            parkButton.setImageResource(parkDrawable);
        }

    }

    public void setupControls(){
        MaterialButton teleOpButton = findViewById(R.id.teleOp);
        teleOpButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            MatchScoutingTeleopActivity.launch(MatchScoutingAutoActivity.this, matchKey, teamKey );
        });

        nectarText = findViewById(R.id.nectar_text);
        nectarButton = findViewById(R.id.nectar_image);
        nectarButton.setOnClickListener(v -> {
            matchResult.setAutoInt7( matchResult.getAutoInt7() + 1);
            populateControlsFromData();
        });

        pollenText = findViewById(R.id.pollen_text);
        pollenButton = findViewById(R.id.pollen_image);
        pollenButton.setOnClickListener(v -> {
            matchResult.setAutoInt8( matchResult.getAutoInt8() + 1);
            populateControlsFromData();
        });

        missedText = findViewById(R.id.missed_ball_text);
        missedButton = findViewById(R.id.missed_ball);
        missedButton.setOnClickListener(v -> {
            matchResult.setAutoInt9( matchResult.getAutoInt9() + 1);
            populateControlsFromData();
        });

        hiveText = findViewById(R.id.hive_text);
        hiveButton = findViewById(R.id.hive_image);
        hiveButton.setOnClickListener(v -> {
            matchResult.setAutoInt10( matchResult.getAutoInt10() + 1 );
            populateControlsFromData();
        });



        leaveButton = findViewById(R.id.leave);
        leaveButton.setImageResource(leaveDrawable);
        leaveButton.setOnClickListener(v -> {
            matchResult.setAutoFlag1( !matchResult.getAutoFlag1() );
            populateControlsFromData();
        });


        parkButton = findViewById(R.id.park_image);
        parkButton.setImageResource(parkDrawable);
        parkButton.setOnClickListener(v -> {
            matchResult.setAutoFlag2( !matchResult.getAutoFlag2() );
            populateControlsFromData();
        });
    }

    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        int nectarDrawable = 0;
        int missedDrawable = 0;
        int hiveDrawable = 0;

        if (settings.getDeviceRole().startsWith("red")){
            nectarDrawable = R.drawable.nectar_red;
            missedDrawable = R.drawable.missing_scoring_elements_red;
            hiveDrawable = R.drawable.hive_red;
            leaveDrawable = R.drawable.leave_red;
            parkDrawable = R.drawable.leave_red;
        } else {
            nectarDrawable = R.drawable.nectar_blue;
            missedDrawable = R.drawable.missing_scoring_elements_blue;
            hiveDrawable = R.drawable.hive_blue;
            leaveDrawable = R.drawable.leave_blue;
            parkDrawable = R.drawable.leave_blue;
        }


    }
}
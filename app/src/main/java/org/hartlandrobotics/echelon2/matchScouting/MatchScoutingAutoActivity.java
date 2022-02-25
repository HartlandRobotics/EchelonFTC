package org.hartlandrobotics.echelon2.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
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

public class MatchScoutingAutoActivity extends AppCompatActivity {
    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    private ImageButton topHubButton;
    private ImageButton bottomHubButton;
    private ImageButton humanPlayerButton;
    private ImageButton exitTarmacButton;

    private MaterialTextView topHubText;
    private MaterialTextView bottomHubText;
    private MaterialTextView humanPlayerText;

    int topHubButtonDrawable;
    int bottomHubButtonDrawable;
    int humanPlayoutButtonDrawable;
    int tarmacButtonDrawable;

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

        BlueAllianceStatus blueAllianceStatus = new BlueAllianceStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingAutoActivity.this, mr->{
                    if( mr == null ){
                        matchResult = matchResultViewModel.getDefault(blueAllianceStatus.getEventKey(), matchKey, teamKey);
                    } else {
                        matchResult = mr;
                    }

                    populateControlsFromData();
                });
    }

    public void populateControlsFromData(){
        topHubText.setText(String.valueOf(matchResult.getAutoHighBalls()));
        bottomHubText.setText(String.valueOf(matchResult.getAutoLowBalls()));
        humanPlayerText.setText(String.valueOf(matchResult.getAutoHumanPlayerShots()));

        if( matchResult.getTaxiTarmac() ){
            exitTarmacButton.setImageResource(R.drawable.taxi_tarmac_green);
        } else {
            exitTarmacButton.setImageResource(tarmacButtonDrawable);
        }

    }

    public void setupControls(){

        MaterialButton teleOpButton = findViewById(R.id.teleOp);
        teleOpButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            MatchScoutingTeleopActivity.launch(MatchScoutingAutoActivity.this, matchKey, teamKey );
        });

        topHubText = findViewById(R.id.topHubText);
        topHubButton = findViewById(R.id.topHub);
        topHubButton.setImageResource(topHubButtonDrawable);
        topHubButton.setOnClickListener(v -> {
            matchResult.setAutoHighBalls( matchResult.getAutoHighBalls() + 1);
            populateControlsFromData();
        });

        bottomHubText = findViewById(R.id.bottomHubText);
        bottomHubButton = findViewById(R.id.bottomHub);
        bottomHubButton.setImageResource(bottomHubButtonDrawable);
        bottomHubButton.setOnClickListener(v -> {
            matchResult.setAutoLowBalls( matchResult.getAutoLowBalls() + 1);
            populateControlsFromData();
        });

        humanPlayerText = findViewById(R.id.humanPlayerText);
        humanPlayerButton = findViewById(R.id.humanPlayer);
        humanPlayerButton.setImageResource(humanPlayoutButtonDrawable);
        humanPlayerButton.setOnClickListener(v -> {
            matchResult.setAutoHumanPlayerShots( matchResult.getAutoHumanPlayerShots() + 1);
            populateControlsFromData();
        });

        exitTarmacButton = findViewById(R.id.taxiTarmac);
        exitTarmacButton.setImageResource(humanPlayoutButtonDrawable);
        exitTarmacButton.setOnClickListener(v -> {
            matchResult.setTaxiTarmac( !matchResult.getTaxiTarmac() );
            populateControlsFromData();
        });

    }

    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        if (settings.getDeviceRole().startsWith("red")){
            topHubButtonDrawable = R.drawable.frc_hub_top_red;
            bottomHubButtonDrawable = R.drawable.frc_hub_bottom_red;
            humanPlayoutButtonDrawable =R.drawable.human_player_red;
            tarmacButtonDrawable = R.drawable.taxi_tarmac_red;
        } else {
            topHubButtonDrawable = R.drawable.frc_hub_top_blue;
            bottomHubButtonDrawable = R.drawable.frc_hub_bottom_blue;
            humanPlayoutButtonDrawable =R.drawable.human_player_blue;
            tarmacButtonDrawable = R.drawable.taxi_tarmac_blue;
        }
    }

}
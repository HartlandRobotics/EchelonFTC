package org.hartlandrobotics.echelon2.matchScouting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.configuration.AdminSettings;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;

public class MatchScoutingAutoActivity extends AppCompatActivity {

    private ImageButton topHubButton;
    private ImageButton bottomHubButton;
    private ImageButton humanPlayerButton;
    private MaterialTextView topHubText;
    private MaterialTextView bottomHubText;
    private MaterialTextView humanPlayerText;
    private Integer currentTopHubCount = 0;
    private Integer currentBottomHubCount = 0;
    private Integer currentHumanCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_match_scouting);

        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        MaterialButton teleOpButton = findViewById(R.id.teleOp);
        teleOpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchScoutingTeleopActvity.launch(MatchScoutingAutoActivity.this);
            }
        });
        topHubButton = findViewById(R.id.topHub);
        topHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTopHubCount++;
                populateControlsFromData();
            }
        });

        bottomHubButton = findViewById(R.id.bottomHub);
        bottomHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBottomHubCount++;
                populateControlsFromData();
            }
        });

        humanPlayerButton = findViewById(R.id.humanPlayer);
        humanPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHumanCount++;
                populateControlsFromData();
            }
        });

        topHubText = findViewById(R.id.topHubText);
        bottomHubText = findViewById(R.id.bottomHubText);
        humanPlayerText = findViewById(R.id.humanPlayerText);
        populateControlsFromData();

        if (settings.getDeviceRole().startsWith("red")){
            topHubButton.setImageResource(R.drawable.frc_hub_top_red);
            bottomHubButton.setImageResource(R.drawable.frc_hub_bottom_red);
            humanPlayerButton.setImageResource(R.drawable.human_player_red);

        }
    }
    public static void launch(Context context){
        Intent intent = new Intent(context, MatchScoutingAutoActivity.class);
        context.startActivity(intent);
    }

    public void populateControlsFromData(){
        topHubText.setText(String.valueOf(currentTopHubCount));
        bottomHubText.setText(String.valueOf(currentBottomHubCount));
        humanPlayerText.setText(String.valueOf(currentHumanCount));

    }

}
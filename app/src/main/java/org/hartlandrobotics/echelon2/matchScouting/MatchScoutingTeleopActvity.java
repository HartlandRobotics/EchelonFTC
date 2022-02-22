package org.hartlandrobotics.echelon2.matchScouting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.configuration.AdminSettings;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;

public class MatchScoutingTeleopActvity extends AppCompatActivity {

    MaterialButton scoutingDoneButton;
    private ImageButton topHubButton;
    private ImageButton bottomHubButton;
    private MaterialTextView topHubText;
    private MaterialTextView bottomHubText;
    private Integer currentTopHubCount = 0;
    private Integer currentBottomHubCount = 0;
    private MaterialButton lowButton;
    private MaterialButton midButton;
    private MaterialButton highButton;
    private MaterialButton traversalButton;
    private boolean isOnLow = false;
    private boolean isOnMid = false;
    private boolean isOnHigh = false;
    private boolean isOnTraversal = false;
    private int buttonColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_scouting_actvity);

        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        scoutingDoneButton = findViewById(R.id.scoutingDone);
        scoutingDoneButton.setOnClickListener(v -> {
            // move to summary screen
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

        traversalButton = findViewById(R.id.traversal);
        traversalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnTraversal = !isOnTraversal;
                populateControlsFromData();
            }
        });

        highButton = findViewById(R.id.high);
        highButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnHigh = !isOnHigh;
                populateControlsFromData();
            }
        });

        midButton = findViewById(R.id.mid);
        midButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnMid = !isOnMid;
                populateControlsFromData();
            }
        });

        lowButton = findViewById(R.id.low);
        lowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnLow = !isOnLow;
                populateControlsFromData();
            }
        });

        topHubText = findViewById(R.id.topHubText);
        bottomHubText = findViewById(R.id.bottomHubText);

        if (settings.getDeviceRole().startsWith("red")){
            topHubButton.setImageResource(R.drawable.frc_hub_top_red);
            bottomHubButton.setImageResource(R.drawable.frc_hub_bottom_red);
            buttonColor = R.color.redAlliance;
        }

        else {
            buttonColor = R.color.blueAlliance;
        }

        populateControlsFromData();



    }

    public static void launch(Context context){
        Intent intent = new Intent(context, MatchScoutingTeleopActvity.class);
        context.startActivity(intent);
    }

    public void populateControlsFromData(){
        topHubText.setText(String.valueOf(currentTopHubCount));
        bottomHubText.setText(String.valueOf(currentBottomHubCount));

        if (isOnTraversal){
            traversalButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryColor)));
            traversalButton.setTextColor(getResources().getColor(buttonColor));
        }
        else {
            traversalButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            traversalButton.setTextColor(getResources().getColor(R.color.primaryColor));
        }

        if (isOnHigh){
            highButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryColor)));
            highButton.setTextColor(getResources().getColor(buttonColor));
        }
        else {
            highButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            highButton.setTextColor(getResources().getColor(R.color.primaryColor));
        }
        if (isOnMid){
            midButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryColor)));
            midButton.setTextColor(getResources().getColor(buttonColor));
        }
        else {
            midButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            midButton.setTextColor(getResources().getColor(R.color.primaryColor));
        }
        if (isOnLow){
            lowButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryColor)));
            lowButton.setTextColor(getResources().getColor(buttonColor));
        }
        else {
            lowButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            lowButton.setTextColor(getResources().getColor(R.color.primaryColor));
        }
    }

}
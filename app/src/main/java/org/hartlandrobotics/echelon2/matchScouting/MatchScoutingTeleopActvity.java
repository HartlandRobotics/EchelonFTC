package org.hartlandrobotics.echelon2.matchScouting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import org.hartlandrobotics.echelon2.R;

public class MatchScoutingTeleopActvity extends AppCompatActivity {
    MaterialButton scoutingDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_scouting_actvity);

        scoutingDoneButton = findViewById(R.id.scoutingDone);
        scoutingDoneButton.setOnClickListener(v -> {
            // move to summary screen
        });
    }

    public static void launch(Context context){
        Intent intent = new Intent(context, MatchScoutingTeleopActvity.class);
        context.startActivity(intent);
    }



}
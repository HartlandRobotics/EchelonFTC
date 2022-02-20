package org.hartlandrobotics.echelon2.matchScouting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.hartlandrobotics.echelon2.R;

public class MatchScoutingActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_scouting_actvity);


    }

    public static void launch(Context context){
        Intent intent = new Intent(context, MatchScoutingActvity.class);
        context.startActivity(intent);
    }



}
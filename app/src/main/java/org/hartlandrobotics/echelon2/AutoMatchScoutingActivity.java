package org.hartlandrobotics.echelon2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class AutoMatchScoutingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_match_scouting);

        MaterialButton teleOpButton = findViewById(R.id.teleOp);
        teleOpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchScoutingActvity.launch(AutoMatchScoutingActivity.this);
            }
        });
    }
    public static void launch(Context context){
        Intent intent = new Intent(context, AutoMatchScoutingActivity.class);
        context.startActivity(intent);
    }


}
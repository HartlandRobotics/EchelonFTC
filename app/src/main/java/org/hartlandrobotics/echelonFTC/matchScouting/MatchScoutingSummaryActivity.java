package org.hartlandrobotics.echelonFTC.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.models.MatchResultViewModel;
//import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;
import org.hartlandrobotics.echelonFTC.ftcapi.status.*;

public class MatchScoutingSummaryActivity extends AppCompatActivity {
    private static final String TAG = "MatchScoutingSummaryActivity";

    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    private String matchKey;
    private String teamKey;

    MatchResultViewModel matchResultViewModel;
    MatchResult matchResult;

    // auto
    private MaterialButton autoClassifiedDecrement;
    private MaterialTextView autoClassifiedValue;
    private MaterialButton autoClassifiedIncrement;

    private MaterialButton autoOverflowDecrement;
    private MaterialTextView autoOverflowValue;
    private MaterialButton autoOverflowIncrement;

    private MaterialButton autoMotifDecrement;
    private MaterialTextView autoMotifValue;
    private MaterialButton autoMotifIncrement;

    private MaterialButton autoMissedDecrement;
    private MaterialTextView autoMissedValue;
    private MaterialButton autoMissedIncrement;

    private MaterialCheckBox autoLeave;

    private MaterialButton teleOpClassifiedDecrement;
    private MaterialTextView teleOpClassifiedValue;
    private MaterialButton teleOpClassifiedIncrement;

    private MaterialButton teleOpOverflowDecrement;
    private MaterialTextView teleOpOverflowValue;
    private MaterialButton teleOpOverflowIncrement;

    private MaterialButton teleOpDepotDecrement;
    private MaterialTextView teleOpDepotValue;
    private MaterialButton teleOpDepotIncrement;

    private MaterialButton teleOpMotifDecrement;
    private MaterialTextView teleOpMotifValue;
    private MaterialButton teleOpMotifIncrement;

    private MaterialButton teleOpMissedDecrement;
    private MaterialTextView teleOpMissedValue;
    private MaterialButton teleOpMissedIncrement;

    //private MaterialButton teleOpDefensesDecrement;
    //private MaterialTextView teleOpDefensesValue;
    //private MaterialButton teleOpDefensesIncrement;

    private MaterialButton endBaseCycle;
    private MaterialTextView endBaseValue;

    private MaterialCheckBox endTwobotsCheckBox;

    private TextInputLayout additionalNotesLayout;

    private MaterialButton submitButton;

    public static void launch(Context context, String matchKey, String teamKey){
        Intent intent = new Intent(context, MatchScoutingSummaryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MATCH_KEY, matchKey);
        bundle.putString(TEAM_KEY, teamKey);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summary);

        setupControls();

        Bundle bundle = getIntent().getExtras();
        matchKey = bundle.getString(MATCH_KEY);
        teamKey = bundle.getString(TEAM_KEY);
        FtcApiStatus orangeAllianceStatus = new FtcApiStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingSummaryActivity.this, mr->{
                    if( mr == null ){
                        matchResult = matchResultViewModel.getDefault(orangeAllianceStatus.getEventKey(), matchKey, teamKey);
                    } else {
                        matchResult = mr;
                    }

                    populateControlsFromData();
                });
    }

    private void setupControls(){

        //Auto
        autoClassifiedValue = findViewById(R.id.autoClassifiedValue);
        autoClassifiedDecrement = findViewById(R.id.autoClassifiedDecrement);
        autoClassifiedDecrement.setOnClickListener(v -> {
                matchResult.setAutoInt6(Math.max(matchResult.getAutoInt6()-1,0));
                populateControlsFromData();
                });
        autoClassifiedIncrement = findViewById(R.id.autoClassifiedIncrement);
        autoClassifiedIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt6( matchResult.getAutoInt6()+1);
            populateControlsFromData();
        });

        autoOverflowValue = findViewById(R.id.autoOverflowValue);
        autoOverflowDecrement = findViewById(R.id.autoOverflowDecrement);
        autoOverflowDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt7( Math.max(matchResult.getAutoInt7()-1,0) );
            populateControlsFromData();
        });
        autoOverflowIncrement = findViewById(R.id.autoOverflowIncrement);
        autoOverflowIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt7( matchResult.getAutoInt7()+1 );
            populateControlsFromData();
        });

        autoMotifValue = findViewById(R.id.autoMotifValue);
        autoMotifDecrement = findViewById(R.id.autoMotifDecrement);
        autoMotifDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt8( Math.max(matchResult.getAutoInt8()-1, 0) );
            populateControlsFromData();
        });
        autoMotifIncrement = findViewById(R.id.autoMotifIncrement);
        autoMotifIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt8( matchResult.getAutoInt8()+1 );
            populateControlsFromData();
        });

        autoMissedValue = findViewById(R.id.autoMissedValue);
        autoMissedDecrement = findViewById(R.id.autoMissedDecrement);
        autoMissedDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt10( Math.max(matchResult.getAutoInt10()-1, 0) );
            populateControlsFromData();
        });
        autoMissedIncrement = findViewById(R.id.autoMissedIncrement);
        autoMissedIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt10( matchResult.getAutoInt10()+1 );
            populateControlsFromData();
        });


        autoLeave = findViewById(R.id.autoLeaveCheckbox);
        autoLeave.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setAutoFlag1(isChecked);
            populateControlsFromData();
        });
        
        //TeleOp
        teleOpClassifiedValue = findViewById(R.id.teleOpClassifiedValue);
        teleOpClassifiedDecrement = findViewById(R.id.teleOpClassifiedDecrement);
        teleOpClassifiedDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt1(Math.max(matchResult.getTeleOpInt1()-1,0));
            populateControlsFromData();
        });
        teleOpClassifiedIncrement = findViewById(R.id.teleOpClassifiedIncrement);
        teleOpClassifiedIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt1( matchResult.getTeleOpInt1()+1);
            populateControlsFromData();
        });

        teleOpOverflowValue = findViewById(R.id.teleOpOverflowValue);
        teleOpOverflowDecrement = findViewById(R.id.teleOpOverflowDecrement);
        teleOpOverflowDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt2( Math.max(matchResult.getTeleOpInt2()-1,0) );
            populateControlsFromData();
        });
        teleOpOverflowIncrement = findViewById(R.id.teleOpOverflowIncrement);
        teleOpOverflowIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt2( matchResult.getTeleOpInt2()+1 );
            populateControlsFromData();
        });

        teleOpMotifValue = findViewById(R.id.teleOpMotifValue);
        teleOpMotifDecrement = findViewById(R.id.teleOpMotifDecrement);
        teleOpMotifDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt4( Math.max(matchResult.getTeleOpInt4()-1,0) );
            populateControlsFromData();
        });
        teleOpMotifIncrement = findViewById(R.id.teleOpMotifIncrement);
        teleOpMotifIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt4( matchResult.getTeleOpInt4()+1 );
            populateControlsFromData();
        });

        teleOpDepotValue = findViewById(R.id.teleOpDepotValue);
        teleOpDepotDecrement = findViewById(R.id.teleOpDepotDecrement);
        teleOpDepotDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt3( Math.max(matchResult.getTeleOpInt3()-1,0) );
            populateControlsFromData();
        });
        teleOpDepotIncrement = findViewById(R.id.teleOpDepotIncrement);
        teleOpDepotIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt3( matchResult.getTeleOpInt3()+1 );
            populateControlsFromData();
        });


        teleOpMissedValue = findViewById(R.id.teleOpMissedValue);
        teleOpMissedDecrement = findViewById(R.id.teleOpMissedDecrement);
        teleOpMissedDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt5( Math.max(matchResult.getTeleOpInt5()-1,0) );
            populateControlsFromData();
        });
        teleOpMissedIncrement = findViewById(R.id.teleOpMissedIncrement);
        teleOpMissedIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt5( matchResult.getTeleOpInt5()+1 );
            populateControlsFromData();
        });





        endBaseCycle = findViewById(R.id.endBaseCycle);
        endBaseValue = findViewById(R.id.endBaseValue);
        endBaseCycle.setOnClickListener(v -> {
            matchResult.setEndInt6((matchResult.getEndInt6()+1)%3);
            populateControlsFromData();
        });

        endTwobotsCheckBox = findViewById(R.id.endTwoBotsCheckbox);
        endTwobotsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setEndFlag1(isChecked);
            populateControlsFromData();
        });

        submitButton = findViewById(R.id.matchSummarySaveButton);
        submitButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            Log.i(TAG, "current match key is " + matchKey);
            //2324-FIM-HAQ-Q001-1
            try{
                String[] tokens = matchKey.split("_");
                //String matchNumberTokens = tokens[2];
                String matchNumberTokens = tokens[tokens.length - 1 ];

                //String[] matchTokens = matchNumberTokens.split("Q");
                Integer nextMatchNumber = Integer.valueOf(matchNumberTokens) + 1;
                MatchSelectionActivity.launch(MatchScoutingSummaryActivity.this, nextMatchNumber);
            }
            catch (Exception e){
                MatchSelectionActivity.launch(MatchScoutingSummaryActivity.this, null);
            }

        });

        additionalNotesLayout = findViewById(R.id.additionalNotes);
        additionalNotesLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                matchResult.setAdditionalNotes( s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    volatile boolean populating = false;
    private void populateControlsFromData() {
        if( matchResult == null ) return;
        if( populating == true ) return;

        populating = true;

        autoLeave.setChecked(matchResult.getAutoFlag1());
        autoClassifiedValue.setText( String.valueOf( matchResult.getAutoInt6() ));
        autoOverflowValue.setText( String.valueOf( matchResult.getAutoInt7() ));
        autoMotifValue.setText( String.valueOf( matchResult.getAutoInt8() ));
        autoMissedValue.setText(String.valueOf(matchResult.getAutoInt10()));

        teleOpClassifiedValue.setText( String.valueOf( matchResult.getTeleOpInt1() ));
        teleOpOverflowValue.setText( String.valueOf( matchResult.getTeleOpInt2() ));
        teleOpMotifValue.setText( String.valueOf( matchResult.getTeleOpInt4() ));
        teleOpDepotValue.setText( String.valueOf( matchResult.getTeleOpInt3() ));
       teleOpMissedValue.setText(String.valueOf(matchResult.getTeleOpInt5() ));


        int baseNum = matchResult.getEndInt6();
        if( baseNum == 0 ){
            endBaseValue.setText("None");
        } else if ( baseNum == 1 ){
            endBaseValue.setText("Part");
        } else if ( baseNum == 2 ){
            endBaseValue.setText("Full");
        }
        endTwobotsCheckBox.setChecked( matchResult.getEndFlag1());

        //teleOpDefensesValue.setText( String.valueOf( matchResult.getDefenseCount() ));

        additionalNotesLayout.getEditText().setText(matchResult.getAdditionalNotes());

        populating = false;

    }
}
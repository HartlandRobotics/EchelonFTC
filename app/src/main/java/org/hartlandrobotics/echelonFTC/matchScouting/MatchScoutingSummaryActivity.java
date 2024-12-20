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

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.models.MatchResultViewModel;
import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;

public class MatchScoutingSummaryActivity extends AppCompatActivity {
    private static final String TAG = "MatchScoutingSummaryActivity";

    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    private String matchKey;
    private String teamKey;

    MatchResultViewModel matchResultViewModel;
    MatchResult matchResult;

    // auto
    private MaterialButton autoSampleHighDecrement;
    private MaterialTextView autoSampleHighValue;
    private MaterialButton autoSampleHighIncrement;

    private MaterialButton autoSampleLowDecrement;
    private MaterialTextView autoSampleLowValue;
    private MaterialButton autoSampleLowIncrement;

    private MaterialButton autoSampleNetZoneDecrement;
    private MaterialTextView autoSampleNetZoneValue;
    private MaterialButton autoSampleNetZoneIncrement;

    private MaterialButton autoSpecimenHighDecrement;
    private MaterialTextView autoSpecimenHighValue;
    private MaterialButton autoSpecimenHighIncrement;

    private MaterialButton autoSpecimenLowDecrement;
    private MaterialTextView autoSpecimenLowValue;
    private MaterialButton autoSpecimenLowIncrement;

    private MaterialCheckBox autoObservationZone;
    private MaterialCheckBox autoLevel1;

    private MaterialButton teleOpSampleHighDecrement;
    private MaterialTextView teleOpSampleHighValue;
    private MaterialButton teleOpSampleHighIncrement;

    private MaterialButton teleOpSampleLowDecrement;
    private MaterialTextView teleOpSampleLowValue;
    private MaterialButton teleOpSampleLowIncrement;

    private MaterialButton teleOpSampleNetZoneDecrement;
    private MaterialTextView teleOpSampleNetZoneValue;
    private MaterialButton teleOpSampleNetZoneIncrement;

    private MaterialButton teleOpSpecimenHighDecrement;
    private MaterialTextView teleOpSpecimenHighValue;
    private MaterialButton teleOpSpecimenHighIncrement;

    private MaterialButton teleOpSpecimenLowDecrement;
    private MaterialTextView teleOpSpecimenLowValue;
    private MaterialButton teleOpSpecimenLowIncrement;





    //private MaterialButton teleOpDefensesDecrement;
    //private MaterialTextView teleOpDefensesValue;
    //private MaterialButton teleOpDefensesIncrement;

    private MaterialCheckBox endHighCheckBox;
    private MaterialCheckBox endMidCheckBox;
    private MaterialCheckBox endLowCheckBox;

    private MaterialCheckBox endObservationZoneCheckBox;

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
        OrangeAllianceStatus orangeAllianceStatus = new OrangeAllianceStatus(getApplicationContext());

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
        autoSampleHighValue = findViewById(R.id.autoSampleHighValue);
        autoSampleHighDecrement = findViewById(R.id.autoSampleHighDecrement);
        autoSampleHighDecrement.setOnClickListener(v -> {
                matchResult.setAutoInt6(Math.max(matchResult.getAutoInt6()-1,0));
                populateControlsFromData();
                });
        autoSampleHighIncrement = findViewById(R.id.autoSampleHighIncrement);
        autoSampleHighIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt6( matchResult.getAutoInt6()+1);
            populateControlsFromData();
        });

        autoSampleLowValue = findViewById(R.id.autoSampleLowValue);
        autoSampleLowDecrement = findViewById(R.id.autoSampleLowDecrement);
        autoSampleLowDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt7( Math.max(matchResult.getAutoInt7()-1,0) );
            populateControlsFromData();
        });
        autoSampleLowIncrement = findViewById(R.id.autoSampleLowIncrement);
        autoSampleLowIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt7( matchResult.getAutoInt7()+1 );
            populateControlsFromData();
        });

        autoSampleNetZoneValue = findViewById(R.id.autoSampleNetZoneValue);
        autoSampleNetZoneDecrement = findViewById(R.id.autoSampleNetZoneDecrement);
        autoSampleNetZoneDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt8( Math.max(matchResult.getAutoInt8()-1, 0) );
            populateControlsFromData();
        });
        autoSampleNetZoneIncrement = findViewById(R.id.autoSampleNetZoneIncrement);
        autoSampleNetZoneIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt8( matchResult.getAutoInt8()+1 );
            populateControlsFromData();
        });


        autoSpecimenHighValue = findViewById(R.id.autoSpecimenHighValue);
        autoSpecimenHighDecrement = findViewById(R.id.autoSpecimenHighDecrement);
        autoSpecimenHighDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt9(Math.max( matchResult.getAutoInt9()-1,0) );
            populateControlsFromData();
        });
        autoSpecimenHighIncrement = findViewById(R.id.autoSpecimenHighIncrement);
        autoSpecimenHighIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt9( matchResult.getAutoInt9()+1 );
            populateControlsFromData();
        });


        autoSpecimenLowValue = findViewById(R.id.autoSpecimenLowValue);
        autoSpecimenLowDecrement = findViewById(R.id.autoSpecimenLowDecrement);
        autoSpecimenLowDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt10( Math.max(matchResult.getAutoInt10()-1,0) );
            populateControlsFromData();
        });
        autoSpecimenLowIncrement = findViewById(R.id.autoSpecimenLowIncrement);
        autoSpecimenLowIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt10( matchResult.getAutoInt10()+1 );
            populateControlsFromData();
        });

        autoObservationZone = findViewById(R.id.autoObservationZoneCheckBox);
        autoObservationZone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setAutoFlag1(isChecked);
            populateControlsFromData();
        });
        autoLevel1 = findViewById(R.id.autoLevel1CheckBox);
        autoLevel1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setAutoFlag2(isChecked);
            populateControlsFromData();
        });




        //TeleOp
        teleOpSampleHighValue = findViewById(R.id.teleOpSampleHighValue);
        teleOpSampleHighDecrement = findViewById(R.id.teleOpSampleHighDecrement);
        teleOpSampleHighDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt1(Math.max(matchResult.getTeleOpInt1()-1,0));
            populateControlsFromData();
        });
        teleOpSampleHighIncrement = findViewById(R.id.teleOpSampleHighIncrement);
        teleOpSampleHighIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt1( matchResult.getTeleOpInt1()+1);
            populateControlsFromData();
        });

        teleOpSampleLowValue = findViewById(R.id.teleOpSampleLowValue);
        teleOpSampleLowDecrement = findViewById(R.id.teleOpSampleLowDecrement);
        teleOpSampleLowDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt2( Math.max(matchResult.getTeleOpInt2()-1,0) );
            populateControlsFromData();
        });
        teleOpSampleLowIncrement = findViewById(R.id.teleOpSampleLowIncrement);
        teleOpSampleLowIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt2( matchResult.getTeleOpInt2()+1 );
            populateControlsFromData();
        });


        teleOpSampleNetZoneValue = findViewById(R.id.teleOpSampleNetZoneValue);
        teleOpSampleNetZoneDecrement = findViewById(R.id.teleOpSampleNetZoneDecrement);
        teleOpSampleNetZoneDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt3( Math.max(matchResult.getTeleOpInt3()-1,0) );
            populateControlsFromData();
        });
        teleOpSampleNetZoneIncrement = findViewById(R.id.teleOpSampleNetZoneIncrement);
        teleOpSampleNetZoneIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt3( matchResult.getTeleOpInt3()+1 );
            populateControlsFromData();
        });


        teleOpSpecimenHighValue = findViewById(R.id.teleOpSpecimenHighValue);
        teleOpSpecimenHighDecrement = findViewById(R.id.teleOpSpecimenHighDecrement);
        teleOpSpecimenHighDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt4( Math.max(matchResult.getTeleOpInt4()-1,0) );
            populateControlsFromData();
        });
        teleOpSpecimenHighIncrement = findViewById(R.id.teleOpSpecimenHighIncrement);
        teleOpSpecimenHighIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt4( matchResult.getTeleOpInt4()+1 );
            populateControlsFromData();
        });


        teleOpSpecimenLowValue = findViewById(R.id.teleOpSpecimenLowValue);
        teleOpSpecimenLowDecrement = findViewById(R.id.teleOpSpecimenLowDecrement);
        teleOpSpecimenLowDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt5( Math.max(matchResult.getTeleOpInt5()-1,0) );
            populateControlsFromData();
        });
        teleOpSpecimenLowIncrement = findViewById(R.id.teleOpSpecimenLowIncrement);
        teleOpSpecimenLowIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt5( matchResult.getTeleOpInt5()+1 );
            populateControlsFromData();
        });









        endHighCheckBox = findViewById(R.id.endHighAscentCheckbox);
        endHighCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setEndFlag4(isChecked);
            populateControlsFromData();
        });
        endMidCheckBox = findViewById(R.id.endMidAscentCheckbox);
        endMidCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setEndFlag3(isChecked);
            populateControlsFromData();
        });
        endLowCheckBox = findViewById(R.id.endLowAscentCheckbox);
        endLowCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setEndFlag2(isChecked);
            populateControlsFromData();
        });

        endObservationZoneCheckBox = findViewById(R.id.endObservationZoneCheckbox);
        endObservationZoneCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setEndFlag1(isChecked);
            populateControlsFromData();
        });

        submitButton = findViewById(R.id.matchSummarySaveButton);
        submitButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            Log.i(TAG, "current match key is " + matchKey);
            //2324-FIM-HAQ-Q001-1
            try{
                String[] tokens = matchKey.split("-");
                String matchNumberTokens = tokens[3];
                String[] matchTokens = matchNumberTokens.split("Q");
                Integer nextMatchNumber = Integer.valueOf(matchTokens[1]) + 1;
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

        autoSampleHighValue.setText( String.valueOf( matchResult.getAutoInt6() ));
        autoSampleLowValue.setText( String.valueOf( matchResult.getAutoInt7() ));
        autoSampleNetZoneValue.setText( String.valueOf( matchResult.getAutoInt8() ));

        autoSpecimenHighValue.setText( String.valueOf( matchResult.getAutoInt9() ));
        autoSpecimenLowValue.setText( String.valueOf( matchResult.getAutoInt10() ));

        autoObservationZone.setChecked(matchResult.getAutoFlag1());
        autoLevel1.setChecked(matchResult.getAutoFlag2());


        teleOpSampleHighValue.setText( String.valueOf( matchResult.getTeleOpInt1() ));
        teleOpSampleLowValue.setText( String.valueOf( matchResult.getTeleOpInt2() ));
        teleOpSampleNetZoneValue.setText( String.valueOf( matchResult.getTeleOpInt3() ));

        teleOpSpecimenHighValue.setText( String.valueOf( matchResult.getTeleOpInt4() ));
        teleOpSpecimenLowValue.setText( String.valueOf( matchResult.getTeleOpInt5() ));




        //teleOpDefensesValue.setText( String.valueOf( matchResult.getDefenseCount() ));
        endObservationZoneCheckBox.setChecked( matchResult.getEndFlag1() );

        endHighCheckBox.setChecked( matchResult.getEndFlag4() );
        endMidCheckBox.setChecked( matchResult.getEndFlag3() );
        endLowCheckBox.setChecked( matchResult.getEndFlag2() );

        additionalNotesLayout.getEditText().setText(matchResult.getAdditionalNotes());

        populating = false;

    }
}
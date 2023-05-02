package org.hartlandrobotics.echelon2.matchScouting;

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

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.models.MatchResultViewModel;
import org.hartlandrobotics.echelon2.status.OrangeAllianceStatus;

public class MatchScoutingSummaryActivity extends AppCompatActivity {
    private static final String TAG = "MatchScoutingSummaryActivity";

    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    private String matchKey;
    private String teamKey;

    MatchResultViewModel matchResultViewModel;
    MatchResult matchResult;

    // auto
    private MaterialButton autoHighDecrement;
    private MaterialTextView autoHighValue;
    private MaterialButton autoHighIncrement;

    private MaterialButton autoLowDecrement;
    private MaterialTextView autoLowValue;
    private MaterialButton autoLowIncrement;

    private MaterialButton autoHumanDecrement;
    private MaterialTextView autoHumanValue;
    private MaterialButton autoHumanIncrement;

    private MaterialCheckBox autoTarmac;

    private MaterialButton teleOpHighDecrement;
    private MaterialTextView teleOpHighValue;
    private MaterialButton teleOpHighIncrement;

    private MaterialButton teleOpLowDecrement;
    private MaterialTextView teleOpLowValue;
    private MaterialButton teleOpLowIncrement;

    private MaterialButton teleOpDefensesDecrement;
    private MaterialTextView teleOpDefensesValue;
    private MaterialButton teleOpDefensesIncrement;

    private MaterialCheckBox endTraverseCheckbox;
    private MaterialCheckBox endHighCheckBox;
    private MaterialCheckBox endMidCheckBox;
    private MaterialCheckBox endLowCheckBox;

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
        autoHighValue = findViewById(R.id.autoHighValue);
        autoHighDecrement = findViewById(R.id.autoHighDecrement);
        autoHighDecrement.setOnClickListener(v -> {
                matchResult.setAutoHighBalls( Math.max( matchResult.getAutoHighBalls() - 1, 0 ));
                populateControlsFromData();
                });
        autoHighIncrement = findViewById(R.id.autoHighIncrement);
        autoHighIncrement.setOnClickListener(v -> {
            matchResult.setAutoHighBalls( matchResult.getAutoHighBalls() + 1);
            populateControlsFromData();
        });

        autoLowValue = findViewById(R.id.autoLowValue);
        autoLowDecrement = findViewById(R.id.autoLowDecrement);
        autoLowDecrement.setOnClickListener(v -> {
            matchResult.setAutoLowBalls( Math.max(matchResult.getAutoLowBalls() -1, 0));
            populateControlsFromData();
        });
        autoLowIncrement = findViewById(R.id.autoLowIncrement);
        autoLowIncrement.setOnClickListener(v -> {
            matchResult.setAutoLowBalls( matchResult.getAutoLowBalls() +1 );
            populateControlsFromData();
        });

        autoHumanValue = findViewById(R.id.autoHumanValue);
        autoHumanDecrement = findViewById(R.id.autoHumanDecrement);
        autoHumanDecrement.setOnClickListener(v -> {
            matchResult.setAutoHumanPlayerShots( Math.max( matchResult.getAutoHumanPlayerShots()-1,0));
            populateControlsFromData();
        });
        autoHumanIncrement = findViewById(R.id.autoHumanIncrement);
        autoHumanIncrement.setOnClickListener(v -> {
            matchResult.setAutoHumanPlayerShots( matchResult.getAutoHumanPlayerShots()+1);
            populateControlsFromData();
        });

        autoTarmac = findViewById(R.id.autoTarmacCheckBox);
        autoTarmac.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setTaxiTarmac(isChecked);
            populateControlsFromData();
        });

        teleOpHighValue = findViewById(R.id.teleOpHighValue);
        teleOpHighDecrement = findViewById(R.id.teleOpHighDecrement);
        teleOpHighDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpHighBalls( Math.max( matchResult.getTeleOpHighBalls() -1, 0));
            populateControlsFromData();
        });
        teleOpHighIncrement = findViewById(R.id.teleOpHighIncrement);
        teleOpHighIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpHighBalls( matchResult.getTeleOpHighBalls()+1);
            populateControlsFromData();
        });

        teleOpLowValue = findViewById(R.id.teleOpLowValue);
        teleOpLowDecrement = findViewById(R.id.teleOpLowDecrement);
        teleOpLowDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpLowBalls(Math.max(matchResult.getTeleOpLowBalls()-1, 0));
            populateControlsFromData();
        });
        teleOpLowIncrement = findViewById(R.id.teleOpLowIncrement);
        teleOpLowIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpLowBalls(matchResult.getTeleOpLowBalls()+1);
            populateControlsFromData();
        });

        teleOpDefensesValue = findViewById(R.id.teleOpDefensesValue);
        teleOpDefensesDecrement = findViewById(R.id.teleOpDefensesDecrement);
        teleOpDefensesDecrement.setOnClickListener(v -> {
            matchResult.setDefenseCount(Math.max(matchResult.getDefenseCount()-1, 0));
            populateControlsFromData();
        });
        teleOpDefensesIncrement = findViewById(R.id.teleOpDefensesIncrement);
        teleOpDefensesIncrement.setOnClickListener(v -> {
            matchResult.setDefenseCount(matchResult.getDefenseCount()+1);
            populateControlsFromData();
        });


        endTraverseCheckbox = findViewById(R.id.endTraverseCheckbox);
        endTraverseCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setEndHangTraverse(isChecked);
            populateControlsFromData();
        });
        endHighCheckBox = findViewById(R.id.endHighCheckbox);
        endHighCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setEndHangHigh(isChecked);
            populateControlsFromData();
        });
        endMidCheckBox = findViewById(R.id.endMidCheckbox);
        endMidCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setEndHangMid(isChecked);
            populateControlsFromData();
        });
        endLowCheckBox = findViewById(R.id.endLowCheckbox);
        endLowCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setEndHangLow(isChecked);
            populateControlsFromData();
        });

        submitButton = findViewById(R.id.matchSummarySaveButton);
        submitButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            Log.i(TAG, "current match key is " + matchKey);
            // 2020mimil_qm1
            String[] tokens = matchKey.split("_qm");
            String matchNumberStr = tokens[1];
            Integer nextMatchNumber = Integer.valueOf(matchNumberStr) + 1;
            MatchSelectionActivity.launch(MatchScoutingSummaryActivity.this, nextMatchNumber);
        });

        additionalNotesLayout = findViewById(R.id.additionalNotes);
        additionalNotesLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                matchResult.setAdditionalNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void populateControlsFromData() {
        if( matchResult == null ) return;

        autoHighValue.setText( String.valueOf( matchResult.getAutoHighBalls() ));
        autoLowValue.setText( String.valueOf( matchResult.getAutoLowBalls() ));
        autoHumanValue.setText( String.valueOf( matchResult.getAutoHumanPlayerShots() ));

        autoTarmac.setChecked( matchResult.getTaxiTarmac() );

        teleOpHighValue.setText( String.valueOf( matchResult.getTeleOpHighBalls() ));
        teleOpLowValue.setText( String.valueOf( matchResult.getTeleOpLowBalls() ));
        teleOpDefensesValue.setText( String.valueOf( matchResult.getDefenseCount() ));

        endTraverseCheckbox.setChecked( matchResult.getEndHangTraverse() );
        endHighCheckBox.setChecked( matchResult.getEndHangHigh() );
        endMidCheckBox.setChecked( matchResult.getEndHangMid() );
        endLowCheckBox.setChecked( matchResult.getEndHangLow() );

        additionalNotesLayout.getEditText().setText(matchResult.getAdditionalNotes());
    }
}
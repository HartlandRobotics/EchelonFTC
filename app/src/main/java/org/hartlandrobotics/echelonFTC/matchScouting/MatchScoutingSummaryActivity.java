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
import org.hartlandrobotics.echelonFTC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.models.MatchResultViewModel;
//import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;
import org.hartlandrobotics.echelonFTC.ftcapi.status.*;
import org.hartlandrobotics.echelonFTC.utilities.RoleUtilities;

public class MatchScoutingSummaryActivity extends AppCompatActivity {
    private static final String TAG = "MatchScoutingSummaryActivity";

    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    private String matchKey;
    private String teamKey;

    MatchResultViewModel matchResultViewModel;
    MatchResult matchResult;

    // auto
    private MaterialButton autoNectarDecrement;
    private MaterialTextView autoNectarValue;
    private MaterialButton autoNectarIncrement;

    private MaterialButton autoPollenDecrement;
    private MaterialTextView autoPollenValue;
    private MaterialButton autoPollenIncrement;

    private MaterialButton autoMissedDecrement;
    private MaterialTextView autoMissedValue;
    private MaterialButton autoMissedIncrement;

    private MaterialButton autoHiveTipDecrement;
    private MaterialTextView autoHiveTipValue;
    private MaterialButton autoHiveTipIncrement;

    private MaterialCheckBox autoLeave;
    private MaterialCheckBox autoPark;


    private MaterialButton teleOpNectarDecrement;
    private MaterialTextView teleOpNectarValue;
    private MaterialButton teleOpNectarIncrement;

    private MaterialButton teleOpPollenDecrement;
    private MaterialTextView teleOpPollenValue;
    private MaterialButton teleOpPollenIncrement;

    private MaterialButton teleOpMissedDecrement;
    private MaterialTextView teleOpMissedValue;
    private MaterialButton teleOpMissedIncrement;

    private MaterialButton teleOpHiveTipDecrement;
    private MaterialTextView teleOpHiveTipValue;
    private MaterialButton teleOpHiveTipIncrement;


    private MaterialButton teleOpHiveRemainingDecrement;
    private MaterialTextView teleOpHiveRemainingValue;
    private MaterialButton teleOpHiveRemainingIncrement;

    private MaterialButton endOwnedDecrement;
    private MaterialTextView endOwnedValue;
    private MaterialButton endOwnedIncrement;

    private MaterialButton endBottomDecrement;
    private MaterialTextView endBottomValue;
    private MaterialButton endBottomIncrement;

    private MaterialButton endGardenDecrement;
    private MaterialTextView endGardenValue;
    private MaterialButton endGardenIncrement;

    private MaterialCheckBox endParkCheckBox;
    private TextInputLayout additionalNotesLayout;

    private MaterialButton submitButton;

    public static void launch(Context context, String matchKey, String teamKey) {
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
        AdminSettings adminStatus = AdminSettingsProvider.getAdminSettings(getApplicationContext());
        String role = adminStatus.getDeviceRole();
        String alliance = RoleUtilities.deviceColor(role);
        FtcApiStatus ftcApiStatus = new FtcApiStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingSummaryActivity.this, mr -> {
                    if (mr == null) {
                        matchResult = matchResultViewModel.getDefault(ftcApiStatus.getEventKey(), matchKey, teamKey, alliance);
                    } else {
                        matchResult = mr;
                    }

                    populateControlsFromData();
                });
    }

    private void setupControls() {

        //Auto
        autoNectarValue = findViewById(R.id.autoNectarValue);
        autoNectarDecrement = findViewById(R.id.autoNectarDecrement);
        autoNectarDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt6(Math.max(matchResult.getAutoInt6() - 1, 0));
            populateControlsFromData();
        });
        autoNectarIncrement = findViewById(R.id.autoNectarIncrement);
        autoNectarIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt6(matchResult.getAutoInt6() + 1);
            populateControlsFromData();
        });

        autoPollenValue = findViewById(R.id.autoPollenValue);
        autoPollenDecrement = findViewById(R.id.autoPollenDecrement);
        autoPollenDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt7(Math.max(matchResult.getAutoInt7() - 1, 0));
            populateControlsFromData();
        });
        autoPollenIncrement = findViewById(R.id.autoPollenIncrement);
        autoPollenIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt7(matchResult.getAutoInt7() + 1);
            populateControlsFromData();
        });

        autoMissedValue = findViewById(R.id.autoMissedValue);
        autoMissedDecrement = findViewById(R.id.autoMissedDecrement);
        autoMissedDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt10(Math.max(matchResult.getAutoInt10() - 1, 0));
            populateControlsFromData();
        });
        autoMissedIncrement = findViewById(R.id.autoMissedIncrement);
        autoMissedIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt10(matchResult.getAutoInt10() + 1);
            populateControlsFromData();
        });

        autoHiveTipValue = findViewById(R.id.autoHiveTipValue);
        autoHiveTipDecrement = findViewById(R.id.autoHiveTipDecrement);
        autoHiveTipDecrement.setOnClickListener(v -> {
            matchResult.setAutoInt8(Math.max(matchResult.getAutoInt8() - 1, 0));
            populateControlsFromData();
        });
        autoHiveTipIncrement = findViewById(R.id.autoHiveTipIncrement);
        autoHiveTipIncrement.setOnClickListener(v -> {
            matchResult.setAutoInt8(matchResult.getAutoInt8() + 1);
            populateControlsFromData();
        });

        autoLeave = findViewById(R.id.autoLeaveCheckbox);
        autoLeave.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setAutoFlag1(isChecked);
            populateControlsFromData();
        });

        autoPark = findViewById(R.id.autoParkCheckbox);
        autoPark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setAutoFlag2(isChecked);
            populateControlsFromData();
        });


        //TeleOp
        teleOpNectarValue = findViewById(R.id.teleOpNectarValue);
        teleOpNectarDecrement = findViewById(R.id.teleOpNectarDecrement);
        teleOpNectarDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt6(Math.max(matchResult.getTeleOpInt6() - 1, 0));
            populateControlsFromData();
        });
        teleOpNectarIncrement = findViewById(R.id.teleOpNectarIncrement);
        teleOpNectarIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt6(matchResult.getTeleOpInt6() + 1);
            populateControlsFromData();
        });

        teleOpPollenValue = findViewById(R.id.teleOpPollenValue);
        teleOpPollenDecrement = findViewById(R.id.teleOpPollenDecrement);
        teleOpPollenDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt7(Math.max(matchResult.getTeleOpInt7() - 1, 0));
            populateControlsFromData();
        });
        teleOpPollenIncrement = findViewById(R.id.teleOpPollenIncrement);
        teleOpPollenIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt7(matchResult.getTeleOpInt7() + 1);
            populateControlsFromData();
        });

        teleOpMissedValue = findViewById(R.id.teleOpMissedValue);
        teleOpMissedDecrement = findViewById(R.id.teleOpMissedDecrement);
        teleOpMissedDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt8(Math.max(matchResult.getTeleOpInt8() - 1, 0));
            populateControlsFromData();
        });
        teleOpMissedIncrement = findViewById(R.id.teleOpMissedIncrement);
        teleOpMissedIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt8(matchResult.getTeleOpInt8() + 1);
            populateControlsFromData();
        });

        teleOpHiveTipValue = findViewById(R.id.teleOpHiveTipValue);
        teleOpHiveTipDecrement = findViewById(R.id.teleOpHiveTipDecrement);
        teleOpHiveTipDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt9(Math.max(matchResult.getTeleOpInt9() - 1, 0));
            populateControlsFromData();
        });
        teleOpHiveTipIncrement = findViewById(R.id.teleOpHiveTipIncrement);
        teleOpHiveTipIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt10(matchResult.getTeleOpInt10() + 1);
            populateControlsFromData();
        });

        teleOpHiveRemainingValue = findViewById(R.id.teleOpHiveRemainingValue);
        teleOpHiveRemainingDecrement = findViewById(R.id.teleOpHiveRemainingDecrement);
        teleOpHiveRemainingDecrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt11(Math.max(matchResult.getTeleOpInt11() - 1, 0));
            populateControlsFromData();
        });
        teleOpHiveRemainingIncrement = findViewById(R.id.teleOpHiveRemainingIncrement);
        teleOpHiveRemainingIncrement.setOnClickListener(v -> {
            matchResult.setTeleOpInt11(matchResult.getTeleOpInt11() + 1);
            populateControlsFromData();
        });

        endOwnedValue = findViewById(R.id.endOwnedValue);
        endOwnedDecrement = findViewById(R.id.endOwnedDecrement);
        endOwnedDecrement.setOnClickListener(v -> {
            matchResult.setEndInt6(Math.max(matchResult.getEndInt6() - 1, 0));
            populateControlsFromData();
        });
        endOwnedIncrement = findViewById(R.id.endOwnedIncrement);
        endOwnedIncrement.setOnClickListener(v -> {
            matchResult.setEndInt6(matchResult.getEndInt6() + 1);
            populateControlsFromData();
        });

        endBottomValue = findViewById(R.id.endBottomValue);
        endBottomDecrement = findViewById(R.id.endBottomDecrement);
        endBottomDecrement.setOnClickListener(v -> {
            matchResult.setEndInt7(Math.max(matchResult.getEndInt7() - 1, 0));
            populateControlsFromData();
        });
        endBottomIncrement = findViewById(R.id.endBottomIncrement);
        endBottomIncrement.setOnClickListener(v -> {
            matchResult.setEndInt7(matchResult.getEndInt7() + 1);
            populateControlsFromData();
        });


        endGardenValue = findViewById(R.id.endGardenValue);
        endGardenDecrement = findViewById(R.id.endGardenDecrement);
        endGardenDecrement.setOnClickListener(v -> {
            matchResult.setEndInt8(Math.max(matchResult.getEndInt8() - 1, 0));
            populateControlsFromData();
        });
        endGardenIncrement = findViewById(R.id.endGardenIncrement);
        endGardenIncrement.setOnClickListener(v -> {
            matchResult.setEndInt8(matchResult.getEndInt8() + 1);
            populateControlsFromData();
        });

        endParkCheckBox = findViewById(R.id.endPark);
        endParkCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setEndFlag1(isChecked);
            populateControlsFromData();
        });

        submitButton = findViewById(R.id.matchSummarySaveButton);
        submitButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
            Log.i(TAG, "current match key is " + matchKey);
            //2324-FIM-HAQ-Q001-1
            try {
                String[] tokens = matchKey.split("_");
                //String matchNumberTokens = tokens[2];
                String matchNumberTokens = tokens[tokens.length - 1];

                //String[] matchTokens = matchNumberTokens.split("Q");
                Integer nextMatchNumber = Integer.valueOf(matchNumberTokens) + 1;
                MatchSelectionActivity.launch(MatchScoutingSummaryActivity.this, nextMatchNumber);
            } catch (Exception e) {
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
                matchResult.setAdditionalNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    volatile boolean populating = false;

    private void populateControlsFromData() {
        if (matchResult == null) return;
        if (populating == true) return;

        populating = true;

        autoNectarValue.setText(String.valueOf(matchResult.getAutoInt6()));
        autoPollenValue.setText(String.valueOf(matchResult.getAutoInt7()));
        autoMissedValue.setText(String.valueOf(matchResult.getAutoInt8()));
        autoHiveTipValue.setText(String.valueOf(matchResult.getAutoInt9()));
        autoLeave.setChecked(matchResult.getAutoFlag1());
        autoPark.setChecked(matchResult.getAutoFlag2());


        teleOpNectarValue.setText(String.valueOf(matchResult.getTeleOpInt6()));
        teleOpPollenValue.setText(String.valueOf(matchResult.getTeleOpInt7()));
        teleOpMissedValue.setText(String.valueOf(matchResult.getTeleOpInt8()));
        teleOpHiveTipValue.setText(String.valueOf(matchResult.getTeleOpInt9()));
        teleOpHiveRemainingValue.setText(String.valueOf(matchResult.getTeleOpInt10()));


        endParkCheckBox.setChecked(matchResult.getEndFlag1());
        endOwnedValue.setText( String.valueOf( matchResult.getEndInt6() ));
        endBottomValue.setText( String.valueOf( matchResult.getEndInt7() ));
        endGardenValue.setText( String.valueOf( matchResult.getEndInt8() ));

        //teleOpDefensesValue.setText( String.valueOf( matchResult.getDefenseCount() ));

        additionalNotesLayout.getEditText().setText(matchResult.getAdditionalNotes());

        populating = false;

    }
}
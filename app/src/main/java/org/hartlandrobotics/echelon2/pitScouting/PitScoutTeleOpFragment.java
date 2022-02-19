package org.hartlandrobotics.echelon2.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;

public class PitScoutTeleOpFragment extends Fragment {
    public static final String TAG = "PitScoutTeleOpFragment";


    RadioGroup doesShootGroup;
    TextInputLayout shootingAccuracyLayout;
    RadioGroup goalPreferenceGroup;
    RadioGroup defenseGroup;
    PitScout data;

    boolean areControlsSetup = false;

    public void setData( PitScout data) {
        this.data = data;
        populateControlsFromData();
    }

    public PitScoutTeleOpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_tele_op, container, false);

        setupControls(view);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "on Resume");
        populateControlsFromData();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "on Pause");
        populateDataFromControls();
    }

    private void setupControls(View view) {
        doesShootGroup = view.findViewById(R.id.doesRobotShoot);
        doesShootGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                boolean canShoot = checkedId == R.id.robotShootYes;
                data.setCanShoot(canShoot);
            }
        });

        shootingAccuracyLayout = view.findViewById(R.id.shootingAccuracy);
        goalPreferenceGroup = view.findViewById(R.id.goalPreference);
        defenseGroup = view.findViewById(R.id.robotDefense);

        areControlsSetup = true;
    }

    public void populateDataFromControls() {
        Log.i(TAG, "populate data from controls");
        boolean doesShoot = doesShootGroup.getCheckedRadioButtonId() == R.id.robotShootYes;
        data.setCanShoot(doesShoot);

        String shootingPercentageText = StringUtils.defaultIfBlank(shootingAccuracyLayout.getEditText().getEditableText().toString(), "0");
        data.setShootingAccuracy(Double.valueOf(shootingPercentageText));

        String preferredGoal = goalPreferenceGroup.getCheckedRadioButtonId() == R.id.goalPreferenceHigh ? "high" : "low";
        data.setPreferredGoal(preferredGoal);

        boolean canPlayDefense = defenseGroup.getCheckedRadioButtonId() == R.id.robotDefenseYes;
        data.setCanPlayDefense(canPlayDefense);
    }

    private void populateControlsFromData() {
        if (data == null) {
            Log.i(TAG, "no data to bind");
            return;
        }

        // check that controls have been established
        if(doesShootGroup == null) return;


        Log.i(TAG, "populate controls from data");
        boolean canShoot = data.getCanShoot();
        int canShootSelection = canShoot ? R.id.robotShootYes : R.id.robotShootNo;
        doesShootGroup.check(canShootSelection);

        String shootingPercentageText = String.valueOf( data.getShootingAccuracy());
        shootingAccuracyLayout.getEditText().setText(shootingPercentageText);

        String preferredGoalText = StringUtils.defaultIfBlank( data.getPreferredGoal(), "low");
        int preferredGoalSelection = preferredGoalText.equals("high") ? R.id.goalPreferenceHigh : R.id.goalPreferenceLow;
        goalPreferenceGroup.check(preferredGoalSelection);

        boolean canPlayDefense = data.getCanPlayDefense();
        int canPlayDefenseSelection = canPlayDefense ? R.id.robotDefenseYes : R.id.robotDefenseNo;
        defenseGroup.check(canPlayDefenseSelection);
    }
}
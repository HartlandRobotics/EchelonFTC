package org.hartlandrobotics.echelonFTC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.PitScout;

public class PitScoutTeleOpFragment extends Fragment {
    public static final String TAG = "PitScoutTeleOpFragment";

    TextInputLayout preferredRoleLayout;
    AutoCompleteTextView preferredRoleAutoComplete;
    String defaultPreferredRole;

    TextInputLayout preferredScoringLayout;
    AutoCompleteTextView preferredScoringAutoComplete;
    String defaultPreferredScoring;

    TextInputLayout teleOpPointsLayout;


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
        preferredRoleLayout = view.findViewById(R.id.preferredRole);
        preferredRoleAutoComplete = view.findViewById(R.id.preferredRoleAutoComplete);
        String[] roles = getResources().getStringArray(R.array.preferred_role);
        defaultPreferredRole = roles[0];
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(requireActivity(), R.layout.dropdown_item, roles);
        preferredRoleAutoComplete.setAdapter(roleAdapter);

        preferredScoringLayout = view.findViewById(R.id.preferredScoring);
        preferredScoringAutoComplete = view.findViewById(R.id.preferredScoringAutoComplete);
        String[] scoring = getResources().getStringArray(R.array.scoring);
        defaultPreferredScoring = scoring[0];
        ArrayAdapter<String> scoringAdapter = new ArrayAdapter<String>(requireActivity(), R.layout.dropdown_item, scoring);
        preferredScoringAutoComplete.setAdapter(scoringAdapter);

        teleOpPointsLayout = view.findViewById(R.id.teleOpPoints);




        areControlsSetup = true;
    }

    public void populateDataFromControls() {
        Log.i(TAG, "populate data from controls");
        String teleOpPreferredRoleString = StringUtils.defaultIfBlank(preferredRoleLayout.getEditText().getText().toString(), defaultPreferredRole.toString());
        data.setTeleOpPreferredRole(teleOpPreferredRoleString);

        String teleOpPreferredScoringString = StringUtils.defaultIfBlank(preferredScoringLayout.getEditText().getText().toString(), defaultPreferredScoring.toString());
        data.setTeleOpPreferredScoring(teleOpPreferredScoringString);

        String teleOpPointsString = StringUtils.defaultIfBlank(teleOpPointsLayout.getEditText().getText().toString(), "0");
        int teleOpPoints = Integer.parseInt(teleOpPointsString.toString());
        data.setAutoPoints(teleOpPoints);

    }

    private void populateControlsFromData() {
        if (data == null) {
            Log.i(TAG, "no data to bind");
            return;
        }

        // check that controls have been established
        if(doesShootGroup == null) return;


        Log.i(TAG, "populate controls from data");
        String preferredRole = StringUtils.defaultIfBlank(data.getTeleOpPreferredRole(), defaultPreferredRole);
        preferredRoleAutoComplete.setText(preferredRole, false);

        String preferredScoring = StringUtils.defaultIfBlank(data.getTeleOpPreferredScoring(), defaultPreferredScoring);
        preferredScoringAutoComplete.setText(preferredScoring, false);

        String teleOpPointsString = StringUtils.defaultIfBlank(String.valueOf(data.getTeleOpPoints()), "0");
        teleOpPointsLayout.getEditText().setText(teleOpPointsString);


    }
}
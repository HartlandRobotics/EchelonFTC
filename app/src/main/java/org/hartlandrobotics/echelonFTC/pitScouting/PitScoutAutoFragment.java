package org.hartlandrobotics.echelonFTC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.PitScout;


public class PitScoutAutoFragment extends Fragment {
    private static final String TAG = "PitScoutAutoFragment";
    LinearLayout hasAutoLayout;
    RadioGroup hasAutoGroup;

    TextInputLayout preferredLayout;
    AutoCompleteTextView preferredAutoComplete;
    String defaultPreferred;

    TextInputLayout autoPointsLayout;

    TextInputLayout prioritizedLayout;
    AutoCompleteTextView prioritizedAutoComplete;
    String defaultPrioritization;



    PitScout data;

    public PitScoutAutoFragment() {
        // Required empty public constructor
    }

    public void setData(PitScout data) {
        this.data = data;
        populateControlsFromData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pitscout_auto, container, false);

        setupControls(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume running");
        populateControlsFromData();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "OnPause running");
        super.onPause();
        populateDataFromControls();
    }

    private void setupControls(View view) {
        hasAutoLayout = view.findViewById(R.id.hasAutoLayout);

        hasAutoGroup = view.findViewById(R.id.hasAutoGroup);
        hasAutoGroup.setOnCheckedChangeListener((group, checkedId) -> {
            setVisibility();
        });

        preferredLayout = view.findViewById(R.id.preferred);
        preferredAutoComplete = view.findViewById(R.id.preferredAutoComplete);
        String[] startingPositions = getResources().getStringArray(R.array.starting_position_types);
        defaultPreferred = startingPositions[0];
        ArrayAdapter<String> startingPositionAdapter = new ArrayAdapter<String>(requireActivity(), R.layout.dropdown_item, startingPositions);
        preferredAutoComplete.setAdapter(startingPositionAdapter);

        autoPointsLayout = view.findViewById(R.id.autoPoints);

        prioritizedLayout = view.findViewById(R.id.prioritize);
        prioritizedAutoComplete = view.findViewById(R.id.prioritizeAutoComplete);
        String[] startingPrioritization = getResources().getStringArray(R.array.prioritization);
        defaultPrioritization = startingPrioritization[0];
        ArrayAdapter<String> startingPrioritizationAdapter = new ArrayAdapter<String>(requireActivity(), R.layout.dropdown_item, startingPrioritization);
        prioritizedAutoComplete.setAdapter(startingPrioritizationAdapter);
    }


    public void populateDataFromControls() {
        if( data == null ) return;
        if( hasAutoGroup == null ) return;

        boolean hasAuto = hasAutoGroup.getCheckedRadioButtonId() == R.id.hasAutoYes;
        data.setHasAutonomous(hasAuto);

        String autoPreferredString = StringUtils.defaultIfBlank(preferredLayout.getEditText().getText().toString(), defaultPreferred.toString());
        data.setAutoPreferred(autoPreferredString);

        String autoPointsString = StringUtils.defaultIfBlank(autoPointsLayout.getEditText().getText().toString(), "0");
        int autoPoints = Integer.parseInt(autoPointsString.toString());
        data.setAutoPoints(autoPoints);

        String autoPrioritizationString = StringUtils.defaultIfBlank(prioritizedLayout.getEditText().getText().toString(), defaultPrioritization.toString());
        data.setAutoPrioritized(autoPrioritizationString);
   }

    public void populateControlsFromData() {
        if (data == null) return;
        if( hasAutoGroup == null ) return;

        int hasAutoCheckedButtonId = data.getHasAutonomous() ? R.id.hasAutoYes : R.id.hasAutoNo;
        hasAutoGroup.check(hasAutoCheckedButtonId);

        String preferred = StringUtils.defaultIfBlank(data.getAutoPreferred(), defaultPreferred);
        preferredAutoComplete.setText(preferred, false);

        String autoPointsString = StringUtils.defaultIfBlank(String.valueOf(data.getAutoPoints()), "0");
        autoPointsLayout.getEditText().setText(autoPointsString);

        String prioritize = StringUtils.defaultIfBlank(data.getAutoPrioritized(), defaultPrioritization);
        prioritizedAutoComplete.setText(prioritize, false);

        setVisibility();
    }

    public void setVisibility() {
        if (hasAutoGroup.getCheckedRadioButtonId() == R.id.hasAutoYes) {
            hasAutoLayout.setVisibility(View.VISIBLE);
        } else {
            hasAutoLayout.setVisibility(View.GONE);
        }
    }
}
package org.hartlandrobotics.echelon2.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;

public class PitScoutTeamFragment extends Fragment {
    TextInputLayout seasonNumberLayout;
    TextInputLayout competitionsThisSeasonLayout;
    TextInputLayout driverSeasonNumberLayout;
    TextInputLayout operatorSeasonNumberLayout;
    TextInputLayout humanAccuracyLayout;
    TextInputLayout additionalNotesLayout;

    PitScout data;

    public void setData( PitScout data) { this.data = data; }

    public PitScoutTeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_team, container, false);

        setupControls(view);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        populateControlsFromData();
    }

    @Override
    public void onPause(){
        super.onPause();
        populateDataFromControls();
    }

    private void setupControls(View view){
        driverSeasonNumberLayout = view.findViewById(R.id.driverSeasonNumber);
        operatorSeasonNumberLayout = view.findViewById(R.id.operatorSeasonNumber);
        humanAccuracyLayout = view.findViewById(R.id.humanShotAccuracy);
        additionalNotesLayout = view.findViewById(R.id.additionalNotes);
    }

    public void populateControlsFromData(){
        if( data == null ) return;
        if( driverSeasonNumberLayout == null ) return;

        String driverExperienceText = String.valueOf(data.getDriverExperience());
        driverSeasonNumberLayout.getEditText().setText(driverExperienceText);

        String operatorExperienceText = String.valueOf(data.getOperatorExperience());
        operatorSeasonNumberLayout.getEditText().setText(operatorExperienceText);

        String humanAccuracyText = String.valueOf(data.getHumanPlayerAccuracy());
        humanAccuracyLayout.getEditText().setText(humanAccuracyText);

        String additionalNotes = StringUtils.defaultIfBlank(data.getExtraNotes(), StringUtils.EMPTY);
        additionalNotesLayout.getEditText().setText(additionalNotes);
    }

    public void populateDataFromControls(){
        if( data == null ) return;
        if( driverSeasonNumberLayout == null ) return;

        int driverExperience= Integer.valueOf( driverSeasonNumberLayout.getEditText().getText().toString());
        data.setDriverExperience(driverExperience);

        int operatorExperience = Integer.valueOf( operatorSeasonNumberLayout.getEditText().getText().toString());
        data.setOperatorExperience(operatorExperience);

        double humanAccuracy = Double.valueOf( humanAccuracyLayout.getEditText().getText().toString());
        data.setHumanPlayerAccuracy(humanAccuracy);

        String additionalNotes = additionalNotesLayout.getEditText().getText().toString();
        data.setExtraNotes(additionalNotes);
    }
}
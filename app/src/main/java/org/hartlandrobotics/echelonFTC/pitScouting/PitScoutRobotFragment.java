package org.hartlandrobotics.echelonFTC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.PitScout;

public class PitScoutRobotFragment extends Fragment {

    TextInputLayout driveTrainLayout;
    AutoCompleteTextView driveTrainAutoComplete;
    String defaultDriveTrain;

    TextInputLayout ingestLayout;
    AutoCompleteTextView ingestAutoComplete;
    String defaultIngest;

    TextInputLayout outgestLayout;
    AutoCompleteTextView outgestAutoComplete;
    String defaultOutgest;



    TextInputLayout additionalNotesLayout;

    PitScout data;

    public void setData( PitScout data) {
        this.data = data;
        populateControlsFromData();
    }

    public PitScoutRobotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_robot, container, false);

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
        driveTrainLayout = view.findViewById(R.id.driveTrain);
        driveTrainAutoComplete = view.findViewById(R.id.driveTrainAutoComplete);
        String[] driveTrains = getResources().getStringArray(R.array.drive_train);
        defaultDriveTrain = driveTrains[0];
        ArrayAdapter adapterDriveTrain = new ArrayAdapter(getActivity(), R.layout.dropdown_item, driveTrains);
        driveTrainAutoComplete.setAdapter(adapterDriveTrain);

        ingestLayout = view.findViewById(R.id.robotIngest);
        ingestAutoComplete = view.findViewById(R.id.robotIngestAutoComplete);
        String[] ingests = getResources().getStringArray(R.array.ingest_types);
        defaultIngest = ingests[0];
        ArrayAdapter adapterIngest = new ArrayAdapter(getActivity(), R.layout.dropdown_item, ingests);
        ingestAutoComplete.setAdapter(adapterIngest);

        outgestLayout = view.findViewById(R.id.robotOutgest);
        outgestAutoComplete = view.findViewById(R.id.robotOutgestAutoComplete);
        String[] outgests = getResources().getStringArray(R.array.outgest_types);
        defaultOutgest = outgests[0];
        ArrayAdapter adapterOutgest = new ArrayAdapter(getActivity(), R.layout.dropdown_item, outgests);
        outgestAutoComplete.setAdapter(adapterOutgest);

        additionalNotesLayout = view.findViewById(R.id.additionalNotes);

    }

    public void populateControlsFromData(){
        if( data == null ) return;
        if( driveTrainLayout == null ) return;

        String driveType = StringUtils.defaultIfBlank(data.getRobotDriveTrain(), defaultDriveTrain);
        driveTrainAutoComplete.setText(driveType, false);

        String ingestType = StringUtils.defaultIfBlank(data.getRobotIngest(), defaultIngest);
        ingestAutoComplete.setText(ingestType, false);

        String outgestType = StringUtils.defaultIfBlank(data.getRobotOutgest(), defaultOutgest);
        outgestAutoComplete.setText(outgestType, false);

        String additionalNotes = StringUtils.defaultIfBlank(data.getExtraNotes(), StringUtils.EMPTY);
        additionalNotesLayout.getEditText().setText(additionalNotes);
    }

    public void populateDataFromControls(){
        if( data == null ) return;
        if( driveTrainLayout == null ) return;

        String driveTrain = StringUtils.defaultIfBlank(driveTrainLayout.getEditText().getText().toString(), StringUtils.EMPTY);
        data.setRobotDriveTrain(driveTrain);

        String outgest = StringUtils.defaultIfBlank(outgestLayout.getEditText().getText().toString(), StringUtils.EMPTY);
        data.setRobotOutgest(outgest);

        String ingest = StringUtils.defaultIfBlank(ingestLayout.getEditText().getText().toString(), StringUtils.EMPTY);
        data.setRobotIngest(ingest);

        String additionalNotes = additionalNotesLayout.getEditText().getText().toString();
        data.setExtraNotes(additionalNotes);
    }
}
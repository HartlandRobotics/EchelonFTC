package org.hartlandrobotics.echelonFTC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class PitScoutEndGameFragment extends Fragment {

    TextInputLayout hangTimeLayout;
    TextInputLayout hangLayout;
    AutoCompleteTextView hangAutoComplete;
    String defaultHang;

    public PitScoutEndGameFragment() {
        // Required empty public constructor
    }

    PitScout data;

    public void setData( PitScout data) {
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
        View view =  inflater.inflate(R.layout.fragment_pitscout_end_game, container, false);

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
        hangTimeLayout = view.findViewById(R.id.hangTime);

        hangLayout = view.findViewById(R.id.hang);
        hangAutoComplete = view.findViewById(R.id.hangAutoComplete);
        String[] hangPositions = getResources().getStringArray(R.array.hang_park_types);
        defaultHang = hangPositions[0];
        ArrayAdapter<String> hangAdapter = new ArrayAdapter<String>(requireActivity(), R.layout.dropdown_item, hangPositions);
        hangAutoComplete.setAdapter(hangAdapter);

    }

    public void populateDataFromControls(){
        if( data == null ) return;
        if( hangTimeLayout == null ) return;

        String hangTimeText = StringUtils.defaultIfBlank(hangTimeLayout.getEditText().getText().toString(), "0");
        data.setEndHangTime(Integer.valueOf(hangTimeText));

        String hangString = StringUtils.defaultIfBlank(hangLayout.getEditText().getText().toString(), defaultHang.toString());
        data.setEndHang(hangString);

    }

    private void populateControlsFromData(){
        if( data == null ){
            return;
        }

        if( hangTimeLayout == null ) return;

        String hangTimeText = String.valueOf(data.getEndHangTime());
        hangTimeLayout.getEditText().setText(hangTimeText);

        String endHabg = StringUtils.defaultIfBlank(data.getAutoPreferred(), defaultHang);
        hangAutoComplete.setText(endHabg, false);

    }
}
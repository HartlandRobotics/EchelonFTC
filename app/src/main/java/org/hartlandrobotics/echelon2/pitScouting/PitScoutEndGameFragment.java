package org.hartlandrobotics.echelon2.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;

public class PitScoutEndGameFragment extends Fragment {

    TextInputLayout hangTimeLayout;
    RadioGroup hangPreferenceGroup;
    TextInputLayout robotSwingLayout;

    public PitScoutEndGameFragment() {
        // Required empty public constructor
    }

    PitScout data;

    public void setData( PitScout data) {
        this.data = data;
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
        hangPreferenceGroup = view.findViewById(R.id.hangPreference);
        robotSwingLayout = view.findViewById(R.id.robotSwing);
    }

    public void populateDataFromControls(){
        if( data == null ) return;
        if( hangTimeLayout == null ) return;

        String hangTimeText = StringUtils.defaultIfBlank(hangTimeLayout.getEditText().getText().toString(), "0");
        data.setHangTime(Integer.valueOf(hangTimeText));

        String hangPreference = "none";
        int hangPreferenceSelection = hangPreferenceGroup.getCheckedRadioButtonId();
        switch( hangPreferenceSelection ){
            case R.id.hangPreferenceLeft:
                hangPreference = "left";
                break;
            case R.id.hangPreferenceCenter:
                hangPreference = "center";
                break;
            case R.id.hangPreferenceRight:
                hangPreference = "right";
                break;
            default:
                hangPreference = "none";
        }
        data.setPreferredHangingSpot(hangPreference);

        String swingInchesText = StringUtils.defaultIfBlank(robotSwingLayout.getEditText().getText().toString(), "0");
        int swingInches = Integer.valueOf( swingInchesText );
        data.setSideSwing(swingInches);
    }

    private void populateControlsFromData(){
        if( data == null ){
            return;
        }

        if( hangTimeLayout == null ) return;

        String hangTimeText = String.valueOf(data.getHangTime());
        hangTimeLayout.getEditText().setText(hangTimeText);

        int hangPreferenceSelection = R.id.hangPreferenceNo;
        String hangingPreference = StringUtils.defaultIfBlank(data.getPreferredHangingSpot(), "none");
        switch( hangingPreference ){
            case "left":
                hangPreferenceSelection = R.id.hangPreferenceLeft;
                break;
            case "center":
                hangPreferenceSelection = R.id.hangPreferenceCenter;
                break;
            case "right":
                hangPreferenceSelection = R.id.hangPreferenceRight;
                break;
            default:
                hangPreferenceSelection = R.id.hangPreferenceNo;
        }
        hangPreferenceGroup.check(hangPreferenceSelection);

        String sideSwingSeconds = String.valueOf( data.getSideSwing());
        robotSwingLayout.getEditText().setText(sideSwingSeconds);
    }
}
package org.hartlandrobotics.echelon2.pitScouting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelon2.PitScoutActivity;
import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;


public class PitScoutAutoFragment extends Fragment {
    private static final String TAG = "PitScoutAutoFragment";
    RadioGroup hasAutoGroup;
    RadioGroup helpAutoGroup;
    TextInputLayout programmingLanguageLayout;
    AutoCompleteTextView programmingLanguageAutoComplete;
    LinearLayout missingAutoLayout;
    LinearLayout hasAutoLayout;
    TextInputLayout autoLanguage;
    TextInputLayout shotCount;
    TextInputLayout shootingPercentage;


    PitScout data;

    public void setData( PitScout data) { this.data = data; }
    public PitScout getData() { return data; }


    public PitScoutAutoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_pitscout_auto, container, false);

        setupControls(view);

        setupHasAuto( view );
        setupNoAuto( view );

        populateControlsFromData();

        setVisibility();

        return view;
    }

    private void setupControls(View view){
        hasAutoLayout = view.findViewById(R.id.hasAutoLayout);
        hasAutoGroup = view.findViewById(R.id.hasAutoGroup);
        shotCount = view.findViewById(R.id.autoBallCount);
        shootingPercentage = view.findViewById(R.id.shootingPercentage);

        missingAutoLayout = view.findViewById(R.id.missingAutoLayout);
        helpAutoGroup = view.findViewById(R.id.helpAutoGroup);
        programmingLanguageLayout = view.findViewById(R.id.autoLanguage);
        programmingLanguageAutoComplete = view.findViewById(R.id.autoLanguageAutoComplete);
        autoLanguage = view.findViewById(R.id.autoLanguage);
    }

    public void populateDataFromControls(){
        // has auto radio button
        boolean hasAuto = hasAutoGroup.getCheckedRadioButtonId() == R.id.hasAutoYes;
        data.setHasAutonomous(hasAuto);

        // balls shot in auto
        String ballCountString = StringUtils.defaultIfBlank( shotCount.getEditText().getText().toString(), "0");
        int ballCount = Integer.parseInt(ballCountString.toString());
        data.setBallsPickedOrShotInAuto(ballCount);

        // want help creating auto
        boolean wantsHelp = hasAutoGroup.getCheckedRadioButtonId() == R.id.helpAutoYes;
        data.setHelpCreatingAuto(wantsHelp);

        // scouted team programming Language
        // default to StringUtils.EMPTH
        data.setCodingLanguage(programmingLanguageAutoComplete.getEditableText().toString());
    }

   public void populateControlsFromData(){
        if( data == null ){
            Log.i(TAG,"no data to bind");
            return;
        }
        // has auto radio button
       if( hasAutoGroup != null ) {
           int hasAutoCheckedButtonId = data.getHasAutonomous() ? R.id.hasAutoYes : R.id.hasAutoNo;
           hasAutoGroup.check(hasAutoCheckedButtonId);
       }

        // balls shot in auto
       if( shotCount != null ) {
           String ballsShotInAuto = StringUtils.defaultIfBlank(String.valueOf(data.getBallsPickedOrShotInAuto()), "0");
           shotCount.getEditText().setText(ballsShotInAuto);
       }

        // wants help creating auto
       if( helpAutoGroup != null ){
        boolean wantsHelpWithAuto = data.getHelpCreatingAuto();
        int wantsHelpCheckedButtonId = wantsHelpWithAuto ? R.id.helpAutoYes : R.id.helpAutoNo;
        helpAutoGroup.check(wantsHelpCheckedButtonId);
       }
        // scouted team programming Language
       if( programmingLanguageAutoComplete != null) {
           if (!StringUtils.isBlank(data.getCodingLanguage())) {
               programmingLanguageAutoComplete.setText(data.getCodingLanguage(), false);
           }
       }
    }

    private void setupHasAuto(View view){
        hasAutoGroup.setOnCheckedChangeListener((group, checkedId) -> { setVisibility(); });
    }
    private void setupNoAuto(View view){
        String[] languages = getResources().getStringArray(R.array.programming_languages);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.dropdown_item, languages);
        programmingLanguageAutoComplete.setAdapter(adapter);

        helpAutoGroup.setOnCheckedChangeListener((group, checkedId) -> { setVisibility(); });
    }

    public void setVisibility(){
        if( hasAutoGroup.getCheckedRadioButtonId() == R.id.hasAutoYes ){
            missingAutoLayout.setVisibility(View.GONE);
            hasAutoLayout.setVisibility(View.VISIBLE);
            autoLanguage.setVisibility(View.GONE);
        } else {
            missingAutoLayout.setVisibility(View.VISIBLE);
            hasAutoLayout.setVisibility(View.GONE);
            autoLanguage.setVisibility(View.VISIBLE);
        }

        if( helpAutoGroup.getCheckedRadioButtonId() == R.id.helpAutoNo ){
            autoLanguage.setVisibility(View.GONE);
        } else {
            autoLanguage.setVisibility(View.VISIBLE);
        }
    }
}
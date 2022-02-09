package org.hartlandrobotics.echelon2.pitScouting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
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

import org.hartlandrobotics.echelon2.PitScoutActivity;
import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;


public class PitScoutAutoFragment extends Fragment {
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

        setupHasAuto( view );
        setupNoAuto( view );

        // some comment for a commit

        setVisibility();

        return view;
    }

    private void setupHasAuto(View view){
        hasAutoLayout = view.findViewById(R.id.hasAutoLayout);
        hasAutoGroup = view.findViewById(R.id.hasAutoGroup);
        shotCount = view.findViewById(R.id.autoBallCount);
        shootingPercentage = view.findViewById(R.id.shootingPercentage);

        hasAutoGroup.setOnCheckedChangeListener((group, checkedId) -> {
            setVisibility();

            boolean  hasAuto = checkedId == R.id.hasAutoYes;
            data.setHasAutonomous(hasAuto);
        });

        shotCount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int ballCount = Integer.parseInt(s.toString());
                data.setBallsPickedOrShotInAuto(ballCount);
            }

            @Override public void afterTextChanged(Editable s) { }
        });

        shootingPercentage.getEditText().addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int shootingPercentage = Integer.parseInt(s.toString());
                data.setPercentAutoShots(shootingPercentage);
            }

            @Override public void afterTextChanged(Editable s) { }
        });

    }
    private void setupNoAuto(View view){
        missingAutoLayout = view.findViewById(R.id.missingAutoLayout);
        helpAutoGroup = view.findViewById(R.id.helpAutoGroup);
        programmingLanguageLayout = view.findViewById(R.id.autoLanguage);
        programmingLanguageAutoComplete = view.findViewById(R.id.autoLanguageAutoComplete);
        autoLanguage = view.findViewById(R.id.autoLanguage);

        helpAutoGroup.setOnCheckedChangeListener((group, checkedId) -> {
            setVisibility();

            boolean wantsHelp = checkedId == R.id.helpAutoYes;
            data.setHelpCreatingAuto(wantsHelp);
        });

        String[] languages = getResources().getStringArray(R.array.programming_languages);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.dropdown_item, languages);
        programmingLanguageAutoComplete.setAdapter(adapter);

        programmingLanguageAutoComplete.addTextChangedListener(
                new TextWatcher() {
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        data.setCodingLanguage(s.toString());
                    }

                    @Override public void afterTextChanged(Editable s) { }
                }
        );
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
package org.hartlandrobotics.echelon2.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PitScoutAutoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PitScoutAutoFragment extends Fragment {
    RadioGroup hasAutoGroup;
    RadioGroup helpAutoGroup;
    TextInputLayout programmingLanguageLayout;
    AutoCompleteTextView programmingLanguageAutoComplete;
    LinearLayout missingAutoLayout;
    LinearLayout hasAutoLayout;
    TextInputLayout autoLanguage;

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

        setVisibility();

        return view;
    }

    private void setupHasAuto(View view){
        hasAutoLayout = view.findViewById(R.id.hasAutoLayout);
        hasAutoGroup = view.findViewById(R.id.hasAutoGroup);
        hasAutoGroup.setOnCheckedChangeListener((group, checkedId) -> {
            setVisibility();
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
        });

        String[] languages = getResources().getStringArray(R.array.programming_languages);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.dropdown_item, languages);
        programmingLanguageAutoComplete.setAdapter(adapter);
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
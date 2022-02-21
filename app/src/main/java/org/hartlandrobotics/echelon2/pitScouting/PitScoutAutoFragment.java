package org.hartlandrobotics.echelon2.pitScouting;

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
import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;


public class PitScoutAutoFragment extends Fragment {
    private static final String TAG = "PitScoutAutoFragment";

    RadioGroup hasAutoGroup;
    RadioGroup helpAutoGroup;
    TextInputLayout programmingLanguageLayout;
    AutoCompleteTextView programmingLanguageAutoComplete;
    String defaultProgrammingLanguage;
    LinearLayout missingAutoLayout;
    LinearLayout hasAutoLayout;
    TextInputLayout autoLanguage;
    TextInputLayout shotCount;
    TextInputLayout shootingPercentage;


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

        shotCount = view.findViewById(R.id.autoBallCount);

        shootingPercentage = view.findViewById(R.id.shootingPercentage);

        missingAutoLayout = view.findViewById(R.id.missingAutoLayout);

        helpAutoGroup = view.findViewById(R.id.helpAutoGroup);
        helpAutoGroup.setOnCheckedChangeListener((group, checkedId) -> {
            setVisibility();
        });

        autoLanguage = view.findViewById(R.id.autoLanguage);

        programmingLanguageLayout = view.findViewById(R.id.autoLanguage);
        programmingLanguageAutoComplete = view.findViewById(R.id.autoLanguageAutoComplete);
        String[] languages = getResources().getStringArray(R.array.programming_languages);
        defaultProgrammingLanguage = languages[0];
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.dropdown_item, languages);
        programmingLanguageAutoComplete.setAdapter(adapter);

    }


    public void populateDataFromControls() {

        if( data == null ) return;
        if( hasAutoGroup == null ) return;

        boolean hasAuto = hasAutoGroup.getCheckedRadioButtonId() == R.id.hasAutoYes;
        data.setHasAutonomous(hasAuto);

        String ballCountString = StringUtils.defaultIfBlank(shotCount.getEditText().getText().toString(), "0");
        int ballCount = Integer.parseInt(ballCountString.toString());
        data.setBallsPickedOrShotInAuto(ballCount);

        double shootingPercentageText = Double.valueOf(StringUtils.defaultIfBlank(shootingPercentage.getEditText().getText().toString(), "0"));
        data.setPercentAutoShots(shootingPercentageText);

        boolean wantsHelp = hasAutoGroup.getCheckedRadioButtonId() == R.id.helpAutoYes;
        data.setHelpCreatingAuto(wantsHelp);

        String codingLanguage = StringUtils.defaultIfBlank(programmingLanguageAutoComplete.getEditableText().toString(), StringUtils.EMPTY);
        data.setCodingLanguage(codingLanguage);
    }

    public void populateControlsFromData() {
        if (data == null) {
            return;
        }

        if( hasAutoGroup == null ) return;
        Log.i(TAG, "populating controls from data");

        int hasAutoCheckedButtonId = data.getHasAutonomous() ? R.id.hasAutoYes : R.id.hasAutoNo;
        hasAutoGroup.check(hasAutoCheckedButtonId);

        double shootingPercentageValue = data.getPercentAutoShots();
        shootingPercentage.getEditText().setText( String.valueOf(shootingPercentageValue));

        String ballsShotInAuto = StringUtils.defaultIfBlank(String.valueOf(data.getBallsPickedOrShotInAuto()), "0");
        shotCount.getEditText().setText(ballsShotInAuto);

        boolean wantsHelpWithAuto = data.getHelpCreatingAuto();
        int wantsHelpCheckedButtonId = wantsHelpWithAuto ? R.id.helpAutoYes : R.id.helpAutoNo;
        helpAutoGroup.check(wantsHelpCheckedButtonId);

        String programmingLanguage = StringUtils.defaultIfBlank(data.getCodingLanguage(), defaultProgrammingLanguage);
        programmingLanguageAutoComplete.setText(programmingLanguage, false);

        setVisibility();
    }

    public void setVisibility() {
        if (hasAutoGroup.getCheckedRadioButtonId() == R.id.hasAutoYes) {
            missingAutoLayout.setVisibility(View.GONE);
            hasAutoLayout.setVisibility(View.VISIBLE);
            autoLanguage.setVisibility(View.GONE);
        } else {
            missingAutoLayout.setVisibility(View.VISIBLE);
            hasAutoLayout.setVisibility(View.GONE);
            autoLanguage.setVisibility(View.VISIBLE);
        }

        if (helpAutoGroup.getCheckedRadioButtonId() == R.id.helpAutoNo) {
            autoLanguage.setVisibility(View.GONE);
        } else {
            autoLanguage.setVisibility(View.VISIBLE);
        }
    }
}
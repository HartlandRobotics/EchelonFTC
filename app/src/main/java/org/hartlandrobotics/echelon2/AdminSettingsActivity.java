package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsViewModel;

public class AdminSettingsActivity extends AppCompatActivity {
    private static final String LOG_TAG = AdminSettingsActivity.class.getSimpleName();

    private TextInputLayout blueAllianceText;
    private TextInputLayout scoutingSeasonText;
    private TextInputLayout errorText;
    private AutoCompleteTextView scoutingSeasonsAutoComplete;

    public static void launch(Context context){
        Intent intent = new Intent( context, AdminSettingsActivity.class );
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        errorText = this.findViewById(R.id.errorText);
        scoutingSeasonsAutoComplete = findViewById(R.id.scoutingSeasonDropDown);

        AdminSettingsViewModel viewModel = AdminSettingsProvider.getAdminSettings(getApplicationContext());
        if( viewModel == null ){
            Log.e(LOG_TAG, "Could not load admin settings");
            showError("Could not load admin settings");
            return;
        }

        setupScoutingSeasonDropDown();
        initializeBlueAllianceKey(viewModel);
        initializeScoutingSeason(viewModel);

    }

    public void setupScoutingSeasonDropDown(){
        String[] scoutingSeasons = getResources().getStringArray(R.array.scouting_years);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.season_dropdown_item, scoutingSeasons);
        scoutingSeasonsAutoComplete.setAdapter(adapter);
    }
    public void initializeBlueAllianceKey(AdminSettingsViewModel vm){
        blueAllianceText = this.findViewById(R.id.blueAllianceApiText);
        setDisplayText( blueAllianceText, vm.getBlueAllianceApiKey() );
        if( !vm.isBlueAllianceApikeySynced() ){
            setOutOfSync(blueAllianceText, vm.getFileSettings().getBlueAllianceApiKey());
        }
    }

    public void initializeScoutingSeason(AdminSettingsViewModel vm){
        scoutingSeasonText = this.findViewById(R.id.scoutingSeasonText);
        setDisplayText( scoutingSeasonText, vm.getScoutingSeason() );
        if( !vm.isScoutingSeasonSynced() ){
            setOutOfSync(scoutingSeasonText, vm.getFileSettings().getScoutingSeason());
        }
    }


    private void setDisplayText(TextInputLayout layout, String displayText){
        layout.getEditText().setText(displayText);
    }

    private void setOutOfSync(TextInputLayout layout, String fileValue ){
        layout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        layout.setEndIconDrawable(R.drawable.ic_menu_refresh);
        layout.setHelperText(fileValue);
    }

    private void showError( String errorMessage ){
        errorText.setErrorEnabled(true);
        errorText.setError(errorMessage);
        errorText.getEditText().setText("Admin Settings not available");
    }
}
package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsViewModel;

import java.util.HashMap;

public class AdminSettingsActivity extends EchelonActivity {
    private static final String LOG_TAG = AdminSettingsActivity.class.getSimpleName();

    private MaterialButtonToggleGroup deviceRoleGroup;
    private HashMap<Integer, String> buttonRoleById;
    private HashMap<String, Integer> buttonRoleByText;
    private TextInputLayout blueAllianceText;
    private TextInputLayout scoutingSeasonText;
    private TextInputLayout teamNumText;
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

        setupToolbar("Admin Settings");


        teamNumText = this.findViewById(R.id.teamNumText);
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
        initializeTeamNumText(viewModel);
        initializeDeviceRole(viewModel);

    }

    public void setupScoutingSeasonDropDown(){
        String[] scoutingSeasons = getResources().getStringArray(R.array.scouting_years);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item_season_dropdown, scoutingSeasons);
        scoutingSeasonsAutoComplete.setAdapter(adapter);
    }
    public void initializeBlueAllianceKey(AdminSettingsViewModel vm){
        blueAllianceText = this.findViewById(R.id.blueAllianceApiText);
        setDisplayText( blueAllianceText, vm.getBlueAllianceApiKey() );
        if( !vm.isBlueAllianceApikeySynced() ){
            setOutOfSync(blueAllianceText, vm.getFileSettings().getBlueAllianceApiKey());
        }
    }
    public void initializeTeamNumText(AdminSettingsViewModel vm){
        setDisplayText(teamNumText, vm.getTeamNumber());
    }

    public void initializeDeviceRole(AdminSettingsViewModel vm){
        buttonRoleByText = new HashMap<>();
        buttonRoleByText.put( "red1", R.id.red1);
        buttonRoleByText.put("red2", R.id.red2);
        buttonRoleByText.put("red3", R.id.red3);
        buttonRoleByText.put("blue1", R.id.blue1);
        buttonRoleByText.put("blue2", R.id.blue2);
        buttonRoleByText.put("blue3", R.id.blue3);

        buttonRoleById = new HashMap<>();
        buttonRoleById.put( R.id.red1, "red1");
        buttonRoleById.put(R.id.red2, "red2");
        buttonRoleById.put(R.id.red3, "red3");
        buttonRoleById.put(R.id.blue1, "blue1");
        buttonRoleById.put(R.id.blue2, "blue2");
        buttonRoleById.put(R.id.blue3, "blue3");

        deviceRoleGroup = findViewById(R.id.deviceRoleSelection);
        deviceRoleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    String currentRole = buttonRoleById.get(checkedId);
                    vm.setDeviceRole(AdminSettingsActivity.this, currentRole);
                }

            }
        });

        int currentButtonId = buttonRoleByText.get(StringUtils.defaultIfBlank(vm.getDeviceRole(),"red1"));
        MaterialButton currentButton = deviceRoleGroup.findViewById(currentButtonId);
        currentButton.toggle();

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
package org.hartlandrobotics.echelonFTC;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsViewModel;

import java.util.HashMap;

public class AdminSettingsActivity extends EchelonActivity {
    private static final String LOG_TAG = AdminSettingsActivity.class.getSimpleName();

    private MaterialButtonToggleGroup deviceRoleGroup;
    private HashMap<Integer, String> buttonRoleById;
    private HashMap<String, Integer> buttonRoleByText;

    private TextInputLayout blueAllianceText;
    private MaterialButton apiKeySaveButton;
    private MaterialButton apiKeyRestoreButton;

    private TextInputLayout teamNumText;
    private MaterialButton teamNumSaveButton;
    private MaterialButton teamNumRestoreButton;

    private TextInputLayout errorText;



    public static void launch(Context context){
        Intent intent = new Intent( context, AdminSettingsActivity.class );
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        setupToolbar("Admin Settings");


        errorText = this.findViewById(R.id.errorText);

        AdminSettingsViewModel viewModel = AdminSettingsProvider.getAdminSettings(getApplicationContext());
        if( viewModel == null ){
            Log.e(LOG_TAG, "Could not load admin settings");
            showError("Could not load admin settings");
            return;
        }

        initializeDeviceRole(viewModel);

        initializeBlueAllianceKey(viewModel);
        initializeTeamNumText(viewModel);

    }

    public void initializeBlueAllianceKey(AdminSettingsViewModel vm){
        blueAllianceText = this.findViewById(R.id.blueAllianceApiText);
        setDisplayText( blueAllianceText, vm.getOrangeAllianceApiKey(), vm.getFileSettings().getOrangeAllianceApiKey() );

        apiKeySaveButton = this.findViewById(R.id.apiKeySaveButton);
        apiKeySaveButton.setOnClickListener(v -> {
            String apiKey = blueAllianceText.getEditText().getText().toString();
            vm.setOrangeAllianceApiKey(getApplicationContext(), apiKey);
        });

        apiKeyRestoreButton = this.findViewById(R.id.apiKeyRestoreButton);
        apiKeyRestoreButton.setOnClickListener(v -> {
            String fileApiKey = vm.getFileSettings().getOrangeAllianceApiKey();
            vm.setOrangeAllianceApiKey(getApplicationContext(), fileApiKey);
            setDisplayText( blueAllianceText, fileApiKey, fileApiKey);
        });
    }

    public void initializeTeamNumText(AdminSettingsViewModel vm){
        teamNumText = this.findViewById(R.id.teamNumText);
        setDisplayText(teamNumText, vm.getTeamNumber(), vm.getFileSettings().getTeamNumber());

        teamNumSaveButton = this.findViewById(R.id.teamNumberSaveButton);
        teamNumSaveButton.setOnClickListener(v -> {
            String teamNumber = teamNumText.getEditText().getText().toString();
            vm.setTeamNumber(getApplicationContext(), teamNumber);
        });

        teamNumRestoreButton = this.findViewById(R.id.teamNumberRestoreButton);
        teamNumRestoreButton.setOnClickListener(v -> {
            String fileTeamNumber = vm.getFileSettings().getTeamNumber();
            vm.setTeamNumber(getApplicationContext(), fileTeamNumber );
            setDisplayText(teamNumText, fileTeamNumber, fileTeamNumber);
        });
    }

    public void initializeDeviceRole(AdminSettingsViewModel vm){
        buttonRoleByText = new HashMap<>();
        buttonRoleByText.put("red1", R.id.red1);
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

    private void setDisplayText(TextInputLayout layout, String displayText, String fileText){
        layout.getEditText().setText(displayText);
        layout.setHelperText( fileText );
    }

    private void showError( String errorMessage ){
        errorText.setErrorEnabled(true);
        errorText.setError(errorMessage);
        errorText.getEditText().setText("Admin Settings not available");
    }
}
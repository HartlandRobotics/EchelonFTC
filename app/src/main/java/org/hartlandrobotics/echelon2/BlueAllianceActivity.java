package org.hartlandrobotics.echelon2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.hartlandrobotics.echelon2.TBA.Api;
import org.hartlandrobotics.echelon2.TBA.ApiInterface;
import org.hartlandrobotics.echelon2.TBA.models.SyncDistrict;
import org.hartlandrobotics.echelon2.TBA.models.SyncStatus;
import org.hartlandrobotics.echelon2.TBA.models.SyncTeam;
import org.hartlandrobotics.echelon2.database.repositories.DistrictRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlueAllianceActivity extends AppCompatActivity {

    private Button statusButton;
    private TextView errorTextDisplay;

    private Button districtButton;
    private TextView errorTextDistrict;

    private DistrictRepo districtRepo;

    private Button teamButton;
    private TextView errorTextTeam;

    public static void launch(Context context){
        Intent intent = new Intent(context, BlueAllianceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_alliance);

        districtRepo = new DistrictRepo(this.getApplication());


        errorTextDistrict = findViewById(R.id.errorTextDistricts);
        errorTextDisplay = findViewById(R.id.errorText);
        errorTextTeam = findViewById(R.id.errorTextTeams);


        statusButton = findViewById(R.id.syncButton);
        statusButton.setOnClickListener((view) -> {
            ApiInterface api = Api.getApiClient(getApplicationContext());

            try {
                Call<SyncStatus> call = api.getStatus();
                call.enqueue(new Callback<SyncStatus>() {
                    @Override
                    public void onResponse(Call<SyncStatus> call, Response<SyncStatus> response) {
                        try {
                            if (!response.isSuccessful()) {
                                errorTextDisplay.setText("Can't reach Blue Alliance");

                            } else {
                                SyncStatus status = response.body();
                                if(status.isDatafeedDown()){
                                    errorTextDisplay.setText("Blue Alliance Offline");
                                }
                                else {
                                    errorTextDisplay.setText("Blue Alliance Online");
                                }
                            }
                        }
                        catch(Exception e){
                        }
                    }

                    @Override
                    public void onFailure(Call<SyncStatus> call, Throwable t) {
                        errorTextDisplay.setText("Invalid call to Blue Alliance");
                    }
                });
            }
            catch(Exception ex){
                errorTextDisplay.setText("Error: " + ex.getMessage());
            }
        });


        districtButton = findViewById(R.id.syncDistricts);
        districtButton.setOnClickListener((view) -> {
            ApiInterface newApi = Api.getApiClient(getApplicationContext());

            try{
                Call<List<SyncDistrict>> newCall = newApi.getDistrictsByYear(2022);
                newCall.enqueue(new Callback<List<SyncDistrict>>() {
                    @Override
                    public void onResponse(Call<List<SyncDistrict>> call, Response<List<SyncDistrict>> response) {
                        try{
                            if(!response.isSuccessful()){
                                errorTextDistrict.setText("Couldn't pull districts");
                            }
                            else{
                                List<SyncDistrict> districts = response.body();
                                districts.stream()
                                        .map(district -> district.toDistrict())
                                        .forEach(district -> districtRepo.upsert(district));
                                errorTextDistrict.setText("Got districts " + districts.size());
                            }
                        }
                        catch(Exception e){
                            errorTextDistrict.setText("Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SyncDistrict>> call, Throwable t) {
                        errorTextDistrict.setText("Couldn't pull districts");
                    }
                });
            }
            catch(Exception e){
                errorTextDistrict.setText("Error second catch " + e.getMessage());
            }
        });

        teamButton = findViewById(R.id.syncTeams);
        teamButton.setOnClickListener((view) -> {
            ApiInterface api = Api.getApiClient(getApplicationContext());

            try{
                Call<List<SyncTeam>> call = api.getTeamsByEvent("2022milan");
                call.enqueue(new Callback<List<SyncTeam>>() {
                    @Override
                    public void onResponse(Call<List<SyncTeam>> call, Response<List<SyncTeam>> response) {
                        try{
                            if(!response.isSuccessful()){
                                errorTextTeam.setText("Couldn't pull teams");
                            }
                            else{
                                List<SyncTeam> teams = response.body();
                                errorTextTeam.setText("Got teams " + teams.size());
                            }
                        }
                        catch(Exception e){
                            errorTextTeam.setText("Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SyncTeam>> call, Throwable t) {
                        errorTextTeam.setText("Couldn't pull teams");
                    }
                });
            }
            catch(Exception e){
                errorTextTeam.setText("Error second catch " + e.getMessage());
            }
        });

    }
}

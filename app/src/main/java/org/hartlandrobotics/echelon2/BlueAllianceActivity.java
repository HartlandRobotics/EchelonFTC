package org.hartlandrobotics.echelon2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.hartlandrobotics.echelon2.TBA.Api;
import org.hartlandrobotics.echelon2.TBA.ApiInterface;
import org.hartlandrobotics.echelon2.TBA.models.SyncStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlueAllianceActivity extends AppCompatActivity {

    private Button statusButton;
    private TextView errorTextDisplay;

    public static void launch(Context context){
        Intent intent = new Intent(context, BlueAllianceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_alliance);

        errorTextDisplay = findViewById(R.id.errorText);

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

    }
}

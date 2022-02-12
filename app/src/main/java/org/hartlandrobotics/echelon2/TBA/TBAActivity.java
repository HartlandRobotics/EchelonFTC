package org.hartlandrobotics.echelon2.TBA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelon2.R;

import org.hartlandrobotics.echelon2.TBA.fragments.DistrictsFragment;
import org.hartlandrobotics.echelon2.TBA.models.SyncStatus;
import org.hartlandrobotics.echelon2.database.entities.District;
import org.hartlandrobotics.echelon2.database.repositories.DistrictRepo;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TBAActivity extends AppCompatActivity {
    BlueAllianceStatus tbaStatus;

    TabLayout tabLayout;
    ViewPager2 viewPager;
    TBAPagerAdapter tbaPagerAdapter;

    TextInputLayout onlineStatusLayout;
    TextInputLayout districtStatusLayout;
    TextInputLayout eventStatusLayout;

    public static void launch(Context context){
        Intent intent = new Intent(context, TBAActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tba);

        tbaStatus = new BlueAllianceStatus(getApplicationContext());

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        tbaPagerAdapter = new TBAPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(tbaPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tbaPagerAdapter.getTabTitle(position))).attach();

        districtStatusLayout = findViewById(R.id.districtStatusLayout);
        setDistrictStatus( tbaStatus.getDistrictKey() );

        eventStatusLayout = findViewById(R.id.eventStatusLayout);
        setEventStatus(tbaStatus.getEventKey());

        onlineStatusLayout = findViewById(R.id.onlineStatusLayout);
        onlineStatusLayout.getEditText().setText(StringUtils.EMPTY);
        checkOnlineStatus();
    }

    private void checkOnlineStatus(){
        ApiInterface newApi = Api.getApiClient(getApplication());
        try {
            Call<SyncStatus> statusCall = newApi.getStatus();
            statusCall.enqueue(new Callback<SyncStatus>() {
                @Override
                public void onResponse(Call<SyncStatus> call, Response<SyncStatus> response) {
                    setOnlineStatus(response.isSuccessful());
                }

                @Override
                public void onFailure(Call<SyncStatus> call, Throwable t) {
                    setOnlineStatus(false);
                }
            });
        }
        catch( Exception e){
            setOnlineStatus(false);
        }
    }

    private void setOnlineStatus( boolean isOnline ){
        onlineStatusLayout.getEditText().setText( String.valueOf(isOnline) );
        tbaStatus.setOnline(isOnline);
    }

    private void setDistrictStatus( String districtKey ){
        districtStatusLayout.getEditText().setText(districtKey);
    }

    private void setEventStatus(String eventKey){
        eventStatusLayout.getEditText().setText(eventKey);
    }

    public void setDistrictKey( String districtKey ){
        setDistrictStatus( districtKey );
        tbaStatus.setDistrictKey(districtKey);
    }

    public void setEventKey(String eventKey){
        setEventStatus(eventKey);
        tbaStatus.setEventKey(eventKey);
    }
}

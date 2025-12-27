/*
package org.hartlandrobotics.echelonFTC.orangeAlliance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.EchelonActivity;
import org.hartlandrobotics.echelonFTC.R;

import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncStatus;
//import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;
import org.hartlandrobotics.echelonFTC.ftapi.status.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrangeAllianceActivity extends EchelonActivity {
    ApiStatus tbaStatus;

    TabLayout tabLayout;
    ViewPager2 viewPager;
    OrangeAlliancePagerAdapter orangeAlliancePagerAdapter;

    TextInputLayout seasonStatusLayout;
    TextInputLayout onlineStatusLayout;
    TextInputLayout districtStatusLayout;
    TextInputLayout eventStatusLayout;
    TextInputLayout matchStatusLayout;

    public static void launch(Context context){
        Intent intent = new Intent(context, OrangeAllianceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tba);

        setupToolbar("Orange Alliance");

        tbaStatus = new OrangeAllianceStatus(getApplicationContext());

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        orangeAlliancePagerAdapter = new OrangeAlliancePagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(orangeAlliancePagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(orangeAlliancePagerAdapter.getTabTitle(position))).attach();

        seasonStatusLayout = findViewById(R.id.seasonStatusLayout);
        setSeasonStatus( tbaStatus.getSeason() );

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

    private void setSeasonStatus( String season ){
        seasonStatusLayout.getEditText().setText(season);
    }
    private void setDistrictStatus( String districtKey ){
        districtStatusLayout.getEditText().setText(districtKey);
    }

    private void setEventStatus(String eventKey){
        eventStatusLayout.getEditText().setText(eventKey);
    }

    private void setMatchStatus(String matchKey){
        matchStatusLayout.getEditText().setText(matchKey);
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
*/

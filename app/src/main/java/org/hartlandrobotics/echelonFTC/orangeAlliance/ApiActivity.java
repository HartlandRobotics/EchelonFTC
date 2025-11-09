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
import org.hartlandrobotics.echelonFTC.status.ApiStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiActivity extends EchelonActivity {
    ApiStatus apiStatus;

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ApiPagerAdapter apiPagerAdapter;

    TextInputLayout seasonStatusLayout;
    TextInputLayout onlineStatusLayout;
    TextInputLayout regionStatusLayout;
    TextInputLayout eventStatusLayout;
    TextInputLayout matchStatusLayout;

    public static void launch(Context context){
        Intent intent = new Intent(context, ApiActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tba);

        setupToolbar("API Events");

        apiStatus = new ApiStatus(getApplicationContext());

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        apiPagerAdapter = new ApiPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(apiPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(apiPagerAdapter.getTabTitle(position))).attach();

        seasonStatusLayout = findViewById(R.id.seasonStatusLayout);
        setSeasonStatus( apiStatus.getSeason() );

        regionStatusLayout = findViewById(R.id.regionStatusLayout);
        setRegionStatus( apiStatus.getRegionCode() );

        eventStatusLayout = findViewById(R.id.eventStatusLayout);
        setEventStatus(apiStatus.getEventKey());

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
        assert onlineStatusLayout.getEditText() != null;
        onlineStatusLayout.getEditText().setText( String.valueOf(isOnline) );
        apiStatus.setOnline(isOnline);
    }

    private void setSeasonStatus( String season ){
        assert seasonStatusLayout.getEditText() != null;
        seasonStatusLayout.getEditText().setText(season);
    }
    private void setRegionStatus(String regionCode ){
        assert regionStatusLayout.getEditText() != null;
        regionStatusLayout.getEditText().setText(regionCode);
    }

    private void setEventStatus(String eventKey){
        assert eventStatusLayout.getEditText() != null;
        eventStatusLayout.getEditText().setText(eventKey);
    }

    private void setMatchStatus(String matchKey){
        assert matchStatusLayout.getEditText() != null;
        matchStatusLayout.getEditText().setText(matchKey);
    }

    public void setRegionCode( String districtKey ){
        setRegionStatus( districtKey );
        apiStatus.setRegionCode(districtKey);
    }

    public void setEventKey(String eventKey){
        setEventStatus(eventKey);
        apiStatus.setEventKey(eventKey);
    }
}

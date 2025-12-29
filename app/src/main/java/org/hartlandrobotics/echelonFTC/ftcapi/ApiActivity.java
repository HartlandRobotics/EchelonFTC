package org.hartlandrobotics.echelonFTC.ftcapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.EchelonActivity;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;
import org.hartlandrobotics.echelonFTC.ftcapi.models.ApiIndex;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiActivity extends EchelonActivity {
    final String TAG = "ApiActivity";
    FtcApiStatus apiStatus;

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ApiPagerAdapter ftcApiPagerAdapter;

    TextInputLayout onlineStatusLayout;
    TextInputLayout regionStatusLayout;
    TextInputLayout eventStatusLayout;

    public static void launch(Context context){
        Intent intent = new Intent(context, ApiActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftcapi);

        setupToolbar("FTC Events API");

        apiStatus = new FtcApiStatus( getApplicationContext());

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        ftcApiPagerAdapter = new ApiPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(ftcApiPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(ftcApiPagerAdapter.getTabTitle(position))).attach();
        onlineStatusLayout = findViewById(R.id.onlineStatusLayout);
        onlineStatusLayout.getEditText().setText(StringUtils.EMPTY);
        checkOnlineStatus();

//
//        seasonStatusLayout = findViewById(R.id.seasonStatusLayout);
//        setSeasonStatus( tbaStatus.getSeason() );
//
        regionStatusLayout = findViewById(R.id.regionStatusLayout);
        setRegionStatus( apiStatus.getRegionKey() );

        eventStatusLayout = findViewById(R.id.eventStatusLayout);
        setEventStatus( apiStatus.getEventCode() );


    }

    private void checkOnlineStatus(){
        ApiInterface newApi = Api.getApiClient(getApplicationContext());
        try {
            Call<ApiIndex> statusCall = newApi.getStatus();
            statusCall.enqueue(new Callback<ApiIndex>() {
                @Override
                public void onResponse(Call<ApiIndex> call, Response<ApiIndex> response) {
                    setOnlineStatus(response.isSuccessful());
                }

                @Override
                public void onFailure(Call<ApiIndex> call, Throwable t) {
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
        apiStatus.setOnline(isOnline);
    }
    private void setRegionStatus( String districtKey ){
        regionStatusLayout.getEditText().setText(districtKey);
    }
    public void setRegionKey( String regionKey ){
        setRegionStatus( regionKey );
        apiStatus.setRegionKey(regionKey);
        Log.e("FtcApiActivity", "regionkey: " + regionKey);
    }

    public void setEventKey(String eventKey){
        setEventStatus(eventKey);
        apiStatus.setEventKey(eventKey);
    }

    private void setEventStatus(String eventKey){
        eventStatusLayout.getEditText().setText(eventKey);
        Log.e(TAG, "eventKey: " + eventKey);

    }

    public void setEventCode(String eventCode){
        setEventStatus(eventCode);
        apiStatus.setEventCode(eventCode);
    }
}

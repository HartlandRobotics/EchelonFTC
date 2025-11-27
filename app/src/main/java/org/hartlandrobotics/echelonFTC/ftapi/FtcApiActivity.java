package org.hartlandrobotics.echelonFTC.ftapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.EchelonActivity;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiStatus;
import org.hartlandrobotics.echelonFTC.ftapi.status.ApiStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FtcApiActivity extends EchelonActivity {
    ApiStatus apiStatus;

    TabLayout tabLayout;
    ViewPager2 viewPager;

    TextInputLayout onlineStatusLayout;




    public static void launch(Context context){
        Intent intent = new Intent(context, FtcApiActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tba);

        setupToolbar("FTC Events API");

        apiStatus = new ApiStatus(getApplicationContext());

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

//        orangeAlliancePagerAdapter = new OrangeAlliancePagerAdapter(getSupportFragmentManager(), getLifecycle());
//        viewPager.setAdapter(orangeAlliancePagerAdapter);
//
//        new TabLayoutMediator(tabLayout, viewPager,
//                (tab, position) -> tab.setText(orangeAlliancePagerAdapter.getTabTitle(position))).attach();
//
//        seasonStatusLayout = findViewById(R.id.seasonStatusLayout);
//        setSeasonStatus( tbaStatus.getSeason() );
//
//        districtStatusLayout = findViewById(R.id.districtStatusLayout);
//        setDistrictStatus( tbaStatus.getDistrictKey() );
//
//        eventStatusLayout = findViewById(R.id.eventStatusLayout);
//        setEventStatus(tbaStatus.getEventKey());

        onlineStatusLayout = findViewById(R.id.onlineStatusLayout);
        onlineStatusLayout.getEditText().setText(StringUtils.EMPTY);
        checkOnlineStatus();
    }

    private void checkOnlineStatus(){
        FtcApiInterface newApi = FtcApi.getApiClient(getApplication());
        try {
            Call<FtcApiStatus> statusCall = newApi.getStatus();
            statusCall.enqueue(new Callback<FtcApiStatus>() {
                @Override
                public void onResponse(Call<FtcApiStatus> call, Response<FtcApiStatus> response) {
                    setOnlineStatus(response.isSuccessful());
                }

                @Override
                public void onFailure(Call<FtcApiStatus> call, Throwable t) {
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


}

/*



public class OrangeAllianceActivity extends EchelonActivity {

    OrangeAlliancePagerAdapter orangeAlliancePagerAdapter;

    TextInputLayout seasonStatusLayout;
    TextInputLayout districtStatusLayout;
    TextInputLayout eventStatusLayout;
    TextInputLayout matchStatusLayout;





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
package org.hartlandrobotics.echelon2.TBA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelon2.R;

import org.hartlandrobotics.echelon2.TBA.fragments.DistrictsFragment;
import org.hartlandrobotics.echelon2.database.entities.District;
import org.hartlandrobotics.echelon2.database.repositories.DistrictRepo;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.List;


public class TBAActivity extends AppCompatActivity {
    BlueAllianceStatus tbaStatus;

    TabLayout tabLayout;
    ViewPager2 viewPager;
    TBAPagerAdapter tbaPagerAdapter;

    TextInputLayout districtStatusLayout;

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

    }

    private void checkOnlineStatus(){
        ApiInterface newApi = Api.getApiClient(getApplication());
    }
    private void setDistrictStatus( String districtKey ){
        districtStatusLayout.getEditText().setText(districtKey);
    }

    public void setDistrictKey( String districtKey ){
        setDistrictStatus( districtKey );
        tbaStatus.setDistrictKey(districtKey);
    }
    public String getDistrictKey(){
        return tbaStatus.getDistrictKey();
    }
}

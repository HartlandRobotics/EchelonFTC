package org.hartlandrobotics.echelonFTC.ftcscout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelonFTC.EchelonActivity;
import org.hartlandrobotics.echelonFTC.R;

public class FTCScoutActivity extends EchelonActivity {
    public static void launch(Context context){
        Intent intent = new Intent(context, FTCScoutActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftc_scout);

        setupToolbar("FTC Scout");

        ViewPager2 ftcScoutViewPager = findViewById(R.id.ftcScoutViewPager);
        TabLayout ftcScoutTabLayout = findViewById(R.id.ftcScoutTabLayout);

        FTCScoutPagerAdapter ftcScoutPagerAdapter = new FTCScoutPagerAdapter(getSupportFragmentManager(), getLifecycle());
        ftcScoutViewPager.setAdapter(ftcScoutPagerAdapter);

        new TabLayoutMediator(ftcScoutTabLayout, ftcScoutViewPager,
                (tab, position) -> tab.setText(ftcScoutPagerAdapter.getTabTitle(position))).attach();
    }
}

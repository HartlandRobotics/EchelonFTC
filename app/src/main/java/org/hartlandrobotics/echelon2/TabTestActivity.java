package org.hartlandrobotics.echelon2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelon2.database.entities.PSData;
import org.hartlandrobotics.echelon2.pitScouting.PitScoutingPagerAdapter;

import java.util.HashMap;
import java.util.Map;

public class TabTestActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    PitScoutingPagerAdapter viewPagerAdapter;

    public static void launch(Context context){
        Intent intent = new Intent( context, TabTestActivity.class );
        context.startActivity(intent);
    }

    private PSData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);

        data = new PSData("starting string", 42, false);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPagerAdapter = new PitScoutingPagerAdapter(
                getSupportFragmentManager(), getLifecycle(), data);
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(viewPagerAdapter.getTabTitle(position))
        ).attach();
    }
}
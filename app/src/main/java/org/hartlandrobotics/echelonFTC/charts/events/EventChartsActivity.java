package org.hartlandrobotics.echelonFTC.charts.events;

import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelonFTC.EchelonActivity;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.ftcapi.status.*;


public class EventChartsActivity extends EchelonActivity {

    TabLayout tabLayout;
    ViewPager2 chartViewPager;
    EventChartPagerAdapter eventChartPagerAdapter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, EventChartsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_charts);

        setupToolbar("Charts");

        setupTabLayout();
    }

    public void setupTabLayout() {
        tabLayout = findViewById(R.id.tabLayout);
        chartViewPager = findViewById(R.id.viewPager);
        eventChartPagerAdapter = new EventChartPagerAdapter(getSupportFragmentManager(), getLifecycle());
        chartViewPager.setAdapter(eventChartPagerAdapter);

        new TabLayoutMediator(tabLayout, chartViewPager, (tab, position) -> tab.setText(eventChartPagerAdapter.getTabTitle(position))).attach();
    }

    public void drawFragments() {
        if( eventChartPagerAdapter != null ){
            eventChartPagerAdapter.drawCharts();
        }
    }
}
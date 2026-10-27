package org.hartlandrobotics.echelonFTC.charts.teams;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelonFTC.EchelonActivity;
import org.hartlandrobotics.echelonFTC.R;

public class TeamChartsActivity extends EchelonActivity {

    TabLayout tabLayout;
    ViewPager2 chartViewPager;
    TeamChartPagerAdapter teamChartPagerAdapter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, TeamChartsActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_charts);

        setupToolbar("Team Charts");

        setupTabLayout();
    }

    public void setupTabLayout() {
        tabLayout = findViewById(R.id.tabLayout);
        chartViewPager = findViewById(R.id.viewPager);
        teamChartPagerAdapter = new TeamChartPagerAdapter(getSupportFragmentManager(), getLifecycle());
        chartViewPager.setAdapter(teamChartPagerAdapter);

        new TabLayoutMediator(tabLayout, chartViewPager, (tab, position) -> tab.setText(teamChartPagerAdapter.getTabTitle(position))).attach();
    }

}

/*

public class EventChartsActivity extends EchelonActivity {



    public void drawFragments() {
        if( eventChartPagerAdapter != null ){
            eventChartPagerAdapter.drawCharts();
        }
    }
}
 */
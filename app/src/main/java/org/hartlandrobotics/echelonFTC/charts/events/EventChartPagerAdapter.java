package org.hartlandrobotics.echelonFTC.charts.events;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventChartPagerAdapter extends FragmentStateAdapter {
    public static final String TAG = "EventChartPagerAdapter";

    private static final int AGGREGATE_AVERAGE_POSITION = 0;
    private static final int AGGREGATE_SCORING_ELEMENTS_POSITION = 1;

    private ChartAveragePointsFragment aggAveragePointsFragment;
    private ChartAverageScoringElementsFragment aggAverageScoringElementsFragment;

    public EventChartPagerAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    private Map<Integer, String> titleByPosition = new HashMap<>();

    public String getTabTitle(int position) {
        if (titleByPosition.isEmpty()) {
            titleByPosition.put(0, "Points");
            titleByPosition.put(1, "Scoring Elements");
        }
        return titleByPosition.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case AGGREGATE_AVERAGE_POSITION:
                Log.i(TAG, "creating new Aggregate Average Fragment");
                if (aggAveragePointsFragment == null) {
                    aggAveragePointsFragment = new ChartAveragePointsFragment();
                }
                return aggAveragePointsFragment;
            case AGGREGATE_SCORING_ELEMENTS_POSITION:
                Log.i(TAG, "creating new Scoring ElementsAverage Fragment");
                if (aggAverageScoringElementsFragment == null) {
                    aggAverageScoringElementsFragment = new ChartAverageScoringElementsFragment();
                }
                return aggAverageScoringElementsFragment;
            default:
                throw new IllegalArgumentException("invalid tab selection for charts activity");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}

package org.hartlandrobotics.echelon2.charts;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.pitScouting.PitScoutAutoFragment;
import org.hartlandrobotics.echelon2.pitScouting.PitScoutEndGameFragment;
import org.hartlandrobotics.echelon2.pitScouting.PitScoutPhotosFragment;
import org.hartlandrobotics.echelon2.pitScouting.PitScoutTeamFragment;
import org.hartlandrobotics.echelon2.pitScouting.PitScoutTeleOpFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartPagerAdapter extends FragmentStateAdapter {
    public static final String TAG = "ChartPagerAdapter";

    // data to come from activity List of TeamDataModels
    //private PitScout data;

    private static final int AGREGATE_AVERAGE_POSITION = 0;
    private static final int TREND_AUTO_POSITION = 1;
    private static final int TREND_TELEOP_POSITION = 2;
    private static final int TREND_ENDGAME_POSITION = 3;

    private ChartAggAverageFragment aggAverageFragment;
    private ChartAutoTrendFragment chartAutoTrendFragment;
    private ChartTeleOpTrendFragment chartTeleOpTrendFragment;
    private ChartEndGameTrendFragment chartEndGameTrendFragment;
    private List<TeamListViewModel> allTeamNumbers;

    public ChartPagerAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    private Map<Integer, String> titleByPosition = new HashMap<>();
    public String getTabTitle(int position){
        if( titleByPosition.size() == 0 ){
            titleByPosition.put(0,"Average Points");
            titleByPosition.put(1,"Trend Auto");
            titleByPosition.put(2, "Trend TeleOp");
            titleByPosition.put(3, "Trend EndGame");
        }
        return titleByPosition.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case AGREGATE_AVERAGE_POSITION:
                Log.i(TAG,"creating new Aggregate Average Fragment");
                aggAverageFragment = new ChartAggAverageFragment();
                return aggAverageFragment;
            case TREND_AUTO_POSITION:
                Log.i(TAG,"creating new Auto Trend Fragment");
                chartAutoTrendFragment = new ChartAutoTrendFragment();
                return chartAutoTrendFragment;
            case TREND_TELEOP_POSITION:
                Log.i(TAG,"creating new TeleOp Trend Fragment");
                chartTeleOpTrendFragment = new ChartTeleOpTrendFragment();
                return chartTeleOpTrendFragment;
            case TREND_ENDGAME_POSITION:
                Log.i(TAG, "creating new EndGame Fragment");
                chartEndGameTrendFragment = new ChartEndGameTrendFragment();
                return chartEndGameTrendFragment;
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
        return 4;
    }

    public void updateFragmentData(List<TeamListViewModel> allTeamNumbers, List<ChartsActivity.TeamDataViewModel> allTeamData) {
        this.allTeamNumbers = allTeamNumbers;

        if( allTeamNumbers == null ) return;

        if( aggAverageFragment != null ){
            aggAverageFragment.setData(allTeamNumbers, allTeamData);
        }

        if( chartAutoTrendFragment != null ){
            chartAutoTrendFragment.setData(allTeamNumbers);
        }

        if( chartTeleOpTrendFragment != null ){
            chartTeleOpTrendFragment.setData(allTeamNumbers);
        }

        if( chartEndGameTrendFragment != null){
            chartEndGameTrendFragment.setData(allTeamNumbers);
        }



    }
}

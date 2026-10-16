//package org.hartlandrobotics.echelonFTC.charts.teams;
//
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.lifecycle.Lifecycle;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//
//import org.hartlandrobotics.echelonFTC.charts.TeamListViewModel;
//import org.hartlandrobotics.echelonFTC.charts.events.ChartAveragePointsFragment;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class TeamChartPagerAdapter extends FragmentStateAdapter {
//    public static final String TAG = "TeamChartPagerAdapter";
//    private static final int TREND_SCORING_ELEMENTS_POSITION = 0;
//    private static final int TREND_AUTO_POSITION = 1;
//    private static final int TREND_TELEOP_POSITION = 2;
//    private static final int TREND_ENDGAME_POSITION = 3;
//
//    private ChartScoringElementsTrendFragment chartScoringElementsTrendFragment;
//    private ChartAutoTrendFragment chartAutoTrendFragment;
//    private ChartTeleOpTrendFragment chartTeleOpTrendFragment;
//    private ChartEndGameTrendFragment chartEndGameTrendFragment;
//    private List<TeamListViewModel> allTeamNumbers;
//
//    public TeamChartPagerAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
//        super(fragmentManager, lifecycle);
//    }
//
//    private Map<Integer, String> titleByPosition = new HashMap<>();
//    public String getTabTitle(int position){
//        if(titleByPosition.isEmpty()){
//            titleByPosition.put(0,"Trend Scoring Elements");
//            titleByPosition.put(1,"Trend Auto");
//            titleByPosition.put(2, "Trend TeleOp");
//            titleByPosition.put(3, "Trend EndGame");
//        }
//        return titleByPosition.get(position);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        switch (position){
//            case TREND_SCORING_ELEMENTS_POSITION:
//                Log.i(TAG,"creating new Aggregate Average Fragment");
//                if( aggAverageFragment == null ) {
//                    aggAverageFragment = new ChartAveragePointsFragment();
//                }
//                return aggAverageFragment;
//            case TREND_AUTO_POSITION:
//                Log.i(TAG,"creating new Auto Trend Fragment");
//                if( chartAutoTrendFragment == null ) {
//                    chartAutoTrendFragment = new ChartAutoTrendFragment();
//                }
//                return chartAutoTrendFragment;
//            case TREND_TELEOP_POSITION:
//                Log.i(TAG,"creating new TeleOp Trend Fragment");
//                if( chartTeleOpTrendFragment == null ) {
//                    chartTeleOpTrendFragment = new ChartTeleOpTrendFragment();
//                }
//                return chartTeleOpTrendFragment;
//            case TREND_ENDGAME_POSITION:
//                Log.i(TAG, "creating new EndGame Fragment");
//                if( chartEndGameTrendFragment == null ) {
//                    chartEndGameTrendFragment = new ChartEndGameTrendFragment();
//                }
//                return chartEndGameTrendFragment;
//            default:
//                throw new IllegalArgumentException("invalid tab selection for charts activity");
//        }
//    }
//
//}

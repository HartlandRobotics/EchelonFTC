package org.hartlandrobotics.echelon2.pitScouting;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import org.hartlandrobotics.echelon2.database.entities.PitScout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PitScoutingPagerAdapter extends FragmentStateAdapter {

    private PitScout data;

    private static final int AUTO_POSITION = 0;
    private static final int TELEOP_POSITION = 1;
    private static final int ENDGAME_POSITION = 2;
    private static final int TEAM_POSITION = 3;

    private PitScoutAutoFragment autoFragment;
    private PitScoutTeleOpFragment teleOpFragment;
    private PitScoutEndGameFragment endGameFragment;
    private PitScoutTeamFragment teamFragment;

    public PitScoutingPagerAdapter(
            @NonNull FragmentManager fragmentManager, Lifecycle lifecycle, PitScout data)
    {
        super(fragmentManager, lifecycle);
        this.data = data;
    }

    public static final String TAG = "PitScoutPagerAdapter";
    private Map<Integer, String> titleByPosition = new HashMap<>();
    public String getTabTitle(int position){
        if( titleByPosition.size() == 0 ){
            titleByPosition.put(0,"Auto");
            titleByPosition.put(1,"Tele Op");
            titleByPosition.put(2, "End  Game");
            titleByPosition.put(3, "Team");
        }
        return titleByPosition.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case AUTO_POSITION:
                Log.i(TAG,"creating new Auto Fragment");
                autoFragment = new PitScoutAutoFragment();
                autoFragment.setData(data);
                return autoFragment;
            case TELEOP_POSITION:
                Log.i(TAG,"creating new TeleOp Fragment");
                teleOpFragment = new PitScoutTeleOpFragment();
                teleOpFragment.setData(data);
                return teleOpFragment;
            case ENDGAME_POSITION:
                Log.i(TAG,"creating new EndGame Fragment");
                endGameFragment = new PitScoutEndGameFragment();
                endGameFragment.setData(data);
                return endGameFragment;
            case TEAM_POSITION:
                Log.i(TAG, "creating new Team Fragment");
                teamFragment = new PitScoutTeamFragment();
                teamFragment.setData(data);
                return teamFragment;
            default:
                Log.e(TAG, "Invalid tab selected for pit scout tab layout");
                return null;
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
}

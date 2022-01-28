package org.hartlandrobotics.echelon2.pitScouting;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.hartlandrobotics.echelon2.database.entities.PSData;

import java.util.HashMap;
import java.util.Map;

public class PitScoutingPagerAdapter extends FragmentStateAdapter {

    private PSData data;

    public PitScoutingPagerAdapter(
            @NonNull FragmentManager fragmentManager, Lifecycle lifecycle, PSData data)
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
        }
        return titleByPosition.get(position);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            Log.e(TAG,"creating new Auto Fragment");
            AutoFragment autoFragment = new AutoFragment();
            autoFragment.setData(data);
            fragment = autoFragment;
        }
        else if (position == 1) {
            Log.e(TAG,"creating new TeleOp Fragment");
            TeleOpFragment teleOpFragment = new TeleOpFragment();
            teleOpFragment.setData(data);
            fragment = teleOpFragment;
        }
        else if (position == 2) {
            Log.e(TAG,"creating new EndGame Fragment");
            EndGameFragment endGameFragment = new EndGameFragment();
            endGameFragment.setData(data);
            fragment = endGameFragment;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

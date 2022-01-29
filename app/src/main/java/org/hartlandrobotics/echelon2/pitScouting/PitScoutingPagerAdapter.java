package org.hartlandrobotics.echelon2.pitScouting;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.hartlandrobotics.echelon2.database.entities.PitScout;

import java.util.HashMap;
import java.util.Map;

public class PitScoutingPagerAdapter extends FragmentStateAdapter {

    private PitScout data;

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
        }
        return titleByPosition.get(position);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            Log.e(TAG,"creating new Auto Fragment");
            PitScoutAutoFragment pitScoutAutoFragment = new PitScoutAutoFragment();
            pitScoutAutoFragment.setData(data);
            fragment = pitScoutAutoFragment;
        }
        else if (position == 1) {
            Log.e(TAG,"creating new TeleOp Fragment");
            PitScoutTeleOpFragment pitScoutTeleOpFragment = new PitScoutTeleOpFragment();
            pitScoutTeleOpFragment.setData(data);
            fragment = pitScoutTeleOpFragment;
        }
        else if (position == 2) {
            Log.e(TAG,"creating new EndGame Fragment");
            PitScoutEndGameFragment pitScoutEndGameFragment = new PitScoutEndGameFragment();
            pitScoutEndGameFragment.setData(data);
            fragment = pitScoutEndGameFragment;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

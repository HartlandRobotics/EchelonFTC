package org.hartlandrobotics.echelonFTC.ftcapi;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.hartlandrobotics.echelonFTC.ftcapi.fragments.EventsFragment;
import org.hartlandrobotics.echelonFTC.ftcapi.fragments.MatchesFragment;
import org.hartlandrobotics.echelonFTC.ftcapi.fragments.RegionsFragment;
import org.hartlandrobotics.echelonFTC.ftcapi.fragments.TeamsFragment;

import java.util.HashMap;
import java.util.Map;

public class ApiPagerAdapter extends FragmentStateAdapter{
    public static final String TAG = "TBAPagerAdapter";

    public ApiPagerAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    private final Map<Integer, String> titleByPosition = new HashMap<>();
    public String getTabTitle(int position){
        if(titleByPosition.isEmpty()){
            titleByPosition.put(0,"Regions");
            titleByPosition.put(1,"Events");
            titleByPosition.put(2, "Teams");
            titleByPosition.put(3, "Matches");
        }
        return titleByPosition.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            Log.e(TAG,"creating new District Fragment");
            RegionsFragment regionsFragment = new RegionsFragment();
            fragment = regionsFragment;
        }
        else if (position == 1) {
            Log.e(TAG,"creating new Events Fragment");
            EventsFragment eventsFragment = new EventsFragment();
            fragment = eventsFragment;
        }
        else if (position == 2) {
            Log.e(TAG,"creating new Teams Fragment");
            TeamsFragment teamFragment = new TeamsFragment();
            fragment = teamFragment;
        }
        else if(position == 3){
            Log.e(TAG, "creating new Matches Fragment");
            MatchesFragment matchFragment = new MatchesFragment();
            fragment = matchFragment;
        }

        return fragment;
    }

    @Override
    public int getItemCount(){ return 4; }
}

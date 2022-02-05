package org.hartlandrobotics.echelon2.TBA;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.hartlandrobotics.echelon2.TBA.fragments.DistrictsFragment;
import org.hartlandrobotics.echelon2.TBA.fragments.EventsFragment;
import org.hartlandrobotics.echelon2.TBA.fragments.MatchesFragment;
import org.hartlandrobotics.echelon2.TBA.fragments.TeamsFragment;

import java.util.HashMap;
import java.util.Map;


public class TBAPagerAdapter extends FragmentStateAdapter {

   public TBAPagerAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
      super(fragmentManager, lifecycle);
   }

   public static final String TAG = "TBAPagerAdapter";
   private Map<Integer, String> titleByPosition = new HashMap<>();
   public String getTabTitle(int position){
      if( titleByPosition.size() == 0 ){
         titleByPosition.put(0,"Districts");
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
         DistrictsFragment districtFragment = new DistrictsFragment();
         fragment = districtFragment;
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

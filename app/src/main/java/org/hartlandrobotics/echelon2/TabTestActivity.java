package org.hartlandrobotics.echelon2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.database.entities.Team;
import org.hartlandrobotics.echelon2.models.TeamViewModel;
import org.hartlandrobotics.echelon2.pitScouting.PitScoutingPagerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TabTestActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    PitScoutingPagerAdapter viewPagerAdapter;
    AutoCompleteTextView teamNumberAutoComplete;

    TeamViewModel teamViewModel;
    List<String> teamNumbers;

    public static void launch(Context context){
        Intent intent = new Intent( context, TabTestActivity.class );
        context.startActivity(intent);
    }

    private PitScout data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);

        teamNumberAutoComplete = findViewById(R.id.teamSelectionAutoComplete);

        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        teamViewModel.getAllTeams().observe( this, teams -> {
            int size = teams.size();
            teamNumbers = teams.stream()
                    .map( t -> t.getTeamNumber() )
                    .sorted()
                    .map(t -> t.toString())
                    .collect(Collectors.toList());

            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_item, teamNumbers);
            teamNumberAutoComplete.setAdapter(adapter);
        });

        data = new PitScout();

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);



        viewPagerAdapter = new PitScoutingPagerAdapter(
                getSupportFragmentManager(), getLifecycle(), data);
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(viewPagerAdapter.getTabTitle(position))
        ).attach();
    }
}
package org.hartlandrobotics.echelon2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.database.entities.Team;
import org.hartlandrobotics.echelon2.models.TeamViewModel;
import org.hartlandrobotics.echelon2.pitScouting.PitScoutingPagerAdapter;
import org.hartlandrobotics.echelon2.utilities.TabLayoutUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PitScoutActivity extends AppCompatActivity {
    TabLayout tabLayout;
    MaterialTextView selectTextPrompt;
    ViewPager2 viewPager;
    PitScoutingPagerAdapter viewPagerAdapter;
    AutoCompleteTextView teamNumberAutoComplete;

    TeamViewModel teamViewModel;
    List<Team> teams;
    List<String> teamNames;
    Team currentTeam;
    private PitScout data;

    public static void launch(Context context) {
        Intent intent = new Intent(context, PitScoutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scout);

        teamNumberAutoComplete = findViewById(R.id.teamSelectionAutoComplete);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        selectTextPrompt = findViewById(R.id.select_team_text);


        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        teamViewModel.getAllTeams().observe(this, ts -> {

            teams = ts;

            teamNames = teams.stream()
                    .map(t -> t.getTeamNumber() + " - " + t.getNickname())
                    .sorted()
                    .collect(Collectors.toList());

            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_item, teamNames);
            teamNumberAutoComplete.setAdapter(adapter);
            teamNumberAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentTeam = teams.get(position);
                    if (hasSelectedTeam()) {
                        viewPager.setVisibility(View.VISIBLE);
                        selectTextPrompt.setVisibility(View.GONE);

                        viewPagerAdapter.notifyDataSetChanged();

                    }
                }
            });
        });

        data = new PitScout();


        viewPagerAdapter = new PitScoutingPagerAdapter(
                getSupportFragmentManager(), getLifecycle(), data);
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(viewPagerAdapter.getTabTitle(position))
        ).attach();

        if (!hasSelectedTeam()) {
            viewPager.setVisibility(View.INVISIBLE);
        }

    }

    public boolean hasSelectedTeam() {
        return currentTeam != null;
    }

}
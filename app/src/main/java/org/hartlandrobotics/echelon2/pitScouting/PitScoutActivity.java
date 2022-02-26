package org.hartlandrobotics.echelon2.pitScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.EchelonActivity;
import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.database.entities.Team;
import org.hartlandrobotics.echelon2.models.PitScoutViewModel;
import org.hartlandrobotics.echelon2.models.TeamViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PitScoutActivity extends EchelonActivity {
    private static final String TAG = "PitScoutActivity";
    private BlueAllianceStatus status;
    TabLayout tabLayout;
    MaterialTextView selectTextPrompt;
    ViewPager2 viewPager;
    PitScoutingPagerAdapter viewPagerAdapter;
    AutoCompleteTextView teamNumberAutoComplete;
    MaterialButton saveButton;
    TeamViewModel teamViewModel;
    List<Team> teams;
    List<String> teamNames;
    Team currentTeam;

    PitScoutViewModel pitScoutViewModel;
    private PitScout data;

    public static void launch(Context context) {
        Intent intent = new Intent(context, PitScoutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scout);

        setupToolbar();

        status = new BlueAllianceStatus(getApplicationContext());

        saveButton = findViewById(R.id.ps_save_button);
        teamNumberAutoComplete = findViewById(R.id.teamSelectionAutoComplete);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        selectTextPrompt = findViewById(R.id.select_team_text);

        pitScoutViewModel = new ViewModelProvider(this).get(PitScoutViewModel.class);
        saveButton.setOnClickListener(v -> {
            if (data == null) {
                Log.e(TAG, "no pit scout data to save");
                return;
            }

            viewPagerAdapter.updatePitScoutData();
            pitScoutViewModel.upsert(data);
        });
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        teamViewModel.getAllTeams().observe(this, ts -> {

            teams = ts.stream()
                    .sorted(Comparator.comparingInt(t -> t.getTeamNumber()))
                    .collect(Collectors.toList());


            teamNames = teams.stream()
                    .map(t -> t.getTeamNumber() + " - " + t.getNickname())
                    //.sorted(Comparator.comparingInt(t -> t.getTeamNumber()))
                    .collect(Collectors.toList());


            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_item, teamNames);
            teamNumberAutoComplete.setAdapter(adapter);
            teamNumberAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
                currentTeam = teams.get(position);
                if (hasSelectedTeam()) {
                    viewPager.setVisibility(View.VISIBLE);
                    selectTextPrompt.setVisibility(View.GONE);

                    String eventKey = status.getEventKey();
                    pitScoutViewModel.getPitScout(eventKey, currentTeam.getTeamKey())
                            .observe(PitScoutActivity.this, ps->{
                                if( ps == null ){
                                    data = pitScoutViewModel.getDefault(status.getEventKey(), currentTeam.getTeamKey());
                                } else {
                                    data = ps;
                                }
                                viewPagerAdapter.setData(data);
                                viewPagerAdapter.notifyDataSetChanged();
                            });
                }
            });
        });

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
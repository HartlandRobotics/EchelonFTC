package org.hartlandrobotics.echelon2.charts;

import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelon2.EchelonActivity;
import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelon2.status.OrangeAllianceStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChartsActivity extends EchelonActivity {

    TabLayout tabLayout;
    ViewPager2 chartViewPager;
    ChartPagerAdapter chartPagerAdapter;

    MatchResultRepo matchResultRepo;
    Map<String, List<MatchResult>> matchResultsByTeam =  new HashMap<>();
    List<TeamListViewModel> allTeamNumbers = new ArrayList<>();
    List<TeamDataViewModel> allTeamsData = new ArrayList<>();
    List<TeamDataViewModel> visibleTeams;

    public static void launch(Context context){
        Intent intent = new Intent(context, ChartsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        setupToolbar("Charts");

        setupTabLayout();

        setupData();
    }

    public void setupTabLayout(){
        tabLayout = findViewById(R.id.tabLayout);
        chartViewPager = findViewById(R.id.viewPager);
        chartPagerAdapter = new ChartPagerAdapter(getSupportFragmentManager(), getLifecycle());
        chartViewPager.setAdapter(chartPagerAdapter);

        new TabLayoutMediator(tabLayout, chartViewPager, (tab, position) -> tab.setText(chartPagerAdapter.getTabTitle(position))).attach();
    }

    public void setupData(){
        OrangeAllianceStatus status = new OrangeAllianceStatus(getApplicationContext());
        String currentEvent = status.getEventKey();

        matchResultRepo = new MatchResultRepo(getApplication());
        matchResultRepo.getMatchResultsByEvent(currentEvent).observe(this, mr -> {
            for( MatchResult matchResult : mr ){
                String teamKey = matchResult.getTeamKey();
                matchResultsByTeam.computeIfAbsent(teamKey,key -> new ArrayList<>());
                matchResultsByTeam.get(teamKey).add(matchResult);
            }

            allTeamNumbers.addAll(
                    matchResultsByTeam.keySet().stream()
                            .map( teamNumber -> new TeamListViewModel(teamNumber.substring(3)))
                            .sorted(Comparator.comparingInt(TeamListViewModel::getTeamInteger))
                            .collect(Collectors.toList()));

            for( Map.Entry<String, List<MatchResult>> entry : matchResultsByTeam.entrySet() ){
                int autoTotal = 0;
                int teleOpTotal = 0;
                int endGameTotal = 0;
                int total = 0;

                Map<Integer, Integer> autoScores = new HashMap<>();
                Map<Integer, Integer> teleOpScores = new HashMap<>();
                Map<Integer, Integer> endGameScores = new HashMap<>();
                String key = entry.getKey();
                int teamNumber = Integer.valueOf( entry.getKey().substring(3) );
                List<MatchResult> matchResults = entry.getValue();
                for( MatchResult matchResult : matchResults ){
                    Integer matchNumber = Integer.valueOf(matchResult.getMatchKey().replace( matchResult.getEventKey() + "_qm", ""));
                    int matchAuto = 0;
                    matchAuto += matchResult.getAutoHighBalls() * 4;
                    matchAuto += matchResult.getAutoLowBalls() * 2;
                    matchAuto += matchResult.getTaxiTarmac() ? 2 : 0;
                    autoTotal += matchAuto;
                    autoScores.put(matchNumber, matchAuto);


                    int matchTeleOp = 0;
                    matchTeleOp += matchResult.getTeleOpHighBalls() * 2;
                    matchTeleOp += matchResult.getTeleOpLowBalls();
                    teleOpTotal += matchTeleOp;
                    teleOpScores.put(matchNumber, matchTeleOp);


                    int matchEndGame = 0;
                    matchEndGame += matchResult.getEndHangLow() ? 4 : 0;
                    matchEndGame += matchResult.getEndHangMid() ? 6 : 0;
                    matchEndGame += matchResult.getEndHangHigh() ? 10 : 0;
                    matchEndGame += matchResult.getEndHangTraverse() ? 15 : 0;
                    endGameTotal += matchEndGame;
                    endGameScores.put(matchNumber, matchEndGame);

                    total = autoTotal + teleOpTotal + endGameTotal;
                }

                // size is only used to calculate averages.
                // 1 is default since it is multiplicitive identity
                int size = matchResults.size() == 0 ? 1 : matchResults.size();
                TeamDataViewModel teamData = new TeamDataViewModel(
                        teamNumber,
                        autoTotal/size,
                        teleOpTotal/size,
                        endGameTotal/size,
                        total/size,
                        autoScores,
                        teleOpScores,
                        endGameScores
                );
                allTeamsData.add(teamData);
            }


            updateFragmentData(allTeamNumbers, allTeamsData);

            //setupChart();
        });

   }
    public void updateFragmentData(List<TeamListViewModel> allTeamNumbers, List<TeamDataViewModel> allTeamsData){
        chartPagerAdapter.updateFragmentData(allTeamNumbers, allTeamsData);
        chartPagerAdapter.notifyDataSetChanged();

    }

    public List<TeamListViewModel> getAllTeamNumbers(){
        return allTeamNumbers;
    }
    public TeamDataViewModel getTeamData(String teamNumber){
        Optional<TeamDataViewModel> teamData = allTeamsData.stream()
                .filter( td -> td.getTeamNumber() == Integer.valueOf(teamNumber))
                .findFirst();

        return teamData.isPresent() ? teamData.get() : null;
    }




    public static class TeamDataViewModel{
        private int teamNumber;
        private float autoAverage;
        private float teleOpAverage;
        private float endGameAverage;
        private float totalAverage;
        private Map<Integer, Integer> autoScores;
        private Map<Integer, Integer> teleOpScores;
        private Map<Integer, Integer> endGameScores;

        public TeamDataViewModel(int teamNumber, float autoAverage, float teleOpAverage, float endGameAverage, float totalAverage,
                                 Map<Integer, Integer> autoScores, Map<Integer, Integer> teleOpScores, Map<Integer, Integer> endGameScores){
            this.teamNumber = teamNumber;
            this.autoAverage = autoAverage;
            this.teleOpAverage = teleOpAverage;
            this.endGameAverage = endGameAverage;
            this.totalAverage = totalAverage;
            this.autoScores = autoScores;
            this.teleOpScores = teleOpScores;
            this.endGameScores = endGameScores;
        }

        public int getTeamNumber() { return teamNumber; }
        public float getAutoAverage() { return autoAverage; }
        public float getTeleOpAverage() { return teleOpAverage; }
        public float getEndGameAverage() { return endGameAverage; }
        public float getTotalAverage() { return totalAverage; }
        public Map<Integer, Integer> getAutoScores() { return autoScores; }
        public Map<Integer, Integer> getTeleOpScores() { return teleOpScores; }
        public Map<Integer, Integer> getEndGameScores() { return endGameScores; }
    }
}
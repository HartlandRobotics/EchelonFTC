package org.hartlandrobotics.echelonFTC.charts;

import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelonFTC.EchelonActivity;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.currentGame.CurrentGame;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFTC.ftcapi.status.*;

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
        FtcApiStatus apiStatus = new FtcApiStatus(getApplicationContext());
        String currentEvent = apiStatus.getEventKey();

        matchResultRepo = new MatchResultRepo(getApplication());
        matchResultRepo.getMatchResultsByEvent(currentEvent).observe(this, mr -> {
            for( MatchResult matchResult : mr ){
                String teamKey = matchResult.getTeamKey();
                matchResultsByTeam.computeIfAbsent(teamKey,key -> new ArrayList<>());
                matchResultsByTeam.get(teamKey).add(matchResult);
            }

            allTeamNumbers.addAll(
                    matchResultsByTeam.keySet().stream()
                            .map( teamNumber -> new TeamListViewModel(teamNumber))
                            .sorted(Comparator.comparingInt(TeamListViewModel::getTeamInteger))
                            .collect(Collectors.toList()));

            for( Map.Entry<String, List<MatchResult>> entry : matchResultsByTeam.entrySet() ){
                int autoTotal = 0;
                int teleOpTotal = 0;
                int endGameTotal = 0;
                int oprTotal = 0;
                int total = 0;

                Map<Integer, Integer> autoScores = new HashMap<>();
                Map<Integer, Integer> teleOpScores = new HashMap<>();
                Map<Integer, Integer> endGameScores = new HashMap<>();
                Map<Integer, Integer> oprScores = new HashMap<>();
                String key = entry.getKey();
                int teamNumber = Integer.parseInt( entry.getKey() );
                List<MatchResult> matchResults = entry.getValue();
                for( MatchResult matchResult : matchResults ){
                    CurrentGame currentGamePoints = MatchResult.toCurrentGamePoints(matchResult);
                    // 2324-FIM-HAQ-Q001-1
                    Integer matchNumber = Integer.valueOf(
                            matchResult.getMatchKey().split("_")[2]
                    );

                    if( currentGamePoints.getContribution() == 0 ) {
                        int matchAuto = 0;
                        matchAuto += currentGamePoints.getAutoPoints();
                        autoScores.put(matchNumber, matchAuto);
                        autoTotal += matchAuto;


                        int matchTeleOp = 0;
                        matchTeleOp += currentGamePoints.getTeleOpPoints();
                        teleOpScores.put(matchNumber, matchTeleOp);
                        teleOpTotal += matchTeleOp;


                        int matchEndGame = 0;
                        matchEndGame += currentGamePoints.getEndPoints();
                        endGameScores.put(matchNumber, matchEndGame);
                        endGameTotal += matchEndGame;

                        int matchOpr = 0;
                        oprScores.put(matchNumber, matchOpr);

                        total = autoTotal + teleOpTotal + endGameTotal;
                    } else {
                        autoScores.put(matchNumber, 0);
                        teleOpScores.put(matchNumber, 0);
                        endGameScores.put(matchNumber, 0);

                        int matchOpr = 0;
                        matchOpr += currentGamePoints.getContribution();
                        oprTotal += matchOpr;
                        oprScores.put(matchNumber, matchOpr);

                        total = oprTotal;
                    }
                }

                // size is only used to calculate averages.
                // 1 is default since it is multiplicitive identity
                int size = matchResults.size();// == 0 ? 1 : matchResults.size();
                TeamDataViewModel teamData = new TeamDataViewModel(
                        teamNumber,
                        autoTotal/size,
                        teleOpTotal/size,
                        endGameTotal/size,
                        oprTotal/size,
                        total/size,
                        autoScores,
                        teleOpScores,
                        endGameScores,
                        oprScores
                );
                allTeamsData.add(teamData);
            }
            updateFragmentData(allTeamNumbers, allTeamsData);
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

        return teamData.orElse(null);
    }

    public static class TeamDataViewModel{
        private int teamNumber;
        private float autoAverage;
        private float teleOpAverage;
        private float endGameAverage;
        private float oprAverage;
        private float totalAverage;
        private Map<Integer, Integer> autoScores;
        private Map<Integer, Integer> teleOpScores;
        private Map<Integer, Integer> endGameScores;
        private Map<Integer, Integer> oprScores;

        public TeamDataViewModel(int teamNumber, float autoAverage, float teleOpAverage, float endGameAverage, float oprAverage, float totalAverage,
                                 Map<Integer, Integer> autoScores, Map<Integer, Integer> teleOpScores, Map<Integer, Integer> endGameScores, Map<Integer,Integer> oprScores){
            this.teamNumber = teamNumber;
            this.autoAverage = autoAverage;
            this.teleOpAverage = teleOpAverage;
            this.endGameAverage = endGameAverage;
            this.oprAverage = oprAverage;
            this.totalAverage = totalAverage;
            this.autoScores = autoScores;
            this.teleOpScores = teleOpScores;
            this.endGameScores = endGameScores;
            this.oprScores = oprScores;
        }

        public int getTeamNumber() { return teamNumber; }
        public float getAutoAverage() { return autoAverage; }
        public float getTeleOpAverage() { return teleOpAverage; }
        public float getEndGameAverage() { return endGameAverage; }
        public float getOprAverage() { return oprAverage; }

        public float getTotalAverage() { return totalAverage; }
        public Map<Integer, Integer> getAutoScores() { return autoScores; }
        public Map<Integer, Integer> getTeleOpScores() { return teleOpScores; }
        public Map<Integer, Integer> getEndGameScores() { return endGameScores; }
        public Map<Integer, Integer> getOprScores() { return oprScores; }

    }
}
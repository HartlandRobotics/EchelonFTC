package org.hartlandrobotics.echelon2;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelon2.blueAlliance.fragments.MatchListViewModel;
import org.hartlandrobotics.echelon2.blueAlliance.fragments.MatchesFragment;
import org.hartlandrobotics.echelon2.database.entities.Match;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.database.repositories.EventRepo;
import org.hartlandrobotics.echelon2.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchScheduleActivity extends EchelonActivity {
    EventRepo eventRepo;
    MatchResultRepo matchResultRepo;
    Map<String, List<MatchResult>> matchResultsByTeam =  new HashMap<>();
    List<MatchScheduleViewModel> viewModels = new ArrayList<>();
    TextInputLayout teamSearchLayout;
    RecyclerView matchRecycler;
    MatchListAdapter matchListAdapter;
    String deviceName;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MatchScheduleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_schedule);

        deviceName = Settings.Secure.getString(getContentResolver(), "bluetooth_name");

        setupToolbar("Match Schedule");

        teamSearchLayout = findViewById(R.id.team_search);
        teamSearchLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                matchListAdapter.setTeamFilter(StringUtils.defaultIfBlank(s.toString(), StringUtils.EMPTY));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        matchListAdapter = new MatchListAdapter(this);

      matchRecycler = findViewById(R.id.match_recycler);
      matchRecycler.setLayoutManager(new LinearLayoutManager(this));
      matchRecycler.setAdapter(matchListAdapter);
      matchRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        BlueAllianceStatus status = new BlueAllianceStatus(getApplicationContext());
        String currentEvent = status.getEventKey();

        eventRepo = new EventRepo(getApplication());
        matchResultRepo = new MatchResultRepo(getApplication());
        matchResultRepo.getMatchResultsByEvent(currentEvent).observe(this, matchResults -> {
            for( MatchResult matchResult : matchResults ){
                String teamKey = matchResult.getTeamKey();
                matchResultsByTeam.computeIfAbsent(teamKey,key -> new ArrayList<>());
                matchResultsByTeam.get(teamKey).add(matchResult);
            }

            eventRepo.getMatchesForEvent(currentEvent).observe(this, event -> {
                List<Match> matches = event.matches;

                for( Match match : matches ){
                    MatchScheduleViewModel matchScheduleViewModel = new MatchScheduleViewModel();

                    matchScheduleViewModel.setMatchNumber( match.getMatchNumber() );

                    int red1Average = getAverageByTeam(match.getRed1TeamKey());
                    matchScheduleViewModel.setRed1(match.getRed1TeamKey());
                    matchScheduleViewModel.setRed1Average(red1Average);
                    matchScheduleViewModel.setRed1Cargo( getAverageCargoCountByTeam(match.getRed1TeamKey()) );
                    matchScheduleViewModel.setRed1Hang( getAverageHangPointsByTeam(match.getRed1TeamKey()) );
                    matchScheduleViewModel.setRed1StdDeviation(getStdDeviationByTeam(match.getRed1TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getRed1TeamKey())));

                    int red2Average = getAverageByTeam(match.getRed2TeamKey());
                    matchScheduleViewModel.setRed2(match.getRed2TeamKey());
                    matchScheduleViewModel.setRed2Average(red2Average);
                    matchScheduleViewModel.setRed2Cargo( getAverageCargoCountByTeam(match.getRed2TeamKey()) );
                    matchScheduleViewModel.setRed2Hang( getAverageHangPointsByTeam(match.getRed2TeamKey()) );
                    matchScheduleViewModel.setRed2StdDeviation(getStdDeviationByTeam(match.getRed2TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getRed2TeamKey())));

                    int red3Average = getAverageByTeam(match.getRed3TeamKey());
                    matchScheduleViewModel.setRed3(match.getRed3TeamKey());
                    matchScheduleViewModel.setRed3Average(red3Average);
                    matchScheduleViewModel.setRed3Cargo( getAverageCargoCountByTeam(match.getRed3TeamKey()) );
                    matchScheduleViewModel.setRed3Hang( getAverageHangPointsByTeam(match.getRed3TeamKey()) );
                    matchScheduleViewModel.setRed3StdDeviation(getStdDeviationByTeam(match.getRed3TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getRed3TeamKey())));

                    int blue1Average = getAverageByTeam(match.getBlue1TeamKey());
                    matchScheduleViewModel.setBlue1(match.getBlue1TeamKey());
                    matchScheduleViewModel.setBlue1Average(blue1Average);
                    matchScheduleViewModel.setBlue1Cargo( getAverageCargoCountByTeam(match.getBlue1TeamKey()) );
                    matchScheduleViewModel.setBlue1Hang( getAverageHangPointsByTeam(match.getBlue1TeamKey()) );
                    matchScheduleViewModel.setBlue1StdDeviation(getStdDeviationByTeam(match.getBlue1TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getBlue1TeamKey())));

                    int blue2Average = getAverageByTeam(match.getBlue2TeamKey());
                    matchScheduleViewModel.setBlue2(match.getBlue2TeamKey());
                    matchScheduleViewModel.setBlue2Average(blue2Average);
                    matchScheduleViewModel.setBlue2Cargo( getAverageCargoCountByTeam(match.getBlue2TeamKey()) );
                    matchScheduleViewModel.setBlue2Hang( getAverageHangPointsByTeam(match.getBlue2TeamKey()) );
                    matchScheduleViewModel.setBlue2StdDeviation(getStdDeviationByTeam(match.getBlue2TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getBlue2TeamKey())));

                    int blue3Average = getAverageByTeam(match.getBlue3TeamKey());
                    matchScheduleViewModel.setBlue3(match.getBlue3TeamKey());
                    matchScheduleViewModel.setBlue3Average(blue3Average);
                    matchScheduleViewModel.setBlue3Cargo( getAverageCargoCountByTeam(match.getBlue3TeamKey()) );
                    matchScheduleViewModel.setBlue3Hang( getAverageHangPointsByTeam(match.getBlue3TeamKey()) );
                    matchScheduleViewModel.setBlue3StdDeviation(getStdDeviationByTeam(match.getBlue3TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getBlue3TeamKey())));

                    viewModels.add(matchScheduleViewModel);
                }

                matchListAdapter.setMatches(viewModels);
                System.out.println("break here");
            });

        });
    }

    private int getSize(String teamKey){
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if( teamMatchResults ==null) return 0;
        return teamMatchResults.size();
    }
    private int getAverageCargoCountByTeam(String teamKey){
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if( teamMatchResults == null || teamMatchResults.size() == 0 ) return 0;

        int totalCargoCount = 0;
        for(MatchResult matchResult : teamMatchResults ){
            totalCargoCount += matchResult.getAutoLowBalls() + matchResult.getAutoHighBalls() +
                    matchResult.getTeleOpLowBalls() + matchResult.getTeleOpHighBalls();
        }
        int averageCargoCount = totalCargoCount / teamMatchResults.size();
        return averageCargoCount;
    }

    private int getAverageHangPointsByTeam(String teamKey){
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if( teamMatchResults == null || teamMatchResults.size() == 0 ) return 0;

        int totalHangPoints = 0;
        for(MatchResult matchResult : teamMatchResults ){
            totalHangPoints += (matchResult.getEndHangLow()? 1 : 0 ) * 4;
            totalHangPoints += (matchResult.getEndHangMid()? 1 : 0 ) * 6;
            totalHangPoints += (matchResult.getEndHangHigh()? 1 : 0 ) * 10;
            totalHangPoints += (matchResult.getEndHangTraverse()? 1 : 0 ) * 15;
        }
        int averageHangPoints = totalHangPoints / teamMatchResults.size();
        return averageHangPoints;
    }

    private int getAverageByTeam( String teamKey ){
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if( teamMatchResults == null || teamMatchResults.size() == 0 ) return 0;

        int totalScore = 0;
        for( MatchResult matchResult : teamMatchResults ){
            int matchScore = 0;
            matchScore += matchResult.getAutoHighBalls() * 4;
            matchScore += matchResult.getAutoLowBalls() * 2;
            matchScore += (matchResult.getTaxiTarmac()? 1 : 0) * 2;
            matchScore += matchResult.getAutoHumanPlayerShots() * 4;

            matchScore += matchResult.getTeleOpHighBalls() * 2;
            matchScore += matchResult.getTeleOpLowBalls() * 1;

            matchScore += (matchResult.getEndHangLow()? 1 : 0 ) * 4;
            matchScore += (matchResult.getEndHangMid()? 1 : 0 ) * 6;
            matchScore += (matchResult.getEndHangHigh()? 1 : 0 ) * 10;
            matchScore += (matchResult.getEndHangTraverse()? 1 : 0 ) * 15;

            totalScore += matchScore;
        }

        int averageScore = totalScore / teamMatchResults.size();

        return averageScore;
    }

    private double getStdDeviationByTeam( String teamKey ){
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if( teamMatchResults == null || teamMatchResults.size() == 0 ) return 0;

        int totalScore = 0;
        for( MatchResult matchResult : teamMatchResults ){
            int matchScore = 0;
            matchScore += matchResult.getAutoHighBalls() * 4;
            matchScore += matchResult.getAutoLowBalls() * 2;
            matchScore += (matchResult.getTaxiTarmac()? 1 : 0) * 2;
            matchScore += matchResult.getAutoHumanPlayerShots() * 4;

            matchScore += matchResult.getTeleOpHighBalls() * 2;
            matchScore += matchResult.getTeleOpLowBalls() * 1;

            matchScore += (matchResult.getEndHangLow()? 1 : 0 ) * 4;
            matchScore += (matchResult.getEndHangMid()? 1 : 0 ) * 6;
            matchScore += (matchResult.getEndHangHigh()? 1 : 0 ) * 10;
            matchScore += (matchResult.getEndHangTraverse()? 1 : 0 ) * 15;

            totalScore += matchScore;
        }

        double averageScore = (double)totalScore / teamMatchResults.size();

        int totalDeviation = 0;
        for( MatchResult matchResult : teamMatchResults ){
            int matchScore = 0;
            matchScore += matchResult.getAutoHighBalls() * 4;
            matchScore += matchResult.getAutoLowBalls() * 2;
            matchScore += (matchResult.getTaxiTarmac()? 1 : 0) * 2;
            matchScore += matchResult.getAutoHumanPlayerShots() * 4;

            matchScore += matchResult.getTeleOpHighBalls() * 2;
            matchScore += matchResult.getTeleOpLowBalls() * 1;

            matchScore += (matchResult.getEndHangLow()? 1 : 0 ) * 4;
            matchScore += (matchResult.getEndHangMid()? 1 : 0 ) * 6;
            matchScore += (matchResult.getEndHangHigh()? 1 : 0 ) * 10;
            matchScore += (matchResult.getEndHangTraverse()? 1 : 0 ) * 15;

            totalDeviation += (matchScore - averageScore) * (matchScore - averageScore);
        }

        double stdDeviation = Math.sqrt(totalDeviation / teamMatchResults.size());

        return stdDeviation;

    }

    public class MatchScheduleViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView matchNumber;
        //private MaterialTextView matchKey;
        private MaterialTextView red1;
        private MaterialTextView red2;
        private MaterialTextView red3;
        private MaterialTextView blue1;
        private MaterialTextView blue2;
        private MaterialTextView blue3;
        private MaterialTextView redPrediction;
        private MaterialTextView bluePrediction;
        private MaterialTextView redCargoPrediction;
        private MaterialTextView blueCargoPrediction;
        private MaterialTextView redHangerPrediction;
        private MaterialTextView blueHangerPrediction;
        private MaterialTextView redConfidenceIntervalMin;
        private MaterialTextView blueConfidenceIntervalMin;
        private MaterialTextView redConfidenceIntervalMax;
        private MaterialTextView blueConfidenceIntervalMax;
        private MaterialTextView redPercentage;
        private MaterialTextView bluePercentage;
        private LinearLayout predictionLayout;

        private MatchScheduleViewModel matchScheduleViewModel;

        MatchScheduleViewHolder(View itemView){
            super(itemView);


            matchNumber = itemView.findViewById(R.id.match_number);
            red1 = itemView.findViewById(R.id.red1);
            red2 = itemView.findViewById(R.id.red2);
            red3 = itemView.findViewById(R.id.red3);
            blue1 = itemView.findViewById(R.id.blue1);
            blue2 = itemView.findViewById(R.id.blue2);
            blue3 = itemView.findViewById(R.id.blue3);
            redPrediction = itemView.findViewById(R.id.red_prediction);
            bluePrediction = itemView.findViewById(R.id.blue_prediction);
            redCargoPrediction = itemView.findViewById(R.id.red_cargo_count);
            blueCargoPrediction = itemView.findViewById(R.id.blue_cargo_count);
            redHangerPrediction = itemView.findViewById(R.id.red_hang_points);
            blueHangerPrediction = itemView.findViewById(R.id.blue_hang_points);
            redConfidenceIntervalMin = itemView.findViewById(R.id.red_confidence_interval_min);
            blueConfidenceIntervalMin = itemView.findViewById(R.id.blue_confidence_interval_min);
            redConfidenceIntervalMax = itemView.findViewById(R.id.red_confidence_interval_max);
            blueConfidenceIntervalMax = itemView.findViewById(R.id.blue_confidence_interval_max);
            redPercentage = itemView.findViewById(R.id.red_percentage_prediction);
            bluePercentage = itemView.findViewById(R.id.blue_percentage_prediction);

            predictionLayout = itemView.findViewById(R.id.prediction_layout);
            if( !( deviceName.contains("aptain" ) || deviceName.contains("oach"))){
                predictionLayout.setVisibility(View.INVISIBLE);
            }

        }

        public void setMatch(MatchScheduleViewModel matchScheduleViewModel){
            this.matchScheduleViewModel = matchScheduleViewModel;

            matchNumber.setText(String.valueOf(matchScheduleViewModel.getMatchNumber()));
            red1.setText("1: " + matchScheduleViewModel.getRed1());
            red2.setText("2: " + matchScheduleViewModel.getRed2());
            red3.setText("3: " + matchScheduleViewModel.getRed3());
            blue1.setText("1: " + matchScheduleViewModel.getBlue1());
            blue2.setText("2: " + matchScheduleViewModel.getBlue2());
            blue3.setText("3: " + matchScheduleViewModel.getBlue3());
            redPrediction.setText( String.valueOf( matchScheduleViewModel.getRedTotal() ) );
            bluePrediction.setText( String.valueOf( matchScheduleViewModel.getBlueTotal() ) );

            redConfidenceIntervalMin.setText(String.valueOf(matchScheduleViewModel.getRedConfidenceIntervalMin()));
            blueConfidenceIntervalMin.setText(String.valueOf(matchScheduleViewModel.getBlueConfidenceIntervalMin()));
            redConfidenceIntervalMax.setText(String.valueOf(matchScheduleViewModel.getRedConfidenceIntervalMax()));
            blueConfidenceIntervalMax.setText(String.valueOf(matchScheduleViewModel.getBlueConfidenceIntervalMax()));
            redPercentage.setText(matchScheduleViewModel.getRedPercentage() + "%");
            bluePercentage.setText(matchScheduleViewModel.getBluePercentage() + "%");

            redCargoPrediction.setText( String.valueOf(matchScheduleViewModel.getRedCargoTotal()));
            blueCargoPrediction.setText( String.valueOf(matchScheduleViewModel.getBlueCargoTotal()));
            redHangerPrediction.setText( String.valueOf(matchScheduleViewModel.getRedHangTotal()));
            blueHangerPrediction.setText( String.valueOf(matchScheduleViewModel.getBlueHangTotal()));
        }

        public void setDisplayText(String displayText){
            matchNumber.setText(displayText);
        }
    }

    public class MatchListAdapter extends RecyclerView.Adapter<MatchScheduleViewHolder>{
        private final LayoutInflater inflater;
        private List<MatchScheduleViewModel> allHolderViewModels;
        private List<MatchScheduleViewModel> holderViewModels;
        private String teamFilter = StringUtils.EMPTY;

        MatchListAdapter(Context context){
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public MatchScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_match_schedule, parent, false);
            return new MatchScheduleViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MatchScheduleViewHolder holder, int position) {
            if(holderViewModels != null){
                holder.setMatch(holderViewModels.get(position));
            }else{
                holder.setDisplayText("No Match Data Yet...");
            }
        }

        void setTeamFilter(String filter){
            teamFilter = StringUtils.defaultIfBlank(filter, StringUtils.EMPTY);
            setMatches(allHolderViewModels);
        }

        void setMatches(List<MatchScheduleViewModel> vms){
            allHolderViewModels = vms;
            String filter = StringUtils.defaultIfBlank(teamFilter, StringUtils.EMPTY );
            String teamKeyFilter = "frc" + filter;
            holderViewModels = vms.stream()
                    .sorted(Comparator.comparingInt(m -> Integer.valueOf( m.getMatchNumber())))
                    .filter( m -> StringUtils.isBlank(filter)
                            || m.getRed1().equals(teamKeyFilter)
                            || m.getRed2().equals(teamKeyFilter)
                            || m.getRed3().equals(teamKeyFilter)
                            || m.getBlue1().equals(teamKeyFilter)
                            || m.getBlue2().equals(teamKeyFilter)
                            || m.getBlue3().equals(teamKeyFilter)
                    )
                    .collect(Collectors.toList());

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return holderViewModels == null ? 0 : holderViewModels.size();
        }
    }
}
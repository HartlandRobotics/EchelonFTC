package org.hartlandrobotics.echelonFTC;

import static java.util.Collections.sort;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.database.currentGame.CurrentGame;
import org.hartlandrobotics.echelonFTC.database.entities.Match;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFTC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchScheduleActivity extends EchelonActivity {

    static String TAG = "MatchScheduleActivity";
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

        OrangeAllianceStatus status = new OrangeAllianceStatus(getApplicationContext());
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

                matches.sort(Comparator.comparingInt(Match::getMatchNumber));
                Log.e(TAG, "" + matches.get(0).getMatchNumber());
                Log.e(TAG, "" + matches.get(1).getMatchNumber());


                for( Match match : matches ){
                    MatchScheduleViewModel matchScheduleViewModel = new MatchScheduleViewModel();

                    //String mn = match.getMatchName();
                    matchScheduleViewModel.setMatchName(  String.format("%3d",match.getMatchNumber() ));// match.getMatchName() );

                    int red1Average = getAverageByTeam(match.getRed1TeamKey());
                    matchScheduleViewModel.setRed1(match.getRed1TeamKey());
                    matchScheduleViewModel.setRed1Average(red1Average);
                    matchScheduleViewModel.setRed1Auto( getAverageAutoPointsByTeam(match.getRed1TeamKey()) );
                    matchScheduleViewModel.setRed1TeleOp( getAverageTeleOpPointsByTeam(match.getRed1TeamKey()) );
                    matchScheduleViewModel.setRed1End( getAverageEndPointsByTeam(match.getRed1TeamKey()) );
                    matchScheduleViewModel.setRed1StdDeviation(getStdDeviationByTeam(match.getRed1TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getRed1TeamKey())));

                    int red2Average = getAverageByTeam(match.getRed2TeamKey());
                    matchScheduleViewModel.setRed2(match.getRed2TeamKey());
                    matchScheduleViewModel.setRed2Average(red2Average);
                    matchScheduleViewModel.setRed2Auto( getAverageAutoPointsByTeam(match.getRed2TeamKey()) );
                    matchScheduleViewModel.setRed2TeleOp( getAverageTeleOpPointsByTeam(match.getRed2TeamKey()) );
                    matchScheduleViewModel.setRed2End( getAverageEndPointsByTeam(match.getRed2TeamKey()) );
                    matchScheduleViewModel.setRed2StdDeviation(getStdDeviationByTeam(match.getRed2TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getRed2TeamKey())));

                    int blue1Average = getAverageByTeam(match.getBlue1TeamKey());
                    matchScheduleViewModel.setBlue1(match.getBlue1TeamKey());
                    matchScheduleViewModel.setBlue1Average(blue1Average);
                    matchScheduleViewModel.setBlue1Auto( getAverageAutoPointsByTeam(match.getBlue1TeamKey()) );
                    matchScheduleViewModel.setBlue1TeleOp( getAverageTeleOpPointsByTeam(match.getBlue1TeamKey()) );
                    matchScheduleViewModel.setBlue1End( getAverageEndPointsByTeam(match.getBlue1TeamKey()) );
                    matchScheduleViewModel.setBlue1StdDeviation(getStdDeviationByTeam(match.getBlue1TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getBlue1TeamKey())));

                    int blue2Average = getAverageByTeam(match.getBlue2TeamKey());
                    matchScheduleViewModel.setBlue2(match.getBlue2TeamKey());
                    matchScheduleViewModel.setBlue2Average(blue2Average);
                    matchScheduleViewModel.setBlue2Auto( getAverageAutoPointsByTeam(match.getBlue2TeamKey()) );
                    matchScheduleViewModel.setBlue2TeleOp( getAverageTeleOpPointsByTeam(match.getBlue2TeamKey()) );
                    matchScheduleViewModel.setBlue2End( getAverageEndPointsByTeam(match.getBlue2TeamKey()) );
                    matchScheduleViewModel.setBlue2StdDeviation(getStdDeviationByTeam(match.getBlue2TeamKey()));
                    matchScheduleViewModel.setMatchCount(Math.max(matchScheduleViewModel.getMatchCount(),
                            getSize(match.getBlue2TeamKey())));

                    viewModels.add(matchScheduleViewModel);
                }

                matchListAdapter.setMatches(viewModels);
            });

        });
    }

    private int getSize(String teamKey){
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if( teamMatchResults ==null) return 0;
        return teamMatchResults.size();
    }

    private int getAverageAutoPointsByTeam(String teamKey) {
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if (teamMatchResults == null || teamMatchResults.size() == 0) return 0;

        int totalPoints = 0;
        for (MatchResult matchResult : teamMatchResults) {
            CurrentGame currentGamePoints = new CurrentGame(matchResult);
            totalPoints += currentGamePoints.getAutoPoints();
        }
        int averagePoints = totalPoints / teamMatchResults.size();
        return averagePoints;
    }

    private int getAverageTeleOpPointsByTeam(String teamKey){
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if( teamMatchResults == null || teamMatchResults.size() == 0 ) return 0;

        int totalPoints = 0;
        for(MatchResult matchResult : teamMatchResults ){
            CurrentGame currentGamePoints = new CurrentGame(matchResult);
            totalPoints += currentGamePoints.getTeleOpPoints();
        }
        int averagePoints = totalPoints / teamMatchResults.size();
        return averagePoints;
    }

    private int getAverageEndPointsByTeam(String teamKey){
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if( teamMatchResults == null || teamMatchResults.size() == 0 ) return 0;

        int totalPoints = 0;
        for(MatchResult matchResult : teamMatchResults ){
            CurrentGame currentGamePoints = new CurrentGame(matchResult);
            totalPoints += currentGamePoints.getEndPoints();
        }
        int averagePoints = totalPoints / teamMatchResults.size();
        return averagePoints;
    }


    private int getAverageByTeam( String teamKey ){
        List<MatchResult> teamMatchResults = matchResultsByTeam.get(teamKey);
        if( teamMatchResults == null || teamMatchResults.size() == 0 ) return 0;

        int totalScore = 0;
        for( MatchResult matchResult : teamMatchResults ){
            CurrentGame points = new CurrentGame(matchResult);

            int matchScore = 0;
            matchScore += points.getAutoPoints();
            matchScore += points.getTeleOpPoints();
            matchScore += points.getEndPoints();

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
            CurrentGame currentGamePoints = new CurrentGame(matchResult);

            int matchScore = 0;
            matchScore += currentGamePoints.getAutoPoints();
            matchScore += currentGamePoints.getTeleOpPoints();
            matchScore += currentGamePoints.getEndPoints();

            totalScore += matchScore;
        }

        double averageScore = (double)totalScore / teamMatchResults.size();

        int totalDeviation = 0;
        for( MatchResult matchResult : teamMatchResults ){
            CurrentGame currentGamePoints = new CurrentGame(matchResult);
            int matchScore = 0;
            matchScore += currentGamePoints.getAutoPoints();
            matchScore += currentGamePoints.getTeleOpPoints();
            matchScore += currentGamePoints.getEndPoints();


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
        private MaterialTextView blue1;
        private MaterialTextView blue2;
        private MaterialTextView redPrediction;
        private MaterialTextView bluePrediction;
        private MaterialTextView redAutoPrediction;
        private MaterialTextView blueAutoPrediction;
        private MaterialTextView redTeleOpPrediction;
        private MaterialTextView blueTeleOpPrediction;
        private MaterialTextView redEndPrediction;
        private MaterialTextView blueEndPrediction;

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
            blue1 = itemView.findViewById(R.id.blue1);
            blue2 = itemView.findViewById(R.id.blue2);
            redPrediction = itemView.findViewById(R.id.red_prediction);
            bluePrediction = itemView.findViewById(R.id.blue_prediction);
            redAutoPrediction = itemView.findViewById(R.id.red_auto_points);
            blueAutoPrediction = itemView.findViewById(R.id.blue_auto_points);
            redTeleOpPrediction = itemView.findViewById(R.id.red_teleOp_points);
            blueTeleOpPrediction = itemView.findViewById(R.id.blue_teleOp_points);
            redEndPrediction = itemView.findViewById(R.id.red_end_points);
            blueEndPrediction = itemView.findViewById(R.id.blue_end_points);

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

            matchNumber.setText(String.valueOf(matchScheduleViewModel.getMatchName()));
            red1.setText("1: " + matchScheduleViewModel.getRed1());
            red2.setText("2: " + matchScheduleViewModel.getRed2());
            blue1.setText("1: " + matchScheduleViewModel.getBlue1());
            blue2.setText("2: " + matchScheduleViewModel.getBlue2());
            redPrediction.setText( String.valueOf( matchScheduleViewModel.getRedTotal() ) );
            bluePrediction.setText( String.valueOf( matchScheduleViewModel.getBlueTotal() ) );

            redConfidenceIntervalMin.setText(String.valueOf(matchScheduleViewModel.getRedConfidenceIntervalMin()));
            blueConfidenceIntervalMin.setText(String.valueOf(matchScheduleViewModel.getBlueConfidenceIntervalMin()));
            redConfidenceIntervalMax.setText(String.valueOf(matchScheduleViewModel.getRedConfidenceIntervalMax()));
            blueConfidenceIntervalMax.setText(String.valueOf(matchScheduleViewModel.getBlueConfidenceIntervalMax()));
            redPercentage.setText(matchScheduleViewModel.getRedPercentage() + "%");
            bluePercentage.setText(matchScheduleViewModel.getBluePercentage() + "%");

            redAutoPrediction.setText( String.valueOf(matchScheduleViewModel.getRedAutoTotal()));
            blueAutoPrediction.setText( String.valueOf(matchScheduleViewModel.getBlueAutoTotal()));
            redTeleOpPrediction.setText( String.valueOf(matchScheduleViewModel.getRedTeleOpTotal()));
            blueTeleOpPrediction.setText( String.valueOf(matchScheduleViewModel.getBlueTeleOpTotal()));
            redEndPrediction.setText( String.valueOf(matchScheduleViewModel.getRedEndTotal()));
            blueEndPrediction.setText( String.valueOf(matchScheduleViewModel.getBlueEndTotal()));

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
            String teamKeyFilter = filter;
            holderViewModels = vms.stream()
                    .sorted(Comparator.comparing(MatchScheduleViewModel::getMatchName))
                    .filter( m -> StringUtils.isBlank(filter)
                            || m.getRed1().equals(teamKeyFilter)
                            || m.getRed2().equals(teamKeyFilter)
                            || m.getBlue1().equals(teamKeyFilter)
                            || m.getBlue2().equals(teamKeyFilter)
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
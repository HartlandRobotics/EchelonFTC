package org.hartlandrobotics.echelonFTC;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.database.currentGame.CurrentGame;
import org.hartlandrobotics.echelonFTC.database.entities.Match;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.database.entities.MatchScore;
import org.hartlandrobotics.echelonFTC.database.entities.Opr;
import org.hartlandrobotics.echelonFTC.database.repositories.MatchRepo;
import org.hartlandrobotics.echelonFTC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFTC.database.repositories.MatchScoreRepo;
import org.hartlandrobotics.echelonFTC.database.repositories.OprRepo;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccuracyActivity extends EchelonActivity{
    String TAG = "AccountabilityActivity";
    MatchRepo matchRepo;
    MatchResultRepo matchResultRepo;
    MatchScoreRepo matchScoreRepo;
    OprRepo oprRepo;

    List<AccuracyViewModel> viewModels = new ArrayList<>();
    List<MatchResult> allMatchResults;
    List<MatchScore> allMatchScores;
    List<Opr> allOpr;

    RecyclerView accuracyRecycler;
    AccuracyListAdapter accuracyListAdapter;
    TextInputLayout calculationType;
    AutoCompleteTextView calculationTypeAutoComplete;
    String defaultCalculationTypes;
    TextInputLayout inaccuracyThreshold;

    MaterialButton calculate;

    public static void launch(Context context) {
        Intent intent = new Intent(context, AccuracyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuracy);

        setupToolbar("Accuracy");

        FtcApiStatus status = new FtcApiStatus(getApplicationContext());
        String currentEvent = status.getEventKey();

        calculationType = findViewById(R.id.calculationType);
        calculationTypeAutoComplete = findViewById(R.id.calculationTypeAutoComplete);
        String[] calculationTypes = getResources().getStringArray(R.array.calculation_type);
        defaultCalculationTypes = calculationTypes[0];
        ArrayAdapter<String> adapterCalculationType = new ArrayAdapter<String>(this, R.layout.dropdown_item, calculationTypes);
        calculationTypeAutoComplete.setAdapter(adapterCalculationType);
        calculationTypeAutoComplete.setText(defaultCalculationTypes, false);
        inaccuracyThreshold = findViewById(R.id.calculationThreshold);

        calculate = findViewById(R.id.calculateButton);
        calculate.setOnClickListener(this::CalculateOnClick);

        String[] allianceColors = {"red", "blue"};
        if (!viewModels.isEmpty()) viewModels.clear();

        accuracyListAdapter = new AccuracyListAdapter(this);
        accuracyRecycler = findViewById(R.id.accuracy_recycler);
        accuracyRecycler.setLayoutManager(new LinearLayoutManager(this));
        accuracyRecycler.setAdapter(accuracyListAdapter);
        accuracyRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        matchRepo = new MatchRepo(getApplication());
        matchScoreRepo = new MatchScoreRepo(getApplication());
        oprRepo = new OprRepo(getApplication());
        matchResultRepo = new MatchResultRepo(getApplication());

        matchRepo.getMatches().observe(this, matches -> {
            matchScoreRepo.getMatchScoresByYearEvent(status.getYear(), status.getEventCode()).observe(this, matchScores -> {
                allMatchScores = matchScores;
                matchResultRepo.getMatchResultsByEvent(currentEvent).observe(this, matchResults -> {
                    allMatchResults = matchResults;
                    int highestMatchNumber = matchResults
                            .stream()
                            .map(mr -> Integer.parseInt(mr.getMatchKey().split("_")[2]) )
                            .max( Comparator.comparingInt(Integer::intValue))
                            .orElse(0)
                            ;

                    oprRepo.getOprs().observe(this, oprs -> {
                        allOpr = oprs;
                        for (Match match : matches) {
                            String currentMatchKey = match.getMatchKey();
                            int currentMatchNumber = match.getMatchNumber();
                            MatchScore matchScore = matchScores.stream()
                                    .filter(ms -> ms.getMatchNumber() == currentMatchNumber)
                                    .collect(Collectors.toList()).get(0);

                            for (String currentAllianceColor : allianceColors) {
                                AccuracyViewModel vm = new AccuracyViewModel();
                                int studentSum = 0;
                                vm.setMatchNumber(currentMatchNumber);
                                if (currentAllianceColor.equals("red")) {
                                    {
                                        String currentTeamKey = match.getRed1TeamKey();
                                        Optional<MatchResult> team1 = matchResults.stream()
                                                .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                                                .filter(mr -> mr.getTeamKey().equals(currentTeamKey))
                                                .filter(mr -> mr.getAlliance().equals("red"))
                                                .findFirst();
                                        if (team1.isPresent()) {
                                            CurrentGame cg = new CurrentGame(team1.get());
                                            studentSum += cg.getTotalPoints();
                                        }


                                        vm.setTablet1Name(currentTeamKey);
                                    }
                                    {
                                        String currentTeamKey = match.getRed2TeamKey();
                                        Optional<MatchResult> team2 = matchResults.stream()
                                                .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                                                .filter(mr -> mr.getTeamKey().equals(currentTeamKey))
                                                .filter(mr -> mr.getAlliance().equals("red"))
                                                .findFirst();
                                        if (team2.isPresent()) {
                                            CurrentGame cg = new CurrentGame(team2.get());
                                            studentSum += cg.getTotalPoints();
                                        }

                                        vm.setTablet2Name(currentTeamKey);
                                    }

                                    vm.setBlueAlliancePoints(matchScore.getRedTotal() - matchScore.getRedPenalty());
                                    vm.setStudentPoints(studentSum);
                                    if( vm.getBlueAlliancePoints() == 0 ){
                                        vm.setPercentInaccuracy(0);
                                    }
                                    else {
                                        vm.setPercentInaccuracy(Math.abs(vm.getBlueAlliancePoints() - vm.getStudentPoints()) * 100 / (double) vm.getBlueAlliancePoints());
                                    }
                                } else if (currentAllianceColor.equals("blue")) {
                                    {
                                        String currentTeamKey = match.getBlue1TeamKey();
                                        Optional<MatchResult> team1 = matchResults.stream()
                                                .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                                                .filter(mr -> mr.getTeamKey().equals(currentTeamKey))
                                                .filter(mr -> mr.getAlliance().equals("blue"))
                                                .findFirst();
                                        if (team1.isPresent()) {
                                            CurrentGame cg = new CurrentGame(team1.get());
                                            studentSum += cg.getTotalPoints();
                                        }

                                        vm.setTablet1Name(currentTeamKey);
                                    }
                                    {
                                        String currentTeamKey = match.getBlue2TeamKey();
                                        Optional<MatchResult> team2 = matchResults.stream()
                                                .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                                                .filter(mr -> mr.getTeamKey().equals(currentTeamKey))
                                                .filter(mr -> mr.getAlliance().equals("blue"))
                                                .findFirst();
                                        if (team2.isPresent()) {
                                            CurrentGame cg = new CurrentGame(team2.get());
                                            studentSum += cg.getTotalPoints();
                                        }

                                        vm.setTablet2Name(currentTeamKey);
                                    }
                                    vm.setBlueAlliancePoints(matchScore.getBlueTotal() - matchScore.getBluePenalty());
                                    vm.setStudentPoints(studentSum);
                                    if( vm.getBlueAlliancePoints() == 0 ){
                                        vm.setPercentInaccuracy(0);
                                    }
                                    else {
                                        vm.setPercentInaccuracy(Math.abs(vm.getBlueAlliancePoints() - vm.getStudentPoints()) * 100 / (double) vm.getBlueAlliancePoints());
                                    }
                                }

                                vm.setAllianceColor(currentAllianceColor);

                                if( highestMatchNumber >= vm.getMatchNumber() )  {
                                    viewModels.add(vm);
                                }
                            }
                        }
                        accuracyListAdapter.setAccuracies(viewModels);

                    });

                });
            });
        });
    }

    public void CalculateOnClick(View view) {
        FtcApiStatus status = new FtcApiStatus(getApplicationContext());
        String eventKey = status.getEventKey();
        boolean studentScore = calculationTypeAutoComplete.getText().toString().equals("StudentScore");
        double threshold = Double.parseDouble(inaccuracyThreshold.getEditText().getText().toString());

        for (MatchResult mr : allMatchResults) {
            if (studentScore) {
                mr.setContribution(0);
                matchResultRepo.upsert(mr);
            } else {
                AccuracyViewModel vm = viewModels.stream()
                        .filter(viewModel -> viewModel.getMatchNumber() == MatchKeyToNumber(mr.getMatchKey()))
                        .filter(viewModel -> viewModel.getAllianceColor().equals(mr.getAlliance()))
                        .findFirst().get();

                if( vm.getPercentInaccuracy() > threshold ){
                    Log.i(TAG, "> threshold");
                    MatchScore currentMatchScore = allMatchScores.stream().filter(ms -> ms.getMatchNumber() == vm.getMatchNumber())
                            .findFirst()
                            .orElse(null)
                            ;

                    int team1Number = Integer.parseInt(vm.getTablet1Name());
                    Opr team1Opr = allOpr.stream().filter( opr -> opr.getTeamNumber() == team1Number).findFirst().get();
                    double team1Points = team1Opr.getOpr() - team1Opr.getFoul();

                    int team2Number =  Integer.parseInt(vm.getTablet2Name());
                    Opr team2Opr = allOpr.stream().filter( opr -> opr.getTeamNumber() == team2Number).findFirst().get();
                    double team2Points = team1Opr.getOpr() - team2Opr.getFoul();

                    double totalPoints = team1Points + team2Points;

                    double team1Percentage = team1Points/totalPoints;
                    double team2Percentage = team2Points/totalPoints;

                    if(Integer.parseInt(mr.getTeamKey()) == team1Number){
                        mr.setContribution((int)(team1Points * team1Percentage));
                    }
                    else if(Integer.parseInt(mr.getTeamKey()) == team2Number){
                        mr.setContribution((int)(team2Points * team2Percentage));
                    }


                    int currentScore = 0;
                    if( vm.getAllianceColor().equals("red")){
                        currentScore = currentMatchScore.getRedTotal() - currentMatchScore.getRedPenalty();
                    } else if (vm.getAllianceColor().equals("blue")){
                        currentScore = currentMatchScore.getBlueTotal() - currentMatchScore.getBluePenalty();
                    }

                    matchResultRepo.upsert(mr);

                }
            }


        }
        if(viewModels != null ) {
            viewModels.clear();
        }

        Toast.makeText(getApplicationContext(),"Calculated " + allMatchResults.size() + " match results", Toast.LENGTH_LONG).show();

    }

    public int MatchKeyToNumber(String matchKey) {
        if(matchKey == null) return 0;
        //Log.i(TAG,matchKey);
        if(matchKey.length() <= 1) return 0;

        //String matchValueStr = StringUtils.defaultIfBlank(matchKey, "2025_qm0" );
        //Log.i(TAG, String.valueOf(matchKey.length()));
        String matchNumberStr = matchKey.split("_")[2];
        return Integer.parseInt(matchNumberStr);
    }

    public class AccuracyViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView matchNumberText;
        private MaterialTextView blueAllianceScoreText;
        private MaterialTextView studentScoreText;
        private MaterialTextView inaccuracyPercentText;
        private MaterialTextView team1Text;
        private MaterialTextView team2Text;

        private AccuracyViewModel accuracyViewModel;

        AccuracyViewHolder(View itemView) {
            super(itemView);

            matchNumberText = itemView.findViewById(R.id.match_number);
            blueAllianceScoreText = itemView.findViewById(R.id.blue_alliance_score);
            studentScoreText = itemView.findViewById(R.id.student_score);
            inaccuracyPercentText = itemView.findViewById(R.id.match_percent_differance);
            team1Text = itemView.findViewById(R.id.team1);
            team2Text = itemView.findViewById(R.id.team2);
        }

        public void setMatch(AccuracyViewModel vm) {
            this.accuracyViewModel = vm;

            matchNumberText.setText(String.valueOf(vm.getMatchNumber()));

            int color = Color.GRAY;
            if (vm.getAllianceColor().equals("red")) {
                color = getColor(R.color.redAlliance);

            }
            if (vm.getAllianceColor().equals("blue")) {
                color = getColor(R.color.blueAlliance);
            }
            blueAllianceScoreText.setTextColor(color);
            blueAllianceScoreText.setText(String.valueOf(vm.getBlueAlliancePoints()));

            studentScoreText.setTextColor(color);
            studentScoreText.setText(String.valueOf(vm.getStudentPoints()));

            String text = String.format(Locale.US, "%.2f %%",vm.getPercentInaccuracy());
            inaccuracyPercentText.setText(text);

            team1Text.setTextColor(color);
            team1Text.setText(vm.getTablet1Name());

            team2Text.setTextColor(color);
            team2Text.setText(vm.getTablet2Name());
        }

        public void setDisplayText(String displayText) {
            matchNumberText.setText(displayText);
        }
    }

    public class AccuracyListAdapter extends RecyclerView.Adapter<AccuracyViewHolder> {
        private final LayoutInflater inflater;
        private List<AccuracyViewModel> allHolderViewModels;
        private List<AccuracyViewModel> holderViewModels;
        private String teamFilter = StringUtils.EMPTY;

        AccuracyListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public AccuracyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_accuracy, parent, false);
            return new AccuracyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AccuracyViewHolder holder, int position) {
            if (holderViewModels != null) {
                holder.setMatch(holderViewModels.get(position));
            } else {
                holder.setDisplayText("No Match Data Yet...");
            }
        }

        void setAccuracies(List<AccuracyViewModel> vms) {
            allHolderViewModels = vms;

            holderViewModels = vms.stream()
                    .sorted(Comparator.comparingDouble(AccuracyViewModel::getPercentInaccuracy).reversed())
                    .collect(Collectors.toList());


            //            viewModels.stream().sorted(Comparator.comparingDouble(AccountabilityViewModel::getPercentInaccuracy).reversed())
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return holderViewModels == null ? 0 : holderViewModels.size();
        }
    }



}

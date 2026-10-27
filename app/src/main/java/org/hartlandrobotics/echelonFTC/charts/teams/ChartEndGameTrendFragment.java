package org.hartlandrobotics.echelonFTC.charts.teams;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.charts.TeamData;
import org.hartlandrobotics.echelonFTC.charts.events.EventChartsActivity;
import org.hartlandrobotics.echelonFTC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;
import org.hartlandrobotics.echelonFTC.utilities.TeamDataUtilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ChartEndGameTrendFragment extends Fragment {

    private static final String TAG = "ChartEndGameTrendFragment";
    private LineChart endGameTrendChart;
    private AutoCompleteTextView teamNumberAutoComplete;

    List<TeamData> allTeamData;
    List<Integer> sortedTeamNumbers;


    public ChartEndGameTrendFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_end_game_trend, container, false);

        Application application = this.requireActivity().getApplication();
        FtcApiStatus apiStatus = new FtcApiStatus(application);
        String currentEvent = apiStatus.getEventKey();

        MatchResultRepo matchResultRepo = new MatchResultRepo(application);
        matchResultRepo.getMatchResultsByEvent(currentEvent).observe(getViewLifecycleOwner(), matchResults -> {
            allTeamData = TeamDataUtilities.toTeamDataList(application, matchResults);

            sortedTeamNumbers = allTeamData.stream()
                    .map(TeamData::getTeamNumber)
                    .sorted()
                    .collect(Collectors.toList());

            setupDropDown();
            setupChartData();
        });

        endGameTrendChart = view.findViewById(R.id.endgame_line_chart);
        setupChart();

        teamNumberAutoComplete = view.findViewById(R.id.teamSelectionAutoComplete);
        teamNumberAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                apiStatus.setCurrentTeam(sortedTeamNumbers.get(position).toString());
                setupChartData();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
        setupDropDown();
        setupChartData();
        endGameTrendChart.invalidate();
    }

    public void setupDropDown(){
        if( sortedTeamNumbers == null ){
            Log.i(TAG, "No teams.");
            return;
        }
        Log.i(TAG, "setupDropDown with " + sortedTeamNumbers.size() + " teams");
        ArrayAdapter<Integer> teamNumberAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, sortedTeamNumbers);
        teamNumberAutoComplete.setAdapter(teamNumberAdapter);

        FtcApiStatus apiStatus = new FtcApiStatus(requireActivity());
        String currentTeam = apiStatus.getCurrentTeam();

        if( StringUtils.isNotBlank(currentTeam) ){
            teamNumberAutoComplete.setText(currentTeam, false);
        }
    }

    public void setupChart(){
        endGameTrendChart.setTouchEnabled(false);
        endGameTrendChart.setPinchZoom(false);
        endGameTrendChart.getDescription().setEnabled(false);

        XAxis xAxis = endGameTrendChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);



        YAxis yAxis = endGameTrendChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);

        endGameTrendChart.getAxisRight().setDrawAxisLine(false);
    }

    public void setupChartData(){
        FtcApiStatus apiStatus = new FtcApiStatus(requireActivity());
        String currentTeam = apiStatus.getCurrentTeam();

        if(StringUtils.isBlank(currentTeam)) return;
        if( allTeamData == null ) return;

        TeamData teamData = allTeamData.stream()
                .filter(teamData1 -> Objects.equals(teamData1.getTeamNumber(), Integer.parseInt(currentTeam)))
                .findFirst()
                .orElse(null);

        if( teamData == null ) return;

        ArrayList<Entry> entries = new ArrayList<>();

        List<Integer> matchNumbers = teamData.getEndGameScores().keySet().stream().sorted().collect(Collectors.toList());

        for( Integer matchNumber : matchNumbers ){
            entries.add( new Entry(matchNumber, teamData.getEndGameScores().get(matchNumber)));
        }

        LineDataSet set1;
        if (endGameTrendChart.getData() != null &&
                endGameTrendChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) endGameTrendChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            endGameTrendChart.getData().notifyDataChanged();
            endGameTrendChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(entries, "End Game Scores");
            set1.setDrawCircles(true);
            set1.enableDashedLine(10f, 0f, 0f);
            set1.enableDashedHighlightLine(10f, 0f, 0f);
            set1.setColor(Color.parseColor("#444444") );
            set1.setCircleColor(Color.parseColor("#000000"));
            set1.setLineWidth(2f);//line size
            set1.setCircleRadius(5f);
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(10f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(5f);
            //set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(5.f);

            set1.setFillColor(Color.WHITE);

            set1.setDrawValues(true);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);

            endGameTrendChart.setData(data);
        }

        endGameTrendChart.invalidate();
    }
}
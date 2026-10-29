package org.hartlandrobotics.echelonFTC.charts.teams;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.charts.TeamData;
import org.hartlandrobotics.echelonFTC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;
import org.hartlandrobotics.echelonFTC.utilities.TeamDataUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class ChartScoringElementsTrendFragment extends Fragment {
    private static final String TAG = "ChartScoringElementsTrendFragment";

    private LineChart scoringElementsTrendChart;
    private AutoCompleteTextView teamNumberAutoComplete;

    List<TeamData> allTeamData;
    List<Integer> sortedTeamNumbers;

    public ChartScoringElementsTrendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_scoring_elements_trend, container, false);

        Application application = this.requireActivity().getApplication();
        final FtcApiStatus apiStatus = new FtcApiStatus(application);
        String currentEvent = apiStatus.getEventKey();

        MatchResultRepo matchResultRepo = new MatchResultRepo(application);
        matchResultRepo.getMatchResultsByEvent(currentEvent).observe(getViewLifecycleOwner(), matchResults -> {
            allTeamData = TeamDataUtilities.toTeamDataList(matchResults);

            sortedTeamNumbers = allTeamData.stream()
                    .map(TeamData::getTeamNumber)
                    .sorted()
                    .collect(Collectors.toList());

            setupDropDown();
            setupChartData();

        });

        scoringElementsTrendChart = view.findViewById(R.id.scoring_elements_line_chart);
        setupChart();

        teamNumberAutoComplete = view.findViewById(R.id.teamSelectionAutoComplete);
        teamNumberAutoComplete.setOnItemClickListener((parent, view1, position, id) -> {
            apiStatus.setCurrentTeam(sortedTeamNumbers.get(position).toString());
            setupChartData();
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
        scoringElementsTrendChart.invalidate();
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
        scoringElementsTrendChart.setTouchEnabled(false);
        scoringElementsTrendChart.setPinchZoom(false);
        scoringElementsTrendChart.getDescription().setEnabled(false);

        XAxis xAxis = scoringElementsTrendChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);

        YAxis yAxis = scoringElementsTrendChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);

        scoringElementsTrendChart.getAxisRight().setDrawAxisLine(false);
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

        List<Integer> matchNumbers = teamData.getAutoScores().keySet().stream().sorted().collect(Collectors.toList());

        for( Integer matchNumber : matchNumbers ){
            entries.add( new Entry(matchNumber, teamData.getScoringElementCounts().get(matchNumber)));
        }

        LineDataSet set1;
        if (scoringElementsTrendChart.getData() != null &&
                scoringElementsTrendChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) scoringElementsTrendChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            scoringElementsTrendChart.getData().notifyDataChanged();
            scoringElementsTrendChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(entries, "Auto Scores");
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

            scoringElementsTrendChart.setData(data);
        }

        scoringElementsTrendChart.invalidate();
    }

}

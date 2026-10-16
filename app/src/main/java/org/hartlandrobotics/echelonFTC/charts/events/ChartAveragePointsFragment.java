package org.hartlandrobotics.echelonFTC.charts.events;

import android.app.Application;
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
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.charts.TeamData;
import org.hartlandrobotics.echelonFTC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;
import org.hartlandrobotics.echelonFTC.utilities.TeamDataUtilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChartAveragePointsFragment extends TeamNumberFragment {
    private static final String TAG = "ChartAggAverageFragment";
    private BarChart totalScoringChart;
    private ListView teamNumberListView;
    private ListNumberAdapter teamListAdapter;
    List<TeamData> allTeamData;

    public ChartAveragePointsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_agg_average, container, false);

        Application application = this.requireActivity().getApplication();
        FtcApiStatus apiStatus = new FtcApiStatus(application);
        String currentEvent = apiStatus.getEventKey();

        long start = System.nanoTime();
        MatchResultRepo matchResultRepo = new MatchResultRepo(application);
        matchResultRepo.getMatchResultsByEvent(currentEvent).observe(getViewLifecycleOwner(), matchResults -> {
            allTeamData = TeamDataUtilities.toTeamDataList(application, matchResults);

            setupFragment();
            drawFragment();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        totalScoringChart = view.findViewById(R.id.agg_average_chart);
        teamNumberListView = view.findViewById(R.id.team_list);

        if (allTeamData != null) {
            setupFragment();
            drawFragment();
        }
    }

    public void setupFragment() {

        if (teamListAdapter == null) {
            teamListAdapter = new ListNumberAdapter(allTeamData);
            teamNumberListView.setAdapter(teamListAdapter);
            teamNumberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(TAG, "onItemClick");
                    Log.i(TAG, "View Item Click: " + view.getClass().getName());
                }
            });
        }

        if (this.allTeamData == null) return;

        List<TeamData> visibleTeamsSortedByPoints = this.allTeamData
                .stream()
                .filter(TeamData::getVisible)
                .sorted(Comparator.comparingInt(TeamData::getTotalPoints).reversed())
                .collect(Collectors.toList());


        XAxis xLabels = totalScoringChart.getXAxis();
        xLabels.setAxisMinimum(0);
        xLabels.setDrawLabels(true);
        xLabels.setLabelCount(visibleTeamsSortedByPoints.size());
        xLabels.setLabelRotationAngle(-90);
        xLabels.setCenterAxisLabels(true);
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setValueFormatter((value, axis) -> {
            int val = Math.min(Math.max(0, (int) Math.floor(value)), visibleTeamsSortedByPoints.size() - 1);
            String label = String.valueOf(visibleTeamsSortedByPoints.get(val).getTeamNumber());
            //Log.e("CHARTLABEL", String.valueOf(val) + "-" +  String.valueOf(label));
            return label;
        });

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int teamIndex = 0; teamIndex < visibleTeamsSortedByPoints.size(); teamIndex++) {
            TeamData aggAverageViewModel = visibleTeamsSortedByPoints.get(teamIndex);
            yVals1.add(new BarEntry(0.5f + teamIndex,
                    new float[]{aggAverageViewModel.getAutoTotalPoints(), aggAverageViewModel.getTeleOpPoints(), aggAverageViewModel.getEndGamePoints(), aggAverageViewModel.getOprPoints()}
            ));
        }

        BarDataSet set1;

        if (totalScoringChart.getData() != null &&
                totalScoringChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) totalScoringChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            totalScoringChart.getData().notifyDataChanged();
            totalScoringChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setDrawIcons(false);
            set1.setColors(getChartColors());
            set1.setStackLabels(new String[]{"Auto", "TeleOp", "EndGame", "Opr"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData barData = new BarData(dataSets);
            barData.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> String.valueOf(value));
            barData.setValueTextColor(Color.WHITE);

            totalScoringChart.setData(barData);
            barData.setDrawValues(false);
        }

        totalScoringChart.setFitBars(true);
        totalScoringChart.invalidate();

    }

    public void notifyActivity() {
        EventChartsActivity eventChartsActivity = (EventChartsActivity) this.requireActivity();

        // need to update the list of invisible teams in system preferences
        String invisibleTemes = allTeamData.stream()
                .filter(teamData -> !teamData.getVisible())
                .map(TeamData::getTeamNumber)
                .map(Object::toString)
                .collect(Collectors.joining("'"));
        FtcApiStatus status = new FtcApiStatus(eventChartsActivity.getApplication());
        status.setInvisibleTeams(invisibleTemes);

        // need to notify the activity of the data change
        eventChartsActivity.teamDataChanged();
    }

    public void drawFragment() {
        if (this.allTeamData == null) return;

        // list of team numbers
        List<TeamData> teamsSortedByNumber = this.allTeamData
                .stream()
                .sorted(Comparator.comparingInt(TeamData::getTeamNumber))
                .collect(Collectors.toList());

        if (teamListAdapter == null) return;

        // team scores
        totalScoringChart.getDescription().setEnabled(false);
        totalScoringChart.setPinchZoom(false);
        totalScoringChart.setTouchEnabled(false);
        totalScoringChart.setDrawGridBackground(false);
        totalScoringChart.setDrawBarShadow(false);

        totalScoringChart.setDrawValueAboveBar(false);
        totalScoringChart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = totalScoringChart.getAxisLeft();
        leftAxis.setValueFormatter((value, axis) -> {
            return String.valueOf(value);
        });
        leftAxis.setAxisMinimum(0f);
        totalScoringChart.getAxisRight().setEnabled(false);

        Legend l = totalScoringChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
    }

    private int[] getChartColors() {
        // have as many colors as stack-values per entry
        int[] colors = new int[4];

        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3);
        colors[3] = Color.BLUE;

        return colors;
    }

}
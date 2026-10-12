package org.hartlandrobotics.echelonFTC.charts;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
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
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.dao.TeamDao;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.database.entities.Team;
import org.hartlandrobotics.echelonFTC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFTC.database.repositories.TeamRepo;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;
import org.hartlandrobotics.echelonFTC.utilities.TeamDataUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartAggAverageFragment extends Fragment {
    private static final String TAG = "ChartAggAverageFragment";
    private BarChart aggScoringChart;
    private ListView teamNumberListView;
    private ListViewItemCheckboxBaseAdapter teamListAdapter;
    List<TeamData> allTeamData;

    public ChartAggAverageFragment() {
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
            this.setupChartData();
            this.setupTeamData();

            long end = System.nanoTime();
            long elapsedNanos = (end - start) / 1_000_000_000;
            Log.i(TAG, elapsedNanos + "ms");
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        aggScoringChart = view.findViewById(R.id.agg_average_chart);
        setupChart();
        setupChartData();
        teamNumberListView = view.findViewById(R.id.team_list);
        setupTeamNumberList();
        setupTeamData();
    }

//    public void setData(List<TeamListViewModel> allTeamNumbers, List<ChartsActivity.TeamDataViewModel> allTeamData) {
//        this.allTeamNumbers = allTeamNumbers;
//        this.allTeamData = allTeamData;
//
//        teamListAdapter.setTeams(allTeamNumbers);
//        teamListAdapter.notifyDataSetChanged();
//
//        setVisibleTeams();
    //       setupChartData();
    //  }

//    public void setVisibleTeams(){
//
    //       Application application = this.getActivity().getApplication();
////
    //      FtcApiStatus apiStatus = new FtcApiStatus(application);
    ////     String currentEvent = apiStatus.getEventKey();
    //   List<String> currentInvisibleTeams = new ArrayList<>(Arrays.asList(StringUtils.defaultIfBlank(apiStatus.getInvisibleTeams(), StringUtils.EMPTY).split(",")));

////        visibleTeamData = allTeamData.stream()
//                .filter( teamData -> !currentInvisibleTeams.contains(String.valueOf(teamData.getTeamNumber())) )
//                .collect(Collectors.toList());
//
//        //need to map a team to team list

    /// /        List<String> visibleTeamNumbers = allTeamNumbers.stream()
    /// /                .filter(TeamListViewModel::getIsSelected)
//                .map(TeamListViewModel::getTeamNumber)
//                .collect(Collectors.toList());
//
//        visibleTeamData = allTeamData.stream()
//                .filter( teamData -> visibleTeamNumbers.contains( String.valueOf(teamData.getTeamNumber()) ))
//                .sorted(Comparator.comparingDouble(ChartsActivity.TeamDataViewModel::getTotalAverage).reversed())
//                .limit(35)
//                .collect(Collectors.toList());
//    }
    public void setupTeamNumberList() {
        teamListAdapter = new ListViewItemCheckboxBaseAdapter(getContext());
        teamNumberListView.setAdapter(teamListAdapter);
        teamNumberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick");
                Log.i(TAG, "View Item Click: " + view.getClass().getName());
            }
        });


    }

    public void setupTeamData() {
        if (this.allTeamData == null) return;

        List<TeamNumberViewModel> teamViewModels = this.allTeamData
                .stream()
                .sorted(Comparator.comparingInt(TeamData::getTeamNumber))
                .map(TeamNumberViewModel::new)
                .collect(Collectors.toList());

        if(teamListAdapter == null) return;
        teamListAdapter.setTeams(teamViewModels);
        teamListAdapter.notifyDataSetChanged();

    }

    public void setupChart() {
        aggScoringChart.getDescription().setEnabled(false);
        aggScoringChart.setPinchZoom(false);
        aggScoringChart.setTouchEnabled(false);
        aggScoringChart.setDrawGridBackground(false);
        aggScoringChart.setDrawBarShadow(false);

        aggScoringChart.setDrawValueAboveBar(false);
        aggScoringChart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = aggScoringChart.getAxisLeft();
        leftAxis.setValueFormatter((value, axis) -> {
            return String.valueOf(value);
        });
        leftAxis.setAxisMinimum(0f);
        aggScoringChart.getAxisRight().setEnabled(false);

        Legend l = aggScoringChart.getLegend();
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

    public void setupChartData() {
        if (this.allTeamData == null) return;

        List<AggAverageViewModel> visibleTeamViewModels = this.allTeamData
                .stream()
                .filter(TeamData::isVisible)
                .sorted(Comparator.comparingInt(TeamData::getTotalPoints).reversed())
                .map(AggAverageViewModel::new)
                .collect(Collectors.toList());


        XAxis xLabels = aggScoringChart.getXAxis();
        xLabels.setAxisMinimum(0);
        xLabels.setDrawLabels(true);
        xLabels.setLabelCount(visibleTeamViewModels.size());
        xLabels.setLabelRotationAngle(-90);
        xLabels.setCenterAxisLabels(true);
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setValueFormatter((value, axis) -> {
            int val = Math.min(Math.max(0, (int) Math.floor(value)), visibleTeamViewModels.size() - 1);
            String label = String.valueOf(visibleTeamViewModels.get(val).getTeamNumber());
            //Log.e("CHARTLABEL", String.valueOf(val) + "-" +  String.valueOf(label));
            return label;
        });

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int teamIndex = 0; teamIndex < visibleTeamViewModels.size(); teamIndex++) {
            AggAverageViewModel aggAverageViewModel = visibleTeamViewModels.get(teamIndex);
            yVals1.add(new BarEntry(0.5f + teamIndex,
                    new float[]{aggAverageViewModel.getAutoAverage(), aggAverageViewModel.getTeleOpAverage(), aggAverageViewModel.getEndGameAverage(), aggAverageViewModel.getOprAverage()}
            ));
        }

        BarDataSet set1;

        if (aggScoringChart.getData() != null &&
                aggScoringChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) aggScoringChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            aggScoringChart.getData().notifyDataChanged();
            aggScoringChart.notifyDataSetChanged();
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

            aggScoringChart.setData(barData);
            barData.setDrawValues(false);
        }

        aggScoringChart.setFitBars(true);
        aggScoringChart.invalidate();
    }

    public class ListViewItemCheckboxBaseAdapter extends BaseAdapter {
        Context context;
        List<TeamNumberViewModel> teamNumberViewModels;

        public ListViewItemCheckboxBaseAdapter(Context context) {
            this.context = context;
        }

        public void setTeams(List<TeamNumberViewModel> teamNumberViewModels) {
            this.teamNumberViewModels = teamNumberViewModels;
        }

        @Override
        public int getCount() {
            if (this.teamNumberViewModels == null) return 0;
            return this.teamNumberViewModels.size();
        }

        @Override
        public TeamNumberViewModel getItem(int index) {
            return this.teamNumberViewModels.get(index);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_item_agg_chart, null, true);

            MaterialTextView teamNumber = rowView.findViewById(R.id.team_number);
            MaterialCheckBox teamSelectedCheckBox = rowView.findViewById(R.id.team_visible);

            TeamNumberViewModel teamListViewModel = teamNumberViewModels.get(position);
            teamNumber.setText(teamListViewModel.getTeamNumber().toString());
            teamSelectedCheckBox.setChecked(teamListViewModel.getIsSelected());
            teamSelectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String teamNumber = buttonView.getText().toString();

                    ViewParent layoutViewParent = buttonView.getParent();
                    ListView listView = (ListView) layoutViewParent.getParent();
                    int position = listView.getPositionForView(buttonView);

                    TeamNumberViewModel teamNumberViewModel = teamNumberViewModels.get(position);
                    Integer tn = teamNumberViewModel.getTeamNumber();
                    teamListViewModel.setIsSelected(isChecked);
                    //teamNumberViewModels.get(position).setIsSelected(isChecked);
                    allTeamData.stream().filter(teamData -> teamData.getTeamNumber().equals(tn) )
                            .forEach(teamData -> teamData.setVisible(isChecked));
                    setupChartData();
                }
            });
            return rowView;
        }

    }
}
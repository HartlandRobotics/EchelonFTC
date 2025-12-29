package org.hartlandrobotics.echelonFTC.charts;

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

import org.hartlandrobotics.echelonFTC.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChartAggAverageFragment extends Fragment {
    private static final String TAG = "ChartAggAverageFragment";
    private BarChart aggScoringChart;
    private ListView teamNumberListView;
    private ListViewItemCheckboxBaseAdapter teamListAdapter;

    private List<TeamListViewModel> allTeamNumbers;
    private List<ChartsActivity.TeamDataViewModel> allTeamData;

    private List<ChartsActivity.TeamDataViewModel> visibleTeamData;

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
        return inflater.inflate(R.layout.fragment_chart_agg_average, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        teamListAdapter = new ListViewItemCheckboxBaseAdapter(getContext());

        ListView teamNumberListView = view.findViewById(R.id.team_list);
        teamNumberListView.setAdapter(teamListAdapter);
        teamNumberListView.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick");
            }
        });

        aggScoringChart = view.findViewById(R.id.agg_average_chart);
        setupChart();
    }

    public void setData(List<TeamListViewModel> allTeamNumbers, List<ChartsActivity.TeamDataViewModel> allTeamData) {
        this.allTeamNumbers = allTeamNumbers;
        this.allTeamData = allTeamData;

        teamListAdapter.setTeams(allTeamNumbers);
        teamListAdapter.notifyDataSetChanged();

        setVisibleTeams();
        setupChartData();
    }

    public void setVisibleTeams(){
        List<String> visibleTeamNumbers = allTeamNumbers.stream()
                .filter(TeamListViewModel::getIsSelected)
                .map(TeamListViewModel::getTeamNumber)
                .collect(Collectors.toList());

        visibleTeamData = allTeamData.stream()
                .filter( teamData -> visibleTeamNumbers.contains( String.valueOf(teamData.getTeamNumber()) ))
                .sorted(Comparator.comparingDouble(ChartsActivity.TeamDataViewModel::getTotalAverage).reversed())
                .limit(35)
                .collect(Collectors.toList());
    }

    public void setupChart(){
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
        int[] colors = new int[3];

        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 3);

        return colors;
    }

    public void setupChartData(){
        XAxis xLabels = aggScoringChart.getXAxis();
        xLabels.setAxisMinimum(0);
        xLabels.setDrawLabels(true);
        xLabels.setLabelCount(visibleTeamData.size());
        xLabels.setLabelRotationAngle(-90);
        xLabels.setCenterAxisLabels(true);
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setValueFormatter((value, axis) -> {
            int val = Math.min( Math.max(0, (int)Math.floor(value) ), visibleTeamData.size()-1);
            String label = String.valueOf( visibleTeamData.get(val).getTeamNumber() );
            Log.e("CHARTLABEL", String.valueOf(val) + "-" +  String.valueOf(label));
            return label;
        });

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for(int teamIndex = 0; teamIndex<visibleTeamData.size(); teamIndex++ ){
            ChartsActivity.TeamDataViewModel teamData = visibleTeamData.get(teamIndex);
            yVals1.add(new BarEntry( 0.5f + teamIndex,
                    new float[]{teamData.getAutoAverage(), teamData.getTeleOpAverage(), teamData.getEndGameAverage()}
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
            set1.setStackLabels(new String[]{"Auto", "TeleOp", "EndGame"});

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
        List<TeamListViewModel> teamViewModels;

        public ListViewItemCheckboxBaseAdapter(Context context) {
            this.context = context;
        }

        public void setTeams(List<TeamListViewModel> teamViewModels){
            this.teamViewModels = teamViewModels;
        }

        @Override
        public int getCount() {
            if( this.teamViewModels == null ) return 0;
            return this.teamViewModels.size();
        }

        @Override
        public TeamListViewModel getItem(int index) {
            return this.teamViewModels.get(index);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=getActivity().getLayoutInflater();
            View rowView=inflater.inflate(R.layout.list_item_agg_chart, null,true);

            MaterialTextView teamNumber = rowView.findViewById(R.id.team_number);
            MaterialCheckBox teamSelectedCheckBox = rowView.findViewById(R.id.team_visible);

            TeamListViewModel teamListViewModel = teamViewModels.get(position);
            teamNumber.setText(teamListViewModel.getTeamNumber());
            teamSelectedCheckBox.setChecked(teamListViewModel.getIsSelected());
            teamSelectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ViewParent layoutViewParent = buttonView.getParent();
                    ListView listView =  (ListView) layoutViewParent.getParent();
                    int position = listView.getPositionForView(buttonView);
                    teamViewModels.get(position).setIsSelected(isChecked);

                    setVisibleTeams();
                    setupChartData();
                }
            });
            return rowView;
        };
    }
}
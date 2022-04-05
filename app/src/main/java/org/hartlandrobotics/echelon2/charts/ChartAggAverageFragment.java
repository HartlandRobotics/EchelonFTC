package org.hartlandrobotics.echelon2.charts;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import org.hartlandrobotics.echelon2.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChartAggAverageFragment extends Fragment {

    private BarChart aggScoringChart;
    private RecyclerView teamNumberRecycler;
    private TeamListAdapter teamListAdapter;

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
        View fragmentView = inflater.inflate(R.layout.fragment_chart_agg_average, container, false);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        teamListAdapter = new TeamListAdapter( getActivity() );

        teamNumberRecycler = view.findViewById(R.id.team_list);
        teamNumberRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        teamNumberRecycler.setAdapter(teamListAdapter);


        aggScoringChart = view.findViewById(R.id.agg_average_chart);
        setupChart();

    }

    public void setData(List<TeamListViewModel> allTeamNumbers, List<ChartsActivity.TeamDataViewModel> allTeamData) {
        teamListAdapter.setTeams(allTeamNumbers);
        teamListAdapter.notifyDataSetChanged();

        List<String> visibleTeamNumbers = allTeamNumbers.stream()
                .filter(TeamListViewModel::getIsSelected)
                .map( teamListViewModel -> teamListViewModel.getTeamNumber() )
                .collect(Collectors.toList());

        visibleTeamData = allTeamData.stream()
                .filter( teamData -> {
                    boolean isVisible = visibleTeamNumbers.contains( String.valueOf(teamData.getTeamNumber()) );
                            return isVisible;
                })
                .sorted(Comparator.comparingDouble(ChartsActivity.TeamDataViewModel::getTotalAverage).reversed())
                .collect(Collectors.toList());

        setupChartData();
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
            float val = value;
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

    public class TeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        MaterialCheckBox teamSelectedCheckBox;
        MaterialTextView teamNumber;

        TeamListViewModel teamViewModel;

        TeamViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            teamNumber = itemView.findViewById(R.id.team_number);
            teamSelectedCheckBox = itemView.findViewById(R.id.team_visible);
        }

        public void setTeam(TeamListViewModel teamViewModel){
            this.teamViewModel = teamViewModel;

            teamNumber.setText(teamViewModel.getTeamNumber());
            teamSelectedCheckBox.setChecked(teamViewModel.getIsSelected());
        }

        public void onClick(View view){
            // update teamViewModel with selected state
            // set team selection back to adapter
            //teamListAdapter.setTeamSelectionChange(teamViewModel);
        }

    }

    public class TeamListAdapter extends RecyclerView.Adapter<TeamViewHolder>{
        private final LayoutInflater inflater;
        List<TeamListViewModel> teamViewModels;

        public void setTeams(List<TeamListViewModel> teamViewModels){
            this.teamViewModels = teamViewModels;
        }

        TeamListAdapter(Context context){ inflater = LayoutInflater.from(context); }

        @NonNull
        @Override
        public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View itemView = inflater.inflate(R.layout.list_item_agg_chart, parent, false);
            return new TeamViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull TeamViewHolder holder, int position){
            if( teamViewModels != null){
                holder.setTeam(teamViewModels.get(position));
            }
        }

        @Override
        public int getItemCount(){
            return teamViewModels == null ? 0 : teamViewModels.size();
        }
    }
}
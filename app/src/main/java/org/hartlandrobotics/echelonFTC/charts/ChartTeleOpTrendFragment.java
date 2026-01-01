package org.hartlandrobotics.echelonFTC.charts;

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

import org.hartlandrobotics.echelonFTC.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ChartTeleOpTrendFragment extends Fragment {

    private LineChart teleOpTrendChart;
    private AutoCompleteTextView teamNumberAutoComplete;
    private String teamNumber;

    private List<String> sortedTeamNumbers;
    ChartsActivity.TeamDataViewModel teamData;

    public ChartTeleOpTrendFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_tele_op_trend, container, false);

        teleOpTrendChart = view.findViewById(R.id.teleop_line_chart);
        setupChart();

        teamNumberAutoComplete = view.findViewById(R.id.teamSelectionAutoComplete);
        teamNumberAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ChartTeleOpTrendFragment", "item clicked");
                String teamNumber = sortedTeamNumbers.get(position);
                teamData = ((ChartsActivity) getActivity()).getTeamData(teamNumber);

                setupChartData();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        setData(((ChartsActivity)getActivity()).getAllTeamNumbers());

        setupDropDown();
    }

    public void setData(List<TeamListViewModel> allTeamNumbers) {
        sortedTeamNumbers = allTeamNumbers.stream()
                .sorted(Comparator.comparingInt(TeamListViewModel::getTeamInteger))
                .map(TeamListViewModel::getTeamNumber)
                .collect(Collectors.toList());
    }

    public void setupDropDown(){
        if( sortedTeamNumbers == null ){
            Log.i("ChartTeleOpTrendFragment", "No teams.");
            return;
        }
        Log.i("ChartTeleOpTrendFragment", "setupDropDown with " + sortedTeamNumbers.size() + " teams");
        ArrayAdapter teamNumberAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_item, sortedTeamNumbers);
        teamNumberAutoComplete.setAdapter(teamNumberAdapter);
    }

    public void setupChart(){
        teleOpTrendChart.setTouchEnabled(false);
        teleOpTrendChart.setPinchZoom(false);
        teleOpTrendChart.getDescription().setEnabled(false);

        XAxis xAxis = teleOpTrendChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);

        YAxis yAxis = teleOpTrendChart.getAxisLeft();
        yAxis.setAxisMinimum(0);
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);

        teleOpTrendChart.getAxisRight().setDrawAxisLine(false);
    }

    public void setupChartData(){
        if( teamData == null ) return;

        ArrayList<Entry> entries = new ArrayList<>();

        List<Integer> matchNumbers = teamData.getTeleOpScores().keySet().stream().sorted().collect(Collectors.toList());

        for( Integer matchNumber : matchNumbers ){
            entries.add( new Entry(matchNumber, teamData.getTeleOpScores().get(matchNumber)));
        }

        LineDataSet set1;
        if (teleOpTrendChart.getData() != null &&
                teleOpTrendChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) teleOpTrendChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            teleOpTrendChart.getData().notifyDataChanged();
            teleOpTrendChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(entries, "TeleOp Scores");
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

            teleOpTrendChart.setData(data);
        }

        teleOpTrendChart.invalidate();
    }
}
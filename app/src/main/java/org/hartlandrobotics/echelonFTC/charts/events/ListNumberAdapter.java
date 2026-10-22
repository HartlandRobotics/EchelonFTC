package org.hartlandrobotics.echelonFTC.charts.events;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.charts.TeamData;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import kotlin.TuplesKt;

public class ListNumberAdapter extends BaseAdapter {

    private static class ViewHolder {
        final MaterialTextView teamNumber;
        final MaterialCheckBox teamSelectedCheckBox;

        ViewHolder(View row) {
            teamNumber = row.findViewById(R.id.team_number);
            teamSelectedCheckBox = row.findViewById(R.id.team_visible);
        }
    }

    private final List<TeamData> teamDataByNumber;

    public ListNumberAdapter(List<TeamData> teamData) {
        this.teamDataByNumber = teamData.stream()
                .sorted(Comparator.comparingInt(TeamData::getTeamNumber))
                .collect(Collectors.toList());
    }

    @Override
    public int getCount() {
        return teamDataByNumber.size();
    }

    @Override
    public TeamData getItem(int position) {
        return teamDataByNumber.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_team_number_visibility, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TeamData team = getItem(position);

        // Bind the current item's values here; these lines run each time
        // this recycled row is used.
        holder.teamNumber.setText(String.valueOf(team.getTeamNumber()));

        // Replace with your actual TeamData getter for the checkbox state.
        boolean isChecked = team.getVisible();///* team.isVisible() */ false;

        // Clear the old listener before setting state, so recycling a row
        // doesn't trigger a change for the previous item.
        holder.teamSelectedCheckBox.setOnCheckedChangeListener(null);
        holder.teamSelectedCheckBox.setChecked(isChecked);
        holder.teamSelectedCheckBox.setOnCheckedChangeListener((button, checked) -> {
            Context context = button.getContext();
            FtcApiStatus status = new FtcApiStatus(context);
            EventChartsActivity eventChartsActivity = (EventChartsActivity) context;
            team.setVisible(checked);

            // Update this TeamData item using your actual setter, for example:
            // team.setVisible(checked);            String invisibleTeams = teamDataByNumber.stream()
            //                    .filter( team -> !team.getVisible())
            //                    .map(TeamData::getTeamNumber)
            //                    .map(String::valueOf)
            //                    .collect(Collectors.joining(","));
            String invisibleTeams = teamDataByNumber.stream()
                    .filter( t -> !t.getVisible())
                    .map(TeamData::getTeamNumber)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            status.setInvisibleTeams(invisibleTeams);
            Log.i("ListNumberAdapter", "invisible teams: " + invisibleTeams);

            eventChartsActivity.drawFragments();
        });

        return convertView;



        //if (view == null) {
        //    view = LayoutInflater.from(parent.getContext())
        //            .inflate(R.layout.list_item_team_number_visibility, parent, false);
        //}

        //MaterialTextView teamNumber = view.findViewById(R.id.team_number);
        //MaterialCheckBox teamSelectedCheckBox = view.findViewById(R.id.team_visible);

        //TeamData teamDataItem = getItem(position);
        ////Log.i("ListNumberAdapter", String.valueOf(position));

        //Log.i( "ListNumberAdapter", "checkbox team number - " + teamNumber.getText().toString());
        //Log.i( "ListNumberAdapter", "data item outside loop team number - " + teamDataItem.getTeamNumber());

        //Context context = teamSelectedCheckBox.getContext();
        //FtcApiStatus status = new FtcApiStatus(context);

        //teamNumber.setText(String.valueOf(teamDataItem.getTeamNumber()));
        //teamSelectedCheckBox.setChecked(teamDataItem.getVisible());
        //teamSelectedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //teamNumber.getText();
            //Log.i( "ListNumberAdapter", "clicked team number - " + teamNumber.getText());

            //teamDataItem.setVisible(isChecked);


        //});

        //return view;
    }

}


package org.hartlandrobotics.echelonFTC.charts.events;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.charts.TeamData;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;
import org.hartlandrobotics.echelonFTC.utilities.TeamDataUtilities;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        Context context = parent.getContext();
        Set<Integer> invisibleTeamNumbers = TeamDataUtilities.invisibleTeamNumbers(context);
        holder.teamSelectedCheckBox.setChecked( !invisibleTeamNumbers.contains(team.getTeamNumber()) );

        // Clear the old listener before setting state, so recycling a row
        // doesn't trigger a change for the previous item.
        holder.teamSelectedCheckBox.setOnCheckedChangeListener(null);
        holder.teamSelectedCheckBox.setOnCheckedChangeListener((button, checked) -> {

            EventChartsActivity eventChartsActivity = (EventChartsActivity) context;
            Set<Integer> current = TeamDataUtilities.invisibleTeamNumbers(context);
            Integer tn = Integer.parseInt( holder.teamNumber.getText().toString() );

            if(checked){
                current.remove(tn);
            }
            else {
                current.add(tn);
            }

            String invisibleTeams = current.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            FtcApiStatus status= new FtcApiStatus(context);
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


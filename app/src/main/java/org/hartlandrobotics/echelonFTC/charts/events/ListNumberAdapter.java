package org.hartlandrobotics.echelonFTC.charts.events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.charts.TeamData;

import java.util.List;

public class ListNumberAdapter extends BaseAdapter {
    private final List<TeamData> teamData;

    public ListNumberAdapter(List<TeamData> teamData) {
        this.teamData = teamData;
    }

    @Override
    public int getCount() {
        return teamData.size();
    }

    @Override
    public TeamData getItem(int position) {
        return teamData.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_team_number_visibility, parent, false);
        }

        MaterialTextView teamNumber = view.findViewById(R.id.team_number);
        MaterialCheckBox teamSelectedCheckBox = view.findViewById(R.id.team_visible);

        TeamData teamData = getItem(position);

        teamNumber.setText(String.valueOf(teamData.getTeamNumber()));
        teamSelectedCheckBox.setChecked(teamData.getVisible());
        teamSelectedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            teamData.setVisible(isChecked);
        });

        // check and see if the chart can be updated

        return view;
    }

}


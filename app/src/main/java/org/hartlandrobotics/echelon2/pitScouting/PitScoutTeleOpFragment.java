package org.hartlandrobotics.echelon2.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;

public class PitScoutTeleOpFragment extends Fragment {

    RadioGroup doesShootGroup;
    PitScout data;

    public void setData( PitScout data) { this.data = data; }
    public PitScout getData() { return data; }

    public PitScoutTeleOpFragment() {
        // Required empty public constructor
    }

    public static PitScoutTeleOpFragment newInstance(String param1, String param2) {
        PitScoutTeleOpFragment fragment = new PitScoutTeleOpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_tele_op, container, false);

        doesShootGroup = view.findViewById(R.id.doesRobotShoot);
        doesShootGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               boolean canShoot = checkedId == R.id.robotShootYes;
                data.setCanShoot(canShoot);
            }
        });

        return view;
    }
}
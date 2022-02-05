package org.hartlandrobotics.echelon2.TBA.fragments;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class EventsFragment extends Fragment {
    public EventsFragment(){
        // required empty constructor
    }
    //pulling events by district
    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}

    private TextView errorTextDisplay;
    Button eventPull;
}

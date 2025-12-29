package org.hartlandrobotics.echelonFTC.ftcapi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.Evt;
import org.hartlandrobotics.echelonFTC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFTC.ftcapi.ApiActivity;
import org.hartlandrobotics.echelonFTC.ftcapi.models.ApiEvents;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {
    private TextView errorTextDisplay;
    private RecyclerView eventRecycler;
    private EventListAdapter eventListAdapter;

    public EventsFragment() {
        // required empty constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_ftcapi_events, container, false);

        errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);
        errorTextDisplay.setVisibility(View.GONE);

        setupCurrentEvents();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupCurrentEvents();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventListAdapter = new EventListAdapter(getActivity());

        eventRecycler = view.findViewById(R.id.event_recycler);
        eventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventRecycler.setAdapter(eventListAdapter);
        eventRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

    }

    //Bring back later when filter events by year
    public void setupCurrentEvents() {
        Context appContext = getActivity().getApplicationContext();
        FtcApiStatus status = new FtcApiStatus(appContext);

        EventRepo eventRepo = new EventRepo(EventsFragment.this.getActivity().getApplication());
        String currentRegion = status.getRegionKey();
        Log.e("FtcApiEventsFragment", "current Region " + currentRegion);
        eventRepo.getRegionWithEvents(currentRegion).observe(getViewLifecycleOwner(), regionWithEvents -> {
            List<Evt> matchingEvents = regionWithEvents.events;
            Log.e("FtcApiEventsFragment","size: " + matchingEvents.size());
            eventListAdapter.setEvents(matchingEvents);

        });


    }

    public class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialTextView eventName;
        private MaterialTextView eventKey;
        private MaterialTextView eventCode;
        private MaterialRadioButton eventSelectedRadioButton;

        private EventViewModel eventViewModel;

        EventsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            eventName = itemView.findViewById(R.id.event_display_name);
            eventKey = itemView.findViewById(R.id.event_key);
            eventCode = itemView.findViewById(R.id.event_code);
            eventSelectedRadioButton = itemView.findViewById(R.id.event_selected_radiobutton);

            eventSelectedRadioButton.setOnClickListener(v -> {
                eventListAdapter.setCurrentEvent(eventViewModel);
            });
        }

        public void setEvent(EventViewModel eventViewModel) {
            this.eventViewModel = eventViewModel;

            eventName.setText(eventViewModel.getEventName());
            eventKey.setText(eventViewModel.getEventKey());
            eventCode.setText(eventViewModel.getEventCode());
            eventSelectedRadioButton.setChecked(eventViewModel.getIsSelected());
        }

        public void setDisplayText(String displayText) {
            //eventName.setText(displayText);
            eventCode.setText(displayText);
        }

        @Override
        public void onClick(View view) {
            eventListAdapter.setCurrentEvent(eventViewModel);
        }
    }

    public class EventListAdapter extends RecyclerView.Adapter<EventsViewHolder> {


        private final LayoutInflater inflater;
        private List<EventViewModel> eventViewModels;

        EventListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_event, parent, false);
            return new EventsViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
            if (eventViewModels != null) {
                holder.setEvent(eventViewModels.get(position));
            } else {
                holder.setDisplayText("No Event Data Yet...");
            }
        }

        void setEvents(List<Evt> events) {
            Context appContext = getActivity().getApplicationContext();
            FtcApiStatus status = new FtcApiStatus(appContext);
            String currentEventKey = status.getEventKey();

            eventViewModels = new ArrayList<>();
            for (Evt event : events) {
                EventViewModel viewModel = new EventViewModel(event);
                if (event.getEventKey().equals(currentEventKey)) {
                    viewModel.setIsSelected(true);
                    setCurrentEvent(viewModel);
                }
                eventViewModels.add(viewModel);
            }

            notifyDataSetChanged();
        }

        void setCurrentEvent(EventViewModel currentViewModel) {
            ApiActivity ftcApiActivity = (ApiActivity) getActivity();
            ftcApiActivity.setEventKey(currentViewModel.getEventKey());
            ftcApiActivity.setEventCode(currentViewModel.getEventCode());

            for (EventViewModel viewModel : eventViewModels) {
                viewModel.setIsSelected(currentViewModel.getEventKey().equals(viewModel.getEventKey()));
            }

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return eventViewModels == null ? 0 : eventViewModels.size();
        }
    }

}


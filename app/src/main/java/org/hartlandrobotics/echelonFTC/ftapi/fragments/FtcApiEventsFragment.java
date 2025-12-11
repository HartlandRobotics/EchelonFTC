package org.hartlandrobotics.echelonFTC.ftapi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.Evt;
import org.hartlandrobotics.echelonFTC.database.repositories.DistrictRepo;
import org.hartlandrobotics.echelonFTC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFTC.ftapi.FtcApiActivity;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiEvents;
import org.hartlandrobotics.echelonFTC.ftapi.status.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FtcApiEventsFragment extends Fragment {

    private TextView errorTextDisplay;
    private RecyclerView eventRecycler;
    private EventListAdapter eventListAdapter;


    public FtcApiEventsFragment() {
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
        ApiStatus status = new ApiStatus(appContext);

        EventRepo eventRepo = new EventRepo(FtcApiEventsFragment.this.getActivity().getApplication());
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

        private FtcApiEventViewModel eventViewModel;

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

        public void setEvent(FtcApiEventViewModel eventViewModel) {
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
        private List<FtcApiEventViewModel> eventViewModels;

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
            ApiStatus status = new ApiStatus(appContext);
            String currentEventKey = status.getEventKey();

            eventViewModels = new ArrayList<>();
            for (Evt event : events) {
                FtcApiEventViewModel viewModel = new FtcApiEventViewModel(event);
                if (event.getEventKey().equals(currentEventKey)) {
                    viewModel.setIsSelected(true);
                    setCurrentEvent(viewModel);
                }
                eventViewModels.add(viewModel);
            }

            notifyDataSetChanged();
        }

        void setCurrentEvent(FtcApiEventViewModel currentViewModel) {
            FtcApiActivity ftcApiActivity = (FtcApiActivity) getActivity();
            ftcApiActivity.setEventKey(currentViewModel.getEventKey());
            ftcApiActivity.setEventCode(currentViewModel.getEventCode());

            for (FtcApiEventViewModel viewModel : eventViewModels) {
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

/*

public class EventsFragment extends Fragment {




    public void setupEventPulls(){
        eventPull.setOnClickListener((view) -> {
            ApiInterface newApi = Api.getApiClient(getActivity().getApplication());

            try{
                Context appContext = getActivity().getApplicationContext();
                OrangeAllianceStatus status = new OrangeAllianceStatus(appContext);
                String districtKey = status.getDistrictKey();
                String eventKeyOverride = StringUtils.defaultIfEmpty( eventKeyOverrideLayout.getEditText().getText().toString(), StringUtils.EMPTY);

                Map<String, String> map = Stream.of(new String[][] {
                        { "region_key", districtKey },
                        { "season_key", "2526" },
                }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                if( StringUtils.isBlank( eventKeyOverride )) {
                    Call<List<SyncEvent>> newCall = newApi.getEventsByRegion(map);
                    newCall.enqueue(new Callback<List<SyncEvent>>() {
                        @Override
                        public void onResponse(Call<List<SyncEvent>> call, Response<List<SyncEvent>> response) {
                            try {
                                if (!response.isSuccessful()) {
                                    errorTextDisplay.setText("Couldn't pull events");
                                } else {
                                    EventRepo eventRepo = new EventRepo(EventsFragment.this.getActivity().getApplication());
                                    List<SyncEvent> syncEvents = response.body();
                                    List<Evt> events = syncEvents.stream()
                                            .map(event -> event.toEvent())
                                            .collect(Collectors.toList());

                                    eventRepo.upsert(events);

                                    for (Evt event : events) {
                                        RgnEvtCrossRef crossRef = event.toRgnEvent(districtKey);
                                        eventRepo.upsert(crossRef);

                                    }

                                    eventListAdapter.setEvents(events);
                                    errorTextDisplay.setText("Got event: " + syncEvents.size());
                                }
                            } catch (Exception e) {
                                Log.e("Inside onResponse", "\"Exception thrown inside onResponse\"");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SyncEvent>> call, Throwable t) {
                            errorTextDisplay.setText("Error attempting to pull events from TBA");
                        }

                    });
                }else {
                    Call<SyncEvent> newCall = newApi.getEventByKey(eventKeyOverride);
                    newCall.enqueue(new Callback<SyncEvent>() {
                        @Override
                        public void onResponse(Call<SyncEvent> call, Response<SyncEvent> response) {
                            try {
                                if (!response.isSuccessful()) {
                                    errorTextDisplay.setText("Couldn't pull events");
                                } else {
                                    EventRepo eventRepo = new EventRepo(EventsFragment.this.getActivity().getApplication());
                                    SyncEvent syncEvent = response.body();
                                    List<Evt> events = new ArrayList<>();
                                    events.add(syncEvent.toEvent()) ;

                                    eventRepo.upsert(events);

                                    OrangeAllianceStatus status = new OrangeAllianceStatus(appContext);
                                    status.setEventKey(eventKeyOverride);

                                    eventListAdapter.setEvents(events);
                                    errorTextDisplay.setText("Got event: " + events.size());
                                }
                            } catch (Exception e) {
                                Log.e("Inside onResponse", "\"Exception thrown inside onResponse\"");
                            }
                        }

                        @Override
                        public void onFailure(Call<SyncEvent> call, Throwable t) {
                            errorTextDisplay.setText("Error attempting to pull events from TBA");
                        }
                    });
                }
            }
            catch(Exception e){
                Log.e("Outside Onresponse", "Exception attempting to pull from TBA");
            }
        });
    }


}


 */

package org.hartlandrobotics.echelonFTC.orangeAlliance.fragments;

import android.app.Application;
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
import org.hartlandrobotics.echelonFTC.orangeAlliance.Api;
import org.hartlandrobotics.echelonFTC.orangeAlliance.ApiInterface;
import org.hartlandrobotics.echelonFTC.orangeAlliance.ApiActivity;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncEvent;
//import org.hartlandrobotics.echelonFTC.database.entities.RgnEvtCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.Evt;
import org.hartlandrobotics.echelonFTC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFTC.status.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {
    private Button eventPull;
    private TextView errorTextDisplay;
    private TextInputLayout eventKeyOverrideLayout;

    private RecyclerView eventRecycler;
    private EventListAdapter eventListAdapter;

    public EventsFragment(){
        // required empty constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.fragment_events, container, false);

        eventKeyOverrideLayout = fragmentView.findViewById(R.id.event_key_override_layout);
        eventPull = fragmentView.findViewById(R.id.eventPullButton);
        errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);
        errorTextDisplay.setVisibility(View.GONE);

        return fragmentView;
    }

    @Override
    public void onResume(){
        super.onResume();
        setupCurrentEvents();
        setupEventPulls();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        eventListAdapter = new EventListAdapter(getActivity());

        eventRecycler = view.findViewById(R.id.event_recycler);
        eventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventRecycler.setAdapter(eventListAdapter);
        eventRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

    }

    //Bring back later when filter events by year
    public void setupCurrentEvents(){
        Context appContext = getActivity().getApplicationContext();
        ApiStatus status = new ApiStatus(appContext);

        String eventKey = eventKeyOverrideLayout.getEditText().getText().toString();
        EventRepo eventRepo = new EventRepo(EventsFragment.this.getActivity().getApplication());
        if( StringUtils.isBlank(eventKey)) {
            String currentDistrict = status.getRegionCode();
//            eventRepo.getDistrictWithEvents(currentDistrict).observe(getViewLifecycleOwner(), district -> {
//
//                String eventKeyInner = eventKeyOverrideLayout.getEditText().getText().toString();
//                if( !StringUtils.isBlank(eventKeyInner )) {
//                    Optional<Evt> matchingEvent = district.events.stream()
//                            .filter( districtEvent -> districtEvent.getEventCode().equals(eventKeyInner ))
//                            .findFirst();
//                    if(matchingEvent.isPresent()) {
//                        eventListAdapter.setEvents(district.events);
//                    }
//                }
//            });
        }
        else{
            eventRepo.getEvent(eventKey).observe(getViewLifecycleOwner(), event ->{
                int i = 10;
                i++;
                //eventListAdapter.setEvents(List.of);

            });
        }
    }

    public void setupEventPulls(){
        eventPull.setOnClickListener((view) -> {
            Application app= requireActivity().getApplication();
            ApiInterface apiClient = Api.getApiClient(app);

            try{
                Context appContext = requireActivity().getApplicationContext();
                ApiStatus status = new ApiStatus(appContext);
                String regionCode = status.getRegionCode();
                String eventKeyOverride = StringUtils.defaultIfEmpty( eventKeyOverrideLayout.getEditText().getText().toString(), StringUtils.EMPTY);

                if( StringUtils.isBlank( eventKeyOverride )) {
                    Call<SyncEvent> newCall = apiClient.getEventsBySeason(status.getSeason());
                    newCall.enqueue(new Callback<SyncEvent>() {
                        @Override
                        public void onResponse(Call<SyncEvent> call, Response<SyncEvent> response) {
                            try {
                                if (!response.isSuccessful()) {
                                    errorTextDisplay.setText("Couldn't pull events");
                                } else {
                                    EventRepo eventRepo = new EventRepo(EventsFragment.this.getActivity().getApplication());
                                    SyncEvent syncEvents = response.body();
                                    List<Evt> events = syncEvents.events.stream()
                                            .map(event -> event.toEvent())
                                            .filter(evt -> evt.regionCode.equals(regionCode))
                                            .collect(Collectors.toList());

                                    for (Evt event : events) {
                                        //RgnEvtCrossRef crossRef = event.toRgnEvent(districtKey);
                                        //eventRepo.upsert(crossRef);
                                        eventListAdapter.setEvents(events);
                                    }
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
                }else {
                    Call<SyncEvent> newCall = apiClient.getEventsByCode(status.getSeason(),eventKeyOverride);
                    newCall.enqueue(new Callback<SyncEvent>() {
                        @Override
                        public void onResponse(Call<SyncEvent> call, Response<SyncEvent> response) {
                            try {
                                if (!response.isSuccessful()) {
                                    errorTextDisplay.setText("Couldn't pull events");
                                } else {
                                    SyncEvent syncEvent = response.body();
                                    for(SyncEvent.EventProperty event : response.body().events ) {
                                        EventRepo eventRepo = new EventRepo(EventsFragment.this.getActivity().getApplication());
                                        List<Evt> events = new ArrayList<>();
                                        events.add(event.toEvent());

                                        eventRepo.upsert(events);

                                        ApiStatus status = new ApiStatus(appContext);
                                        status.setEventKey(eventKeyOverride);

                                        eventListAdapter.setEvents(events);
                                        errorTextDisplay.setText("Got event: " + events.size());
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Inside onResponse", "\"Exception thrown inside onResponse\"");
                            }
                        }

                        @Override
                        public void onFailure(Call<SyncEvent> call, Throwable t) {
                            errorTextDisplay.setText("Error attempting to pull events from API");
                        }
                    });
                }
            }
            catch(Exception e){
                Log.e("Outside OnResponse", "Exception attempting to pull from API");
            }
        });
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialTextView eventName;
        private MaterialTextView eventKey;
        private MaterialRadioButton eventSelectedRadioButton;

        private EventsListViewModel eventViewModel;

        EventsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            eventName = itemView.findViewById(R.id.event_display_name);
            eventKey = itemView.findViewById(R.id.event_key);
            eventSelectedRadioButton = itemView.findViewById(R.id.event_selected_radiobutton);

            eventSelectedRadioButton.setOnClickListener(v -> {
                eventListAdapter.setCurrentEvent( eventViewModel );
            });
        }

        public void setEvent(EventsListViewModel eventViewModel) {
            this.eventViewModel = eventViewModel;

            eventName.setText(eventViewModel.getName());
            eventKey.setText(eventViewModel.getEventId());
            eventSelectedRadioButton.setChecked(eventViewModel.getIsSelected());
        }

        public void setDisplayText(String displayText) {
            eventName.setText(displayText);
        }

        @Override
        public void onClick(View view) {
            eventListAdapter.setCurrentEvent(eventViewModel);
        }
    }

    public class EventListAdapter extends RecyclerView.Adapter<EventsFragment.EventsViewHolder> {


        private final LayoutInflater inflater;
        private List<EventsListViewModel> eventViewModels;

        EventListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public EventsFragment.EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_event, parent, false);
            return new EventsFragment.EventsViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull EventsFragment.EventsViewHolder holder, int position) {
            if (eventViewModels != null) {
                holder.setEvent(eventViewModels.get(position));
            } else {
                holder.setDisplayText("No Event Data Yet...");
            }
        }

        void setEvents(List<Evt> events) {
            Context appContext = getActivity().getApplicationContext();
            ApiStatus status = new ApiStatus(appContext);
            String currentEventId = status.getEventKey();

            eventViewModels = new ArrayList<>();
            for( Evt event : events ){
                EventsListViewModel viewModel = new EventsListViewModel(event);
                if( event.getEventId().equals(currentEventId) ){
                    viewModel.setIsSelected(true);
                }
                eventViewModels.add(viewModel);
            }

            notifyDataSetChanged();
        }

        void setCurrentEvent(EventsListViewModel currentViewModel){
            ApiActivity orangeAllianceActivity = (ApiActivity)getActivity();
            orangeAllianceActivity.setEventKey(currentViewModel.getEventId());

            for(EventsListViewModel viewModel : eventViewModels){
                viewModel.setIsSelected( currentViewModel.getEventId().equals(viewModel.getEventId()));
            }

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return eventViewModels == null ? 0 : eventViewModels.size();
        }
    }

}

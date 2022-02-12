package org.hartlandrobotics.echelon2.TBA.fragments;

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
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.TBA.Api;
import org.hartlandrobotics.echelon2.TBA.ApiInterface;
import org.hartlandrobotics.echelon2.TBA.TBAActivity;
import org.hartlandrobotics.echelon2.TBA.models.SyncEvent;
import org.hartlandrobotics.echelon2.database.entities.DistrictEvtCrossRef;
import org.hartlandrobotics.echelon2.database.entities.Evt;
import org.hartlandrobotics.echelon2.database.repositories.EventRepo;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {
    private Button eventPull;
    private TextView errorTextDisplay;

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

        eventPull = fragmentView.findViewById(R.id.eventPullButton);
        errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);
        errorTextDisplay.setVisibility(View.GONE);

        setupCurrentEvents();

        setupEventPulls();

        return fragmentView;
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
        BlueAllianceStatus status = new BlueAllianceStatus(appContext);
        String currentDistrict = status.getDistrictKey();
        EventRepo eventRepo = new EventRepo(EventsFragment.this.getActivity().getApplication());
        eventRepo.getDistrictWithEvents(currentDistrict).observe(getViewLifecycleOwner(), district -> {
            eventListAdapter.setEvents(district.events);
        });
    }

    public void setupEventPulls(){
        eventPull.setOnClickListener((view) -> {
            ApiInterface newApi = Api.getApiClient(getActivity().getApplication());

            try{
                Context appContext = getActivity().getApplicationContext();
                BlueAllianceStatus status = new BlueAllianceStatus(appContext);
                String districtKey = status.getDistrictKey();

                Call<List<SyncEvent>> newCall = newApi.getEventsByDistrict(districtKey);
                newCall.enqueue(new Callback<List<SyncEvent>>() {
                    @Override
                    public void onResponse(Call<List<SyncEvent>> call, Response<List<SyncEvent>> response){
                        try{
                            if(!response.isSuccessful()){
                                errorTextDisplay.setText("Couldn't pull events");
                            }
                            else{
                                EventRepo eventRepo = new EventRepo(EventsFragment.this.getActivity().getApplication());
                                List<SyncEvent> syncEvents = response.body();
                                List<Evt> events = syncEvents.stream()
                                        .map(event -> event.toEvent())
                                        .collect(Collectors.toList());

                                eventRepo.upsert(events);

                                for(Evt event: events){
                                    DistrictEvtCrossRef crossRef = event.toDistrictEvent(districtKey);
                                    eventRepo.upsert(crossRef);

                                }

                                eventListAdapter.setEvents(events);
                                errorTextDisplay.setText("Got event: " + syncEvents.size());
                            }
                        }
                        catch(Exception e){
                            Log.e("Inside onResponse", "\"Exception thrown inside onResponse\"");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<SyncEvent>> call, Throwable t){
                        errorTextDisplay.setText("Error attempting to pull events from TBA");
                    }

                });
            }
            catch(Exception e){
                Log.e("Outside Onresponse", "Exception attempting to pull from TBA");
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

            eventName.setText(eventViewModel.getEventName());
            eventKey.setText(eventViewModel.getEventKey());
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
            BlueAllianceStatus status = new BlueAllianceStatus(appContext);
            String currentEventKey = status.getEventKey();

            eventViewModels = new ArrayList<>();
            for( Evt event : events ){
                EventsListViewModel viewModel = new EventsListViewModel(event);
                if( event.getEventKey().equals(currentEventKey) ){
                    viewModel.setIsSelected(true);
                }
                eventViewModels.add(viewModel);
            }

            notifyDataSetChanged();
        }

        void setCurrentEvent(EventsListViewModel currentViewModel){
            TBAActivity tbaActivity = (TBAActivity)getActivity();
            tbaActivity.setEventKey(currentViewModel.getEventKey());

            for(EventsListViewModel viewModel : eventViewModels){
                viewModel.setIsSelected( currentViewModel.getEventKey().equals(viewModel.getEventKey()));
            }

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return eventViewModels == null ? 0 : eventViewModels.size();
        }
    }

}

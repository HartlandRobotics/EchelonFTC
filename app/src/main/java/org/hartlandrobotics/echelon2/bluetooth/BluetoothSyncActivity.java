package org.hartlandrobotics.echelon2.bluetooth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.hartlandrobotics.echelon2.EchelonActivity;
import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.configuration.AdminSettings;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.models.MatchResultViewModel;
import org.hartlandrobotics.echelon2.models.PitScoutViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
public class BluetoothSyncActivity extends EchelonActivity {
    private static final String TAG = "BluetoothSyncActivity";

    MaterialTextView matchResultsTotalText;
    MaterialTextView matchResultsUnsyncedLabel;
    MaterialTextView matchResultsUnsyncedText;
    MaterialButton resetSyncButton;

    MaterialTextView pitScoutTotalText;
    MaterialTextView pitScoutUnsyncedLabel;
    MaterialTextView pitScoutUnsyncedText;
    MaterialButton resetPitscoutSyncButton;

    LinearLayout deviceLayout;

    MaterialButton red1SyncButton;
    MaterialButton red2SyncButton;
    MaterialButton red3SyncButton;

    MaterialButton blue1SyncButton;
    MaterialButton blue2SyncButton;
    MaterialButton blue3SyncButton;

    MaterialButton sub1SyncButton;
    MaterialButton sub2SyncButton;
    MaterialButton sub3SyncButton;

    RecyclerView logRecyclerView;
    LogLinesAdapter logLinesAdapter;

    MatchResultViewModel matchResultViewModel;
    BluetoothSyncService bluetoothService;
    private Map<String, BluetoothDevice> devices;
    List<MatchResult> matchResults;

    PitScoutViewModel pitscoutViewModel;

    Map<String,String> deviceNameByRole;
    Map<String, MaterialButton> buttonsByDeviceName;

    public static void launch(Context context){
        Intent intent = new Intent(context, BluetoothSyncActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_sync);


        setupToolbar();
        setupControls();

        setupDeviceMapping();

        setupData();

    }

    private void setupData(){
        // only need this if this is a non captain tablet
        BlueAllianceStatus blueAllianceStatus = new BlueAllianceStatus(getApplicationContext());

        pitscoutViewModel = new ViewModelProvider( this ).get(PitScoutViewModel.class);
        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultsByEvent(blueAllianceStatus.getEventKey()).observe(this, mrList -> {
            logLinesAdapter.addStatusItem("Match results loaded");
            if( mrList == null || mrList.size() == 0){
                matchResults = new ArrayList<>();
                logLinesAdapter.addStatusItem("No match results on this device");
            } else {
                matchResults = mrList;
                logLinesAdapter.addStatusItem("device contains " + matchResults.size() + " match results");
            }

            matchResultsTotalText.setText( String.valueOf( matchResults.size() ) );
            long unsyncedCount = matchResults.stream().filter( mr -> !mr.getHasBeenSynced() ).count();
            matchResultsUnsyncedText.setText( String.valueOf(unsyncedCount));

            //pitscoutViewModel.getPitScout()

            setupService();
            setVisibility();
        });
    }

    private void setupDeviceMapping( ){
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(this);
        String teamNumber = settings.getTeamNumber();

        deviceNameByRole = new HashMap<>();
        deviceNameByRole.put("red1", "red_1_" + teamNumber);
        deviceNameByRole.put("red2", "red__2_" + teamNumber);
        deviceNameByRole.put("red3", "red_3_" + teamNumber);

        deviceNameByRole.put("blue1", "blue_1_" + teamNumber);
        deviceNameByRole.put("blue2", "blue_2_" + teamNumber);
        deviceNameByRole.put("blue3", "blue_3_" + teamNumber);

        deviceNameByRole.put("sub1", "sub_1_" + teamNumber);
        deviceNameByRole.put("sub2", "sub_2_" + teamNumber);
        deviceNameByRole.put("sub3", "sub_3_" + teamNumber);

        buttonsByDeviceName = new HashMap<>();
        buttonsByDeviceName.put("red_1_" + teamNumber, red1SyncButton);
        buttonsByDeviceName.put("red_2_" + teamNumber, red2SyncButton);
        buttonsByDeviceName.put("red_3_" + teamNumber, red3SyncButton);

        buttonsByDeviceName.put("blue_1_" + teamNumber, blue1SyncButton);
        buttonsByDeviceName.put("blue_2_" + teamNumber, blue2SyncButton);
        buttonsByDeviceName.put("blue_3_" + teamNumber, blue3SyncButton);

        buttonsByDeviceName.put("sub_1_" + teamNumber, sub1SyncButton);
        buttonsByDeviceName.put("sub_2_" + teamNumber, sub2SyncButton);
        buttonsByDeviceName.put("sub_3_" + teamNumber, sub3SyncButton);
    }

    private void setupControls(){
         matchResultsTotalText = findViewById(R.id.totalMatchResults);
         matchResultsUnsyncedLabel = findViewById(R.id.unsyncedMatchResultsLabel);
         matchResultsUnsyncedText = findViewById(R.id.unsyncedMatchResults);
         resetSyncButton = findViewById(R.id.resetSyncButton);
         resetSyncButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 for( MatchResult matchResult : matchResults ) {
                     matchResult.setHasBeenSynced(false);
                     matchResultViewModel.upsert(matchResult);
                 }

                 //setupData();
             }
         });
        pitScoutTotalText = findViewById(R.id.totalPitScout);
        pitScoutUnsyncedLabel = findViewById(R.id.unsyncedPitScoutLabel);
        pitScoutUnsyncedText = findViewById(R.id.unsyncedPitScout);
        resetPitscoutSyncButton = findViewById(R.id.resetPitScoutSyncButton);
        resetPitscoutSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for( PitScout pitscout : pitScouts ){
                //    pitscout.setHasBeenSynced(false);
                //    pitscoutViewModel.upsert(pitscout);
                //}
            }
        });


        deviceLayout = findViewById( R.id.deviceLayout);

        red1SyncButton = findViewById(R.id.red1SyncButton);
        red1SyncButton.setOnClickListener(v ->
                pullDeviceData("red1", red1SyncButton)
        );

        red2SyncButton = findViewById(R.id.red2SyncButton);
        red2SyncButton.setOnClickListener(v ->
                pullDeviceData("red2", red2SyncButton)
        );

        red3SyncButton = findViewById(R.id.red3SyncButton);
        red3SyncButton.setOnClickListener(v ->
                pullDeviceData("red3", red3SyncButton)
        );



        blue1SyncButton = findViewById(R.id.blue1SyncButton);
        blue1SyncButton.setOnClickListener(v ->
                pullDeviceData("blue1", blue1SyncButton)
        );

        blue2SyncButton = findViewById(R.id.blue2SyncButton);
        blue2SyncButton.setOnClickListener(v ->
                pullDeviceData("blue2", blue2SyncButton)
        );

        blue3SyncButton = findViewById(R.id.blue3SyncButton);
        blue3SyncButton.setOnClickListener(v ->
                pullDeviceData("blue3", blue3SyncButton)
        );



        sub1SyncButton = findViewById(R.id.sub1SyncButton);
        sub1SyncButton.setOnClickListener(v ->
                pullDeviceData("sub1", sub1SyncButton)
        );

        sub2SyncButton = findViewById(R.id.sub2SyncButton);
        sub2SyncButton.setOnClickListener(v ->
                pullDeviceData("sub2", sub2SyncButton)
        );

        sub3SyncButton = findViewById(R.id.sub3SyncButton);
        sub3SyncButton.setOnClickListener(v ->
                pullDeviceData("sub3", sub3SyncButton)
        );

        logRecyclerView = findViewById(R.id.sync_log_text);
        logLinesAdapter = new LogLinesAdapter( this );
        logRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        logRecyclerView.setAdapter( logLinesAdapter );
    }

    private void setVisibility(){
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        String deviceName = bluetoothService.getCurrentDeviceName();
        //if( deviceName == "captain_" + settings.getTeamNumber() ){
        // dburton
        if( deviceName.equals("master_" + settings.getTeamNumber()) ){
            matchResultsUnsyncedLabel.setVisibility(View.GONE);
            matchResultsUnsyncedText.setVisibility(View.GONE);
        } else {
            deviceLayout.setVisibility(View.GONE);
        }

    }

    private void pullDeviceData(String deviceRole, MaterialButton source){
        String deviceName =  deviceNameByRole.get(deviceRole);
        BluetoothDevice device = devices.get(deviceName);

        logLinesAdapter.addStatusItem( "Pulling data from " + deviceName );

        bluetoothService.connectToDevice(device);

        //source.setIconResource(R.drawable.outline_file_download_done_24);
        //source.setIconResource(R.drawable.outline_error_outline_24);
    }

    private void setupService() {
        if (bluetoothService == null) {
            bluetoothService = new BluetoothSyncService(this, handler, matchResults);//, mPitScout );
            if (bluetoothService.isEnabled()) {
                devices = bluetoothService.getDevices();
            }
            bluetoothService.start();
        }
    }

    public class LogLinesAdapter extends RecyclerView.Adapter<LogLineViewHolder> {
        private final LayoutInflater inflater;
        private List<String> statusItems = new ArrayList<String>();

        LogLinesAdapter(Context context) {
            inflater = LayoutInflater.from( context );
        }

        @NonNull
        @Override
        public LogLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate( R.layout.list_item_bluetooth_log, parent, false );
            return new LogLineViewHolder( itemView );
        }

        @Override
        public void onBindViewHolder(@NonNull LogLineViewHolder viewHolder, int position) {
            if ( statusItems != null ) {
                viewHolder.setStatusText( statusItems.get( position ) );
            }
        }

        public void addStatusItem(String newStatus) {
            this.statusItems.add( 0, newStatus );
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if ( this.statusItems != null ) {
                return statusItems.size();
            }
            return 0;
        }
    }

    public class LogLineViewHolder extends RecyclerView.ViewHolder {
        private final TextView mStatusItemText;

        private LogLineViewHolder(View itemView) {
            super( itemView );

            mStatusItemText = itemView.findViewById( R.id.log_item_text );
        }

        public void setStatusText(String statusText) {
            mStatusItemText.setText( statusText );
        }
    }


    // Bluetooth  \/\/\/\/\/\/\/
    private String runningMessage = "";
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch ( msg.what ) {
                case BluetoothMessageType.MESSAGE_READ:
                    Log.i(TAG, "in handler read");
                    byte[] readBuf = (byte[]) msg.obj;

                    String currentMessage = new String( readBuf, 0, msg.arg1 );
                    runningMessage += currentMessage;
                    //ObjectMapper jsonReader = new ObjectMapper();
                    Gson gson = new Gson();
                    ResultsContainer transferResults;
                    try {
                        Log.i(TAG, "in read with message: " + runningMessage );
                        //transferResults = jsonReader.readValue(runningMessage, ResultsContainer.class);
                        transferResults = gson.fromJson( runningMessage, ResultsContainer.class );
                        Log.i(TAG, "got transfer results with: " + transferResults.getMatchResults().size() );
                        runningMessage = "";
                        logLinesAdapter.addStatusItem("received match results");
                        StringBuilder sb = new StringBuilder();
                        sb.append( "Uploaded:" )
                                .append( transferResults.getMatchResults().size() ).append( " match results " )
                        //.append( transferResults.getPitScoutResults().size() ).append( " pit scoutings");
                        ;
                        List<MatchResult> transferredMatchResults = transferResults.getMatchResults();
                        for ( MatchResult currentResult : transferredMatchResults ) {
                            currentResult.setHasBeenSynced( true );
                            matchResultViewModel.upsert( currentResult );
                        }

                        String deviceName = transferResults.getSourceDeviceName();
                        MaterialButton callingButton = buttonsByDeviceName.get(deviceName);
                        callingButton.setIconResource(R.drawable.outline_file_download_done_24);
                        logLinesAdapter.addStatusItem( "Completed Sync with " + transferredMatchResults.size() );

                        bluetoothService.start();

                        logLinesAdapter.addStatusItem(sb.toString());
                    } catch ( Exception e ) {
                        logLinesAdapter.addStatusItem( "failed to convert: " + e.getMessage() );
                    }

                    setupData();
                    break;

                case BluetoothMessageType.MESSAGE_WRITE:
                    logLinesAdapter.addStatusItem( "message written from this device");
                    break;
                case BluetoothMessageType.MESSAGE_REQUEST:
                    logLinesAdapter.addStatusItem( "Request for data from master" );
                    break;
                case BluetoothMessageType.MESSAGE_SENT:
                    logLinesAdapter.addStatusItem( "Data sent to master" );
                    for ( MatchResult currentMatchResult : bluetoothService.nextUnsyncedMatchResults ) {
                        currentMatchResult.setHasBeenSynced(true);
                        matchResultViewModel.upsert( currentMatchResult );
                    }
                    setupData();

                    bluetoothService.start();

                    break;
            }
        }
    };
}
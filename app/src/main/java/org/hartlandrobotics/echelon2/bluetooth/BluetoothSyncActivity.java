package org.hartlandrobotics.echelon2.bluetooth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.hartlandrobotics.echelon2.EchelonActivity;
import org.hartlandrobotics.echelon2.R;

import java.util.ArrayList;
import java.util.List;

public class BluetoothSyncActivity extends EchelonActivity {

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
    }

    private void setupControls(){
        red1SyncButton = findViewById(R.id.red1SyncButton);
        red1SyncButton.setOnClickListener(v -> pullDeviceData("red1", red1SyncButton));
        red2SyncButton = findViewById(R.id.red2SyncButton);
        red2SyncButton.setOnClickListener(v -> pullDeviceData("red2", red2SyncButton));
        red3SyncButton = findViewById(R.id.red3SyncButton);
        red3SyncButton.setOnClickListener(v -> pullDeviceData("red3", red3SyncButton));

        blue1SyncButton = findViewById(R.id.blue1SyncButton);
        blue1SyncButton.setOnClickListener(v -> pullDeviceData("blue1", blue1SyncButton));
        blue2SyncButton = findViewById(R.id.blue2SyncButton);
        blue2SyncButton.setOnClickListener(v -> pullDeviceData("blue2", blue2SyncButton));
        blue3SyncButton = findViewById(R.id.blue3SyncButton);
        blue3SyncButton.setOnClickListener(v -> pullDeviceData("blue3", blue3SyncButton));

        sub1SyncButton = findViewById(R.id.sub1SyncButton);
        sub1SyncButton.setOnClickListener(v -> pullDeviceData("sub1", sub1SyncButton));
        sub2SyncButton = findViewById(R.id.sub2SyncButton);
        sub2SyncButton.setOnClickListener(v -> pullDeviceData("sub2", sub2SyncButton));
        sub3SyncButton = findViewById(R.id.sub3SyncButton);
        sub3SyncButton.setOnClickListener(v -> pullDeviceData("sub3", sub3SyncButton));

        logRecyclerView = findViewById(R.id.sync_log_text);
    }

    private void pullDeviceData(String deviceName, MaterialButton source){
        source.setIconResource(R.drawable.outline_file_download_done_24);
        //source.setIconResource(R.drawable.outline_error_outline_24);
    }

    /*
    public class SyncerAdapter extends RecyclerView.Adapter<SyncerViewHolder> {
        private final LayoutInflater mInflater;
        private List<String> statusItems = new ArrayList<String>();

        SyncerAdapter(Context context) {
            mInflater = LayoutInflater.from( context );
        }

        @NonNull
        @Override
        public SyncerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate( R.layout.list_item_bluetooth, parent, false );
            return new SyncerViewHolder( itemView );
        }

        @Override
        public void onBindViewHolder(@NonNull SyncerViewHolder viewHolder, int position) {
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

    public class SyncerViewHolder extends RecyclerView.ViewHolder {
        private final TextView mStatusItemText;

        private SyncerViewHolder(View itemView) {
            super( itemView );

            mStatusItemText = itemView.findViewById( R.id.status_item_text );
        }

        public void setStatusText(String statusText) {
            mStatusItemText.setText( statusText );
        }


    }
    */
}
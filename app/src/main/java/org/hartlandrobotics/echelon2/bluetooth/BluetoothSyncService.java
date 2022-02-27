package org.hartlandrobotics.echelon2.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.database.entities.PitScout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BluetoothSyncService {
    private static final String TAG = "BluetoothSyncerService";
    private static final String SERVICE_NAME = "BluetoothSyncerInsecure";
    private static final UUID SERVICE_UUID = UUID.fromString( "a7b92250-830c-4541-a741-23ac168bfd6f" );

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    private int currentState;

    private AcceptThread acceptThread;
    private ConnectThread connectThread;
    private ConnectedThread connectedThread;

    private final Handler handler;
    private BluetoothAdapter bluetoothAdapter;
    private Context callingContext;
    private List<MatchResult> matchResults;
    public List<MatchResult> lastSentResults;

    private List<PitScout> pitScoutResults;
    public List<PitScout> lastSentPitScoutResults;

    private String currentDeviceName;
    private Map<String, BluetoothDevice> devices;
    private String teamNumber;

    public BluetoothSyncService(Context context,
                                Handler handler,
                                String teamNumber,
                                List<MatchResult> matchResults,
                                List<PitScout> pitScoutResults

    ){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        callingContext = context;
        this.handler = handler;
        currentState = STATE_NONE;
        this.teamNumber = teamNumber;
        this.matchResults = matchResults;
        this.pitScoutResults = pitScoutResults;
    }

    public void setData(List<MatchResult> matchResults, List<PitScout> pitScoutResults){
        this.matchResults = matchResults;
        this.pitScoutResults = pitScoutResults;
    }

    public boolean isAdapterAvailable() {
        return bluetoothAdapter != null;
    }

    public boolean isEnabled() {
        return isAdapterAvailable() && bluetoothAdapter.isEnabled();
    }

    public String getCurrentDeviceName(){
        currentDeviceName = StringUtils.defaultIfBlank(currentDeviceName, bluetoothAdapter.getName());
        return currentDeviceName;
    }

    public Map<String, BluetoothDevice> getDevices() {
        if ( devices != null ) return devices;

        devices = bluetoothAdapter.getBondedDevices()
                .stream()
                .filter( bluetoothDevice -> bluetoothDevice.getName().startsWith( "red" ) ||
                        bluetoothDevice.getName().startsWith( "blue" ) ||
                        bluetoothDevice.getName().startsWith( "alt" ) ||
                        bluetoothDevice.getName().startsWith( "video" ) ||
                        bluetoothDevice.getName().startsWith("captain"))
                .collect( Collectors.toMap( BluetoothDevice::getName, Function.identity() ) );

        return devices;
    }

    public synchronized void updateState(){
    }

    public synchronized void start() {

        if ( connectThread != null ) {
            connectThread.cancel();
            connectThread = null;
        }

        if ( connectedThread != null ) {
            connectedThread.cancel();
            connectedThread = null;
        }

        if ( acceptThread == null ) {
            acceptThread = new AcceptThread( );
            acceptThread.start();
        }

        updateState();
    }

    public void connectToDevice(BluetoothDevice device) {
        if ( currentState == STATE_CONNECTING && connectThread != null ) {
            connectThread.cancel();
            connectThread = null;
        }

        if ( connectedThread != null ) {
            connectedThread.cancel();
            connectedThread = null;
        }

        connectThread = new ConnectThread( device );
        connectThread.start();

        updateState();
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if ( connectThread != null ) {
            connectThread.cancel();
            connectThread = null;
        }

        if ( connectedThread != null ) {
            connectedThread.cancel();
            connectedThread = null;
        }

        if ( acceptThread != null ) {
            acceptThread.cancel();
            acceptThread = null;
        }

        connectedThread = new ConnectedThread( socket );
        connectedThread.start();

        // could send a message to say which device is connected, but
        // they way we use this we should know that
        updateState();
    }

    public void connectionFailed() {
        // maybe set current state
    }

    private void connectionLost() {
        // Send a failure message back to the Activity
        /*
        Message msg = mHandler.obtainMessage(Constants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOAST, "Device connection was lost");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
         */

        currentState = STATE_NONE;
        // Update UI title
        //updateUserInterfaceTitle();

        // Start the service over to restart listening mode
        BluetoothSyncService.this.start();
    }

    public void write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (currentState != STATE_CONNECTED) return;
            r = connectedThread;
        }
        r.write(out);
    }

    private void requestMatchData(){
        //Toast.makeText( mCallingContext, "Sending request for match data", Toast.LENGTH_LONG );
        //mConnectedThread.requestMatchData();
    }
    // \/\/\/\/\/ Threads below

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket acceptServerSocket;

        public AcceptThread() {
            BluetoothServerSocket newServerSocket = null;

            // Create a new listening server socket
            try {
                newServerSocket = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord( SERVICE_NAME, SERVICE_UUID );
            } catch ( IOException e ) {
                //Toast.makeText( mCallingContext, "Could not create BT server socket", Toast.LENGTH_LONG ).show();
            }
            acceptServerSocket = newServerSocket;
            currentState = STATE_LISTEN;
        }

        public void run() {
            setName( "AcceptThread" );

            BluetoothSocket socket;

            while ( currentState != STATE_CONNECTED ) {
                try {
                    socket = acceptServerSocket.accept();
                    Log.i(TAG, "accept success");
                } catch ( IOException e ) {
                    Log.e(TAG, "accept failed");
                    break;
                }

                // should have a connected socket now
                if ( socket == null ) continue;

                synchronized ( BluetoothSyncService.this ) {
                    switch ( currentState ) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            // Start the connected thread.
                            connected( socket, socket.getRemoteDevice() );
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            // Either not ready or already connected. Terminate new socket.
                            try {
                                socket.close();
                            } catch ( IOException e ) {
                                Log.e(TAG,"could not close unwanted socket");
                            }
                            break;
                    }
                }
            }
            Log.i(TAG, "END acceptThread");

        }

        public void cancel() {
            //Log.d(TAG, "Socket Type" + mSocketType + "cancel " + this);
            try {
                acceptServerSocket.close();
            } catch ( IOException e ) {
                Log.e(TAG,"close() of server failed");
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket connectSocket;
        private final BluetoothDevice connectDevice;

        public ConnectThread(BluetoothDevice device) {
            connectDevice = device;
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                tmp = device.createInsecureRfcommSocketToServiceRecord( SERVICE_UUID );
            } catch ( IOException e ) {
                Log.e(TAG,"Socket Type create failed");
            }
            connectSocket = tmp;
            //mState = STATE_CONNECTING;
        }

        public static final String CONNECTED_THREAD_NAME = "BluetoothSyncerConnectThread";

        public void run() {
            //Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName( CONNECTED_THREAD_NAME );

            bluetoothAdapter.cancelDiscovery();
            try {
                connectSocket.connect();
            } catch ( IOException connectException ) {
                try {
                    connectSocket.close();
                } catch ( IOException closeException ) {
                    Log.e(TAG, "unable to close socket during connection failute");
                }
                connectionFailed();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized ( ConnectThread.this ) {
                connectThread = null;
            }

            // Start the connected thread
            //mConnectThread.cancel();
            connected( connectSocket, connectDevice);
        }

        public void cancel() {
            try {
                connectSocket.close();
            } catch ( IOException e ) {
                Log.e(TAG,"close of connect socket failed");
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket connectedSocket;
        private final InputStream connectedInStream;
        private final OutputStream connectedOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            //Log.d(TAG, "create ConnectedThread: " + socketType);
            connectedSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch ( IOException e ) {
                Log.e(TAG, "temp sockets not created");
            }

            connectedInStream = tmpIn;
            connectedOutStream = tmpOut;
            currentState = STATE_CONNECTED;
        }

        private boolean dataSent = false;
        public void run() {
            //Log.i(TAG, "BEGIN mConnectedThread");

            // Keep listening to the InputStream while connected
            while ( currentState == STATE_CONNECTED ) {
                try {
                    if( getCurrentDeviceName().equals( "captain_" + teamNumber )) {
                       // captain tablet consuming data sent from scouting tablet
                        String result = "";
                        boolean isDone = false;
                        while( !isDone ){
                            int maxSize = 1024;//*20;
                            byte[] buffer = new byte[maxSize];
                            int numberOfBytes = connectedInStream.read( buffer );

                            String currentString = new String(buffer, 0, numberOfBytes);
                            System.out.println( "currentString: " + currentString );
                            result += currentString;

                            ObjectMapper mapper = new ObjectMapper();
                            try {
                                ResultsContainer r = mapper.readValue(result, ResultsContainer.class);
                                isDone = true;
                            }
                            catch( Exception e) {
                                String message = e.getMessage();
                                Log.e(TAG,"parsing error: " + e.getMessage());
                            }
                        }

                        // Send the obtained bytes to the UI Activity
                        Log.i(TAG, "completed parsing with " + result);
                        byte[] msg = result.getBytes();
                        handler.obtainMessage( BluetoothMessageType.MESSAGE_READ, msg.length, -1, msg )
                                .sendToTarget();

                        currentState = STATE_NONE;
                    }
                    else {
                        if( !dataSent ) {
                            // scout tablet sending to the captain tablet
                            handler.obtainMessage( BluetoothMessageType.MESSAGE_REQUEST, -1, -1, null )
                                    .sendToTarget();
                            lastSentResults = new ArrayList<>();
                            List<MatchResult> nextUnsyncedMatchResults = matchResults.stream()
                                    .filter( result -> result.getHasBeenSynced() == false )
                                    .collect( Collectors.toList( ));

                            lastSentPitScoutResults = new ArrayList<>();
                            List<PitScout> nextUnsyncedPitScoutResults = pitScoutResults.stream()
                                    .filter( result -> !result.getHasBeenSynced() )
                                    .collect( Collectors.toList() );

                            ResultsContainer resultsContainer = new ResultsContainer(getCurrentDeviceName(), nextUnsyncedMatchResults, nextUnsyncedPitScoutResults );

                            ObjectMapper mapper = new ObjectMapper();
                            String json = mapper.writeValueAsString( resultsContainer );

                            // get the pit scout and match result data serialize into a string
                            write( json.getBytes() );
                            dataSent = true;

                            lastSentResults.addAll(nextUnsyncedMatchResults);
                            lastSentPitScoutResults.addAll(nextUnsyncedPitScoutResults);
                            handler.obtainMessage( BluetoothMessageType.MESSAGE_SENT, -1, -1, null )
                                    .sendToTarget();
                        }
                    }
                } catch ( IOException e ) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost();
                    break;
                }

                try {
                    currentState = STATE_NONE;
                    connectedSocket.close();
                } catch ( IOException e ) {
                    // need a handler for sending an error message
                    Log.e(TAG, "need a handler for sending error message", e);
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                connectedOutStream.write(buffer);

                // Share the sent message back to the UI Activity
                handler.obtainMessage(BluetoothMessageType.MESSAGE_WRITE, buffer.length, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG,"exception during write: " ,e);
                //Toast.makeText( mCallingContext,"Exception during write", Toast.LENGTH_LONG).show();
            }
        }


        public void cancel() {
            try { connectedSocket.close(); }
            catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }


    }
}

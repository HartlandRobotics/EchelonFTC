package org.hartlandrobotics.echelon2.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

import org.hartlandrobotics.echelon2.database.entities.MatchResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private int mCurrentState;
    // ? private int mNewState;

    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    private final Handler mHandler;
    private BluetoothAdapter mBluetoothAdapter;
    private Context mCallingContext;
    private List<MatchResult> mMatchResults;
    public List<MatchResult> NextUnsyncedResults;
    //private List<PitScout> mPitScoutResults;

    public BluetoothSyncService(Context context, Handler handler, List<MatchResult> matchResults){//, List<PitScout> pitScoutResults) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mCallingContext = context;
        mHandler = handler;
        mCurrentState = STATE_NONE;
        mMatchResults = matchResults;
        //mPitScoutResults = pitScoutResults;
        NextUnsyncedResults = matchResults.stream()
                .filter( result -> result.getHasBeenSynced() == false )
                .limit( 5 )
                .collect( Collectors.toList( ));
    }

    public boolean isAdapterAvailable() { return mBluetoothAdapter != null; }

    public boolean isEnabled() {
        return isAdapterAvailable() && mBluetoothAdapter.isEnabled();
    }

    private String mCurrentDeviceName = "";
    public String getCurrentDeviceName(){
        if( mCurrentDeviceName.isEmpty() )
            mCurrentDeviceName = mBluetoothAdapter.getName();
        return mCurrentDeviceName;
    }

    private Map<String, BluetoothDevice> mDevices;
    public Map<String, BluetoothDevice> getDevices() {
        if ( mDevices != null ) return mDevices;

        mDevices = mBluetoothAdapter.getBondedDevices()
                .stream()
                .filter( bluetoothDevice -> bluetoothDevice.getName().startsWith( "red" ) ||
                        bluetoothDevice.getName().startsWith( "blue" ) ||
                        bluetoothDevice.getName().startsWith( "video" ) ||
                        bluetoothDevice.getName().startsWith( "master" ) )
                .collect( Collectors.toMap( BluetoothDevice::getName, Function.identity() ) );

        return mDevices;
    }

    public synchronized void updateState(){

    }

    public synchronized void start() {
        NextUnsyncedResults = mMatchResults.stream()
                .filter( result -> result.getHasBeenSynced() == false )
                .limit( 5 )
                .collect( Collectors.toList( ));
        if ( mConnectThread != null ) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if ( mConnectedThread != null ) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if ( mAcceptThread == null ) {
            mAcceptThread = new AcceptThread( );
            mAcceptThread.start();
        }

        updateState();
    }

    public void connectToDevice(BluetoothDevice device) {
        if ( mCurrentState == STATE_CONNECTING && mConnectThread != null ) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if ( mConnectedThread != null ) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mConnectThread = new ConnectThread( device );
        mConnectThread.start();

        updateState();
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if ( mConnectThread != null ) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if ( mConnectedThread != null ) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if ( mAcceptThread != null ) {
            mAcceptThread.cancel();
            mAcceptThread = null;
        }

        mConnectedThread = new ConnectedThread( socket );
        mConnectedThread.start();

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

        mCurrentState = STATE_NONE;
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
            if (mCurrentState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        r.write(out);
    }

    private void requestMatchData(){
        //Toast.makeText( mCallingContext, "Sending request for match data", Toast.LENGTH_LONG );
        //mConnectedThread.requestMatchData();
    }
    // \/\/\/\/\/ Threads below

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket newServerSocket = null;

            // Create a new listening server socket
            try {
                newServerSocket = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord( SERVICE_NAME, SERVICE_UUID );
            } catch ( IOException e ) {
                //Toast.makeText( mCallingContext, "Could not create BT server socket", Toast.LENGTH_LONG ).show();
            }
            mmServerSocket = newServerSocket;
            mCurrentState = STATE_LISTEN;
        }

        public void run() {
            setName( "AcceptThread" );

            BluetoothSocket socket;

            while ( mCurrentState != STATE_CONNECTED ) {
                try {
                    socket = mmServerSocket.accept();
                } catch ( IOException e ) {
                    //Toast.makeText( mCallingContext, "accept failed", Toast.LENGTH_LONG ).show();
                    break;
                }

                // should have a connected socket now
                if ( socket == null ) continue;

                synchronized ( BluetoothSyncService.this ) {
                    switch ( mCurrentState ) {
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
                            } catch ( IOException e ) {/*Log.e(TAG, "Could not close unwanted socket", e);*/}
                            break;
                    }
                }
            }
            //Log.i(TAG, "END mAcceptThread, socket Type: " + mSocketType);

        }

        public void cancel() {
            //Log.d(TAG, "Socket Type" + mSocketType + "cancel " + this);
            try {
                mmServerSocket.close();
            } catch ( IOException e ) {
                //Log.e(TAG, "Socket Type" + mSocketType + "close() of server failed", e);
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                tmp = device.createInsecureRfcommSocketToServiceRecord( SERVICE_UUID );
            } catch ( IOException e ) {
                //Toast.makeText( mCallingContext, "Socket Type: create() failed", Toast.LENGTH_LONG ).show();
            }
            mmSocket = tmp;
            //mState = STATE_CONNECTING;
        }

        public static final String CONNECTED_THREAD_NAME = "BluetoothSyncerConnectThread";

        public void run() {
            //Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName( CONNECTED_THREAD_NAME );

            mBluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
            } catch ( IOException connectException ) {
                try {
                    mmSocket.close();
                } catch ( IOException closeException ) {
                    //Toast.makeText( mCallingContext, "unable to close() socket during connection failure" + closeException.getMessage(), Toast.LENGTH_LONG ).show();
                }
                connectionFailed();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized ( ConnectThread.this ) {
                mConnectThread = null;
            }

            // Start the connected thread
            //mConnectThread.cancel();
            connected( mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch ( IOException e ) {
                //Toast.makeText( mCallingContext, "close() of connect socket failed" + e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            //Log.d(TAG, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch ( IOException e ) {
                //Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            mCurrentState = STATE_CONNECTED;
        }

        private boolean dataSent = false;
        public void run() {
            //Log.i(TAG, "BEGIN mConnectedThread");

            // Keep listening to the InputStream while connected
            while ( mCurrentState == STATE_CONNECTED ) {
                try {
                    if( getCurrentDeviceName().equals( "master_3536" )) {
                        String result = "";
                        boolean isDone = false;
                        while( !isDone ){
                            int maxSize = 1024;//*20;
                            byte[] buffer = new byte[maxSize];
                            int numberOfBytes = mmInStream.read( buffer );
                            //if( numberOfBytes <= maxSize || numberOfBytes == 0){
                            //if( numberOfBytes == 0){
                            //isDone = true;
                            //}
                            String currentString = new String(buffer, 0, numberOfBytes);
                            System.out.println( "currentString: " + currentString );
                            result += currentString;
                            // dburton convert to Jackson
                            //Gson gson = new Gson();
                            try {
                                //ResultsContainer r = gson.fromJson( result, ResultsContainer.class );
                                isDone = true;
                            }
                            catch( Exception e) {
                                String message = e.getMessage();

                                //Toast.makeText( mCallingContext, "error" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        // Read from the InputStream
                        //bytes = mmInStream.read( buffer );
                        //System.out.println("inRead");

                        // Send the obtained bytes to the UI Activity
                        byte[] msg = result.getBytes();
                        mHandler.obtainMessage( BluetoothMessageType.MESSAGE_READ, msg.length, -1, msg )
                                .sendToTarget();

                        mCurrentState = STATE_NONE;
                    }
                    else {
                        if( !dataSent ) {
                            mHandler.obtainMessage( BluetoothMessageType.MESSAGE_REQUEST, -1, -1, null )
                                    .sendToTarget();

                            //ResultsContainer resultsContainer = new ResultsContainer(getCurrentDeviceName(), mMatchResults, mPitScoutResults);
                            ResultsContainer resultsContainer = new ResultsContainer(getCurrentDeviceName(), NextUnsyncedResults );

                            // dburton convert to Jackson
                            //Gson gson = new Gson();
                            //String json = gson.toJson( resultsContainer );
                            String json = "{}";
                            // get the pit scout and match result data serialize into a string
                            write( json.getBytes() );
                            dataSent = true;
                            mHandler.obtainMessage( BluetoothMessageType.MESSAGE_SENT, -1, -1, null )
                                    .sendToTarget();
                        }
                    }
                } catch ( IOException e ) {
                    //Log.e(TAG, "disconnected", e);
                    connectionLost();
                    break;
                }

                try {
                    mCurrentState = STATE_NONE;
                    mmSocket.close();
                } catch ( IOException e ) {
                    // need a handler for sending an error message
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(BluetoothMessageType.MESSAGE_WRITE, buffer.length, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                //Toast.makeText( mCallingContext,"Exception during write", Toast.LENGTH_LONG).show();
            }
        }


        public void cancel() {
            try { mmSocket.close(); }
            catch (IOException e) {
                //Log.e(TAG, "close() of connect socket failed", e);
            }
        }


    }
}

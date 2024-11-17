package org.hartlandrobotics.echelonFTC.pitScouting;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.nsd.NsdManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.PitScout;
import org.hartlandrobotics.echelonFTC.database.entities.Team;
import org.hartlandrobotics.echelonFTC.utilities.FileUtilities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PitScoutPhotosFragment extends Fragment {
    public PitScoutPhotosFragment() {
        // Required empty public constructor
    }

    private static String TAG = "PitScoutPhotosFragment";
    private Button cameraButton;
    private Button nextPicture;
    private Button backPicture;
    private ViewPager robotImagesPager;
    RobotImage robotImageAdapter;
    PitScout data;
    private int teamNumber;

    ActivityResultLauncher<Intent> cameraActivityResultLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    public void setData(PitScout data){
        this.data = data;

        Log.e(TAG, "setData " + data.getTeamKey());
        teamNumber = Integer.parseInt(data.getTeamKey());
        populateImagesArea();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_photos, container, false);

        nextPicture = view.findViewById(R.id.nextPic);
        backPicture = view.findViewById(R.id.backPic);

        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    int resultCode = result.getResultCode();

                    if (resultCode == RESULT_OK) {
                                                    Log.e(TAG, "onActivityResult " + "before photo" );
                                                    Bitmap photo = (Bitmap) (result.getData().getExtras().get("data"));
                        Log.e(TAG, "onActivityResult " + photo );
                                                  SaveImage( photo );
                                                  //populateImagesArea();



                        //Bitmap photo = (Bitmap) data.getExtras().get( "data" );
                            //Log.e(TAG, "onActivityResult " + "before save " + photo.getByteCount() );


                    }
                });
        if(data != null) {
            teamNumber = Integer.parseInt(trimTeamNumber(data.getTeamKey()));
            SetupImagesArea(view);
            setupNextAndBackButton();
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private String trimTeamNumber(String teamKey){
        String safeTeamKey = StringUtils.defaultIfBlank(teamKey,StringUtils.EMPTY);

        return safeTeamKey.startsWith("frc") ? safeTeamKey.substring(3) : safeTeamKey;
    }

    public void setupNextAndBackButton(){
        nextPicture.setOnClickListener(view -> {
            robotImagesPager.setCurrentItem(Math.min(robotImagesPager.getCurrentItem() + 1, robotImageAdapter.getCount() - 1));
        });
        backPicture.setOnClickListener(view -> {
            robotImagesPager.setCurrentItem(Math.max(0, robotImagesPager.getCurrentItem() - 1));
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        populateImagesArea();
    }

    private void SetupImagesArea(View view){
        cameraButton = view.findViewById(R.id.photoButton);
        cameraButton.setOnClickListener(vw -> {
            try{
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraActivityResultLauncher.launch(cameraIntent);
                //startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
            catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Couldn't load photo", Toast.LENGTH_LONG).show();
            }
        });

        robotImagesPager = view.findViewById(R.id.picture_view_pager);
        robotImageAdapter = new RobotImage(getActivity().getApplicationContext(), teamNumber);
    }

    private void populateImagesArea(){
        if(robotImageAdapter == null) return;

        teamNumber = Integer.parseInt(trimTeamNumber(data.getTeamKey()));

        robotImageAdapter = new RobotImage(getActivity().getApplicationContext(), teamNumber);
        robotImagesPager.setAdapter(robotImageAdapter);
        robotImageAdapter.notifyDataSetChanged();
    }

    private static final int CAMERA_PIC_REQUEST = 22;

//    @Override
//    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
//        super.onActivityResult( requestCode, resultCode, data );
//        Log.e(TAG, "onActivityResult " + String.valueOf(requestCode) );
//        Log.e(TAG, "onActivityResult " + String.valueOf(requestCode) +" "+ CAMERA_PIC_REQUEST);
//        Log.e(TAG, "onActivityResult " + String.valueOf(resultCode) +" "+ RESULT_OK);
//
//
//        try {
//            switch ( requestCode ) {
//                case CAMERA_PIC_REQUEST:
//                    if ( resultCode == RESULT_OK ) {
//                        try {
//                            Log.e(TAG, "onActivityResult " + "before photo" );
//                            Bitmap photo = (Bitmap) data.getExtras().get( "data" );
//                            Log.e(TAG, "onActivityResult " + "before save " + photo.getByteCount() );
//
//                            SaveImage( photo );
//                            Log.e(TAG, "onActivityResult " + "after save" );
//
//                        } catch ( Exception e ) {
//                        }
//                    }
//                    break;
//                default:
//                    break;
//            }
//        } catch ( Exception e ) {
//            Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_LONG ).show();
//        }
//    }


    private void SaveImage(Bitmap finalBitmap) {
        Log.e(TAG ,"SaveImage " + "first line");

        PitScoutActivity fa = (PitScoutActivity)this.getActivity();
        AutoCompleteTextView teamNumberAutoComplete = fa.findViewById(R.id.teamSelectionAutoComplete);
        Editable et = teamNumberAutoComplete.getEditableText();
        String st = et.toString().split(" - ")[0];
        teamNumber = Integer.parseInt(st);


        //Team team = fa.currentTeam;
        //teamNumber = Integer.parseInt(data.getTeamKey());
        Log.e(TAG, "SaveImage data" + String.valueOf(data));

        Log.e(TAG, "SaveImage team n number" + String.valueOf(teamNumber));

        if( teamNumber == 0 ){
            Toast.makeText( getActivity(), "Please Select team first...", Toast.LENGTH_LONG).show();
            return;
        }

        File externalFilesDir = getImageFilePath( teamNumber );
        externalFilesDir.mkdirs();
        String path = externalFilesDir.getAbsolutePath();
        File[] files = getImageFiles( teamNumber );


        int nextNumber = getNextFileNumber( files );

        String nextFileName = String.format( "Image%03d.jpg", nextNumber );
        File file = new File( externalFilesDir, nextFileName );


        try {
            FileOutputStream out = new FileOutputStream( file );
            finalBitmap.compress( Bitmap.CompressFormat.JPEG, 90, out );
            out.flush();
            out.close();
        } catch ( Exception e ) {
            Toast t = Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_LONG );
            t.show();

        }

        //robotImagesPager = this.getView().findViewById(R.id.picture_view_pager);
        //robotImageAdapter = new RobotImage(getActivity().getApplicationContext(), teamNumber);

        populateImagesArea();

        //robotImageAdapter.notifyDataSetChanged();
    }

    private int getNextFileNumber(File[] files) {
        int largestNumber = Integer.MIN_VALUE;
        if ( files.length == 0 ) return 1;

        String[] fileNames = Arrays.stream( files )
                .map( (file) -> file.getName() )
                .toArray( String[]::new );

        for ( String currentFileName : fileNames ) {
            Matcher m = Pattern.compile( "\\d+" ).matcher( currentFileName );
            while ( m.find() ) {
                String numberString = m.group();
                largestNumber = Math.max( Integer.parseInt( numberString ), largestNumber );
            }
        }
        return largestNumber + 1;
    }

    private File[] getImageFiles(int teamNumber) {
        return getImageFilePath( teamNumber ).listFiles();
    }

    private File getImageFilePath(int teamNumber) {
        File fw = FileUtilities.ensureDirectory(getActivity().getApplicationContext(), "scouting_images/team_" + teamNumber );
        return fw;

    }

}
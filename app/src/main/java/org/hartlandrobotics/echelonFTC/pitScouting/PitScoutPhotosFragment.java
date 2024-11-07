package org.hartlandrobotics.echelonFTC.pitScouting;

import static android.app.Activity.RESULT_OK;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.PitScout;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    public void setData(PitScout data){
        this.data = data;

        populateImagesArea();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_photos, container, false);

        nextPicture = view.findViewById(R.id.nextPic);
        backPicture = view.findViewById(R.id.backPic);

        teamNumber = Integer.parseInt(trimTeamNumber(data.getTeamKey()));
        SetupImagesArea(view);
        setupNextAndBackButton();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    private String trimTeamNumber(String teamKey){
        String safeTeamKey = StringUtils.defaultIfBlank(teamKey,StringUtils.EMPTY);

        String teamNumber = safeTeamKey.startsWith("frc") ? safeTeamKey.substring(3) : safeTeamKey;

        return teamNumber;
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
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
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

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        try {
            switch ( requestCode ) {
                case CAMERA_PIC_REQUEST:
                    if ( resultCode == RESULT_OK ) {
                        try {
                            Bitmap photo = (Bitmap) data.getExtras().get( "data" );
                            SaveImage( photo );
                        } catch ( Exception e ) {
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch ( Exception e ) {
            Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_LONG );
        }
    }


    private void SaveImage(Bitmap finalBitmap) {
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
        robotImagesPager.getAdapter().notifyDataSetChanged();
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
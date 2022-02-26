package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.models.MatchResultViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ExportActivity extends EchelonActivity {

    private Button exportMatchResultsButton;

    public static void launch(Context context){
        Intent intent = new Intent( context, ExportActivity.class );
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        setupToolbar();

        exportMatchResultsButton = findViewById(R.id.exportMatchResults);
        exportMatchResults();
    }

    public void exportMatchResults(){
        exportMatchResultsButton.setOnClickListener((view) -> {
            Context appContext = getApplicationContext();
            BlueAllianceStatus status = new BlueAllianceStatus(appContext);
            File externalFilesDir = getFilePath();
            externalFilesDir.mkdirs();
            String path = externalFilesDir.getAbsolutePath();
            File[] files = getFilePaths();
            MatchResultViewModel matchResultViewModel = new MatchResultViewModel(getApplication());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
            Date date = new Date();
            String dateForFile = dateFormat.format(date);
            String fileName = "Match_Data_" + dateForFile + ".csv";
            File file = new File( externalFilesDir, fileName);

            matchResultViewModel.getMatchResultsByEvent(status.getEventKey()).observe(this, matchResults -> {
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    String header = "Event_Key,Match_Key,Team_Key,Auto_Taxi_Tarmac,Auto_High_Balls,Auto_Low_Balls,Auto_Human_Player_Shots,Teleop_High_Balls,Teleop_Low_Balls,End_Hang_Low,End_Hang_Mid,End_Hang_High,End_Hang_Traverse\n";
                    outputStream.write(header.getBytes());
                    for(MatchResult mr: matchResults){
                        List<String> dataForFile = new ArrayList<>();
                        dataForFile.add(mr.getEventKey());
                        dataForFile.add(mr.getMatchKey());
                        dataForFile.add(mr.getTeamKey());
                        dataForFile.add(String.valueOf(mr.getTaxiTarmac()));
                        dataForFile.add(String.valueOf(mr.getAutoHighBalls()));
                        dataForFile.add(String.valueOf(mr.getAutoLowBalls()));
                        dataForFile.add(String.valueOf(mr.getAutoHumanPlayerShots()));
                        dataForFile.add(String.valueOf(mr.getTeleOpHighBalls()));
                        dataForFile.add(String.valueOf(mr.getTeleOpLowBalls()));
                        dataForFile.add(String.valueOf(mr.getEndHangLow()));
                        dataForFile.add(String.valueOf(mr.getEndHangMid()));
                        dataForFile.add(String.valueOf(mr.getEndHangHigh()));
                        dataForFile.add(String.valueOf(mr.getEndHangTraverse()));
                        String outputString = dataForFile.stream().collect(Collectors.joining(",")) + "\n";
                        outputStream.write(outputString.getBytes());
                    }
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    Log.e("Try catch for outputstream", "Can't fine Outputstream file");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private File[] getFilePaths() {
        return getFilePath().listFiles();
    }

    private File getFilePath() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext() );
        return cw.getExternalFilesDir( "match_data");
    }
}
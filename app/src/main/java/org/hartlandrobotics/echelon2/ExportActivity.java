package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelon2.database.entities.Match;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.database.entities.MatchResultWithTeamMatch;
import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.database.entities.Team;
import org.hartlandrobotics.echelon2.models.MatchResultViewModel;
import org.hartlandrobotics.echelon2.models.PitScoutViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExportActivity extends EchelonActivity {

    private Button exportMatchResultsButton;
    private Button exportPitScoutResultsButton;
    private Button importCSVMatchButton;

    private MatchResultViewModel matchResultViewModel;

    public static void launch(Context context){
        Intent intent = new Intent( context, ExportActivity.class );
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        setupToolbar("Export Data");

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);

        exportMatchResultsButton = findViewById(R.id.exportMatchResults);
        exportMatchResults();
        exportPitScoutResultsButton = findViewById(R.id.exportPitScouting);
        exportPitScoutResults();
        importCSVMatchButton = findViewById(R.id.importMatchCSV);
        setupCSVImportButton();

    }

    public void exportMatchResults(){
        exportMatchResultsButton.setOnClickListener((view) -> {
            Context appContext = getApplicationContext();
            BlueAllianceStatus status = new BlueAllianceStatus(appContext);
            File externalFilesDir = getFilePathForMatch();
            externalFilesDir.mkdirs();
            String path = externalFilesDir.getAbsolutePath();
            File[] files = getFilePathsForMatch();
            MatchResultViewModel matchResultViewModel = new MatchResultViewModel(getApplication());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
            Date date = new Date();
            String dateForFile = dateFormat.format(date);
            String fileName = "Match_Data_" + dateForFile + ".csv";
            File file = new File( externalFilesDir, fileName);

            matchResultViewModel.getMatchResultsWithTeamMatchByEvent(status.getEventKey()).observe(this, matchResults -> {
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    String header = "Event_Key,Match_Key,Team_Key,Match_Number,Team_Number,Auto_Taxi_Tarmac,Auto_High_Balls,Auto_Low_Balls,Auto_Human_Player_Shots,Teleop_High_Balls,Teleop_Low_Balls,Teleop_Defenses,End_Hang_Low,End_Hang_Mid,End_Hang_High,End_Hang_Traverse,Match_Result_Key\n";
                    outputStream.write(header.getBytes());
                    for(MatchResultWithTeamMatch matchResultWithTeamMatch: matchResults){
                        MatchResult mr = matchResultWithTeamMatch.matchResult;
                        Match m = matchResultWithTeamMatch.match;
                        Team t = matchResultWithTeamMatch.team;

                        List<String> dataForFile = new ArrayList<>();
                        dataForFile.add(mr.getEventKey());
                        dataForFile.add(mr.getMatchKey());
                        dataForFile.add(mr.getTeamKey());
                        dataForFile.add(String.valueOf(m.getMatchNumber()));
                        dataForFile.add(String.valueOf(t.getTeamNumber()));
                        dataForFile.add(String.valueOf(mr.getTaxiTarmac()));
                        dataForFile.add(String.valueOf(mr.getAutoHighBalls()));
                        dataForFile.add(String.valueOf(mr.getAutoLowBalls()));
                        dataForFile.add(String.valueOf(mr.getAutoHumanPlayerShots()));
                        dataForFile.add(String.valueOf(mr.getTeleOpHighBalls()));
                        dataForFile.add(String.valueOf(mr.getTeleOpLowBalls()));
                        dataForFile.add(String.valueOf(mr.getDefenseCount()));
                        dataForFile.add(String.valueOf(mr.getEndHangLow()));
                        dataForFile.add(String.valueOf(mr.getEndHangMid()));
                        dataForFile.add(String.valueOf(mr.getEndHangHigh()));
                        dataForFile.add(String.valueOf(mr.getEndHangTraverse()));
                        dataForFile.add(mr.getMatchResultKey());
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

    public void exportPitScoutResults(){
        exportPitScoutResultsButton.setOnClickListener((view) -> {
            Context appContext = getApplicationContext();
            BlueAllianceStatus status = new BlueAllianceStatus(appContext);
            File externalFilesDir = getFilePathForPitScout();
            externalFilesDir.mkdirs();
            String path = externalFilesDir.getAbsolutePath();
            File[] files = getFilePathsForPitScout();
            PitScoutViewModel pitScoutViewModel = new PitScoutViewModel(getApplication());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
            Date date = new Date();
            String dateForFile = dateFormat.format(date);
            String fileName = "PitScout_Data_" + dateForFile + ".csv";
            File file = new File(externalFilesDir, fileName);

            pitScoutViewModel.getPitScoutByEvent(status.getEventKey()).observe(this, pitScoutResults -> {
                try{
                    FileOutputStream outputStream = new FileOutputStream(file);
                    String header = "Team_Key,Has_Autonomous,Help_With_Auto,Coding_Language,Shoots_Auto,Percent_Auto_Shots,Balls_Picked_Or_Shot_Auto,Can_Shoot,Shooting_Accuracy,Preferred_Goal,Can_Play_Defense,Can_Robot_Hang,Highest_Hang_Bar,Hang_Time,Preferred_Hanging_Spot,Side_Swing,Driver_Experience,Operator_Experience,Human_Player_Accuracy,Extra_Notes\n";
                    outputStream.write(header.getBytes());
                    for(PitScout ps: pitScoutResults){
                        List<String> psData = new ArrayList<>();
                        psData.add(ps.getTeamKey());
                        psData.add(String.valueOf(ps.getHasAutonomous()));
                        psData.add(String.valueOf(ps.getHelpCreatingAuto()));
                        psData.add(ps.getCodingLanguage());
                        psData.add(String.valueOf(ps.getShootsInAuto()));
                        psData.add(String.valueOf(ps.getPercentAutoShots()));
                        psData.add(String.valueOf(ps.getBallsPickedOrShotInAuto()));
                        psData.add(String.valueOf(ps.getCanShoot()));
                        psData.add(String.valueOf(ps.getShootingAccuracy()));
                        psData.add(ps.getPreferredGoal());
                        psData.add(String.valueOf(ps.getCanPlayDefense()));
                        psData.add(String.valueOf(ps.getCanRobotHang()));
                        psData.add(String.valueOf(ps.getHighestHangBar()));
                        psData.add(String.valueOf(ps.getHangTime()));
                        psData.add(ps.getPreferredHangingSpot());
                        psData.add(String.valueOf(ps.getSideSwing()));
                        psData.add(String.valueOf(ps.getDriverExperience()));
                        psData.add(String.valueOf(ps.getOperatorExperience()));
                        psData.add(String.valueOf(ps.getHumanPlayerAccuracy()));
                        psData.add(ps.getExtraNotes());
                        String outputString = psData.stream().collect(Collectors.joining(",")) + "\n";
                        outputStream.write(outputString.getBytes());
                    }
                    outputStream.close();
                }
                catch(Exception e){
                    Log.e("In Catch for Pit Scout", "Exception trying to export pitscout data", e);
                }
            });
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void importCSVMatches() throws IOException {
        File importPath = getImportPath();
        File newFile = new File(importPath.getAbsolutePath().concat("/matchResults.csv"));
        Stream<String> lines = Files.lines(newFile.toPath());
        List<String> inputLines = lines.collect(Collectors.toList());

        int timesRan = 0;
        for(int lineIndex = 1; lineIndex < inputLines.size(); lineIndex++){
            timesRan++;
            String currentLine = inputLines.get(lineIndex);
            String[] columns = currentLine.split(",");

            String eventKey = columns[0];
            String matchKey = columns[1];
            String teamKey = columns[2];
            int matchNum = Integer.parseInt(columns[3]);
            int teamNum = Integer.parseInt(columns[4]);
            boolean taxi = false;
            if(columns[5].equals("TRUE")) {
                taxi = true;
            }
            int autoHigh = Integer.parseInt(columns[6]);
            int autoLow = Integer.parseInt(columns[7]);
            int autoHuman = Integer.parseInt(columns[8]);
            int teleHigh = Integer.parseInt(columns[9]);
            int teleLow = Integer.parseInt(columns[10]);
            int teleDef = Integer.parseInt(columns[11]);
            boolean lowHang = false;
            if(columns[12].equals("TRUE")){
                lowHang = true;
            }
            boolean midHang = false;
            if(columns[13].equals("TRUE")){
                midHang = true;
            }
            boolean highHang = false;
            if(columns[14].equals("TRUE")){
                highHang = true;
            }
            boolean travHang = false;
            if(columns[15].equals("TRUE")){
                travHang = true;
            }
            String matchResultKey = columns[16];
            MatchResult matchResult = new MatchResult(matchResultKey, eventKey, matchKey, teamKey, false, taxi, autoHigh, autoLow, autoHuman, teleHigh, teleLow, lowHang, midHang, highHang, travHang, " ", teleDef);
            matchResultViewModel.upsert(matchResult);
        }
        Log.e("Test", "Times ran: " + timesRan);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setupCSVImportButton(){
        importCSVMatchButton.setOnClickListener(v -> {
            try{
                importCSVMatches();
                Log.e("under setupCSVImportButton", "imported match results");
                Toast.makeText(this, "imported matches", Toast.LENGTH_LONG).show();
            }
            catch(Exception E){
                E.printStackTrace();
            }
        });
    }

    public File getImportPath(){
        ContextWrapper cw = new ContextWrapper( getApplicationContext() );
        return cw.getExternalFilesDir( "imports");
    }

    private File[] getFilePathsForMatch() {
        return getFilePathForMatch().listFiles();
    }

    private File getFilePathForMatch() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext() );
        return cw.getExternalFilesDir( "match_data");
    }

    private File[] getFilePathsForPitScout(){
        return getFilePathForPitScout().listFiles();
    }

    private File getFilePathForPitScout(){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        return cw.getExternalFilesDir("pitscout_data");
    }
}
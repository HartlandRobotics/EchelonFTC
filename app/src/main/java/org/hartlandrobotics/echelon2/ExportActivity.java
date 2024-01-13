package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.ColumnInfo;

import org.hartlandrobotics.echelon2.database.entities.Match;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.database.entities.MatchResultWithTeamMatch;
import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.database.entities.Team;
import org.hartlandrobotics.echelon2.models.MatchResultViewModel;
import org.hartlandrobotics.echelon2.models.PitScoutViewModel;
import org.hartlandrobotics.echelon2.status.OrangeAllianceStatus;

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
            OrangeAllianceStatus status = new OrangeAllianceStatus(appContext);
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
                    String header = "Event_Key,Match_Key,Team_Key,Match_Name,Team_Number," +
                            "auto_park_backstage,auto_white_pxl_purple_pxl,auto_white_pxl_yellow_pxl,auto_team_purple-pxl,auto_team_yellow-pxl,auto_pxl_backdrop,auto_pxl_backstage," +
                            "teleOp_Pxl_Backstage,teleOp_Pxl_Backdrop,teleOp_Artist,teleOp_Set,teleOp_Drop," +
                            "end_park_backstage,end_suspended,end_landing_zone," +
                            "Match_Result_Key\n";

                    outputStream.write(header.getBytes());
                    for(MatchResultWithTeamMatch matchResultWithTeamMatch: matchResults){
                        MatchResult mr = matchResultWithTeamMatch.matchResult;
                        Match m = matchResultWithTeamMatch.match;
                        Team t = matchResultWithTeamMatch.team;

                        List<String> dataForFile = new ArrayList<>();
                        dataForFile.add(mr.getEventKey());
                        dataForFile.add(mr.getMatchKey());
                        dataForFile.add(mr.getTeamKey());
                        dataForFile.add(String.valueOf(m.getMatchName()));
                        dataForFile.add(String.valueOf(t.getTeamNumber()));

                        dataForFile.add(String.valueOf(mr.getAutoParkBackstage()));
                        dataForFile.add(String.valueOf(mr.getAutoWhitePxlPurplePxl()));
                        dataForFile.add(String.valueOf(mr.getAutoWhitePxlYellowPxl()));
                        dataForFile.add(String.valueOf(mr.getAutoTeamPurplePxl()));
                        dataForFile.add(String.valueOf(mr.getAutoTeamYellowPxl()));
                        dataForFile.add(String.valueOf(mr.getAutoPxlBackdrop()));
                        dataForFile.add(String.valueOf(mr.getAutoPxlBackstage()));
                        dataForFile.add(String.valueOf(mr.getTeleOpPxlBackstage()));
                        dataForFile.add(String.valueOf(mr.getTeleOpPxlBackdrop()));
                        dataForFile.add(String.valueOf(mr.getTeleOpArtist()));
                        dataForFile.add(String.valueOf(mr.getTeleOpSet()));
                        dataForFile.add(String.valueOf(mr.getTeleOpDrop()));
                        dataForFile.add(String.valueOf(mr.getEndParkBackstage()));
                        dataForFile.add(String.valueOf(mr.getEndSuspended()));
                        dataForFile.add(String.valueOf(mr.getEndLandingZone()));


                        dataForFile.add(mr.getMatchResultKey());
                        String outputString = dataForFile.stream().collect(Collectors.joining(",")) + "\n";
                        outputStream.write(outputString.getBytes());
                    }
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    Log.e("Try catch for outputstream", "Can't find Outputstream file");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public void exportPitScoutResults(){
        exportPitScoutResultsButton.setOnClickListener((view) -> {
            Context appContext = getApplicationContext();
            OrangeAllianceStatus status = new OrangeAllianceStatus(appContext);
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

    private static final int eventKeyIndex = 0;
    private static final int matchKeyIndex = 1;
    private static final int teamKeyIndex = 2;
    private static final int matchNumIndex = 3;
    private static final int teamNumIndex = 4;
    private static final int autoParkBackstageIndex = 5;
    private static final int autoWhitePxlPurplePxlIndex = 6;
    private static final int autoWhitePxlYellowPxlIndex = 7;
    private static final int autoTeamPurplePxlIndex = 8;
    private static final int autoTeamYellowPxlIndex = 9;
    private static final int autoPxlBackdropIndex = 10;
    private static final int autoPxlBackstageIndex = 11;
    private static final int teleOpPxlBackstageIndex = 12;
    private static final int teleOpPxlBackdropIndex = 13;
    private static final int teleOpSetIndex = 14;
    private static final int teleOpArtistIndex = 15;
    private static final int teleOpDropIndex = 16;
    private static final int endParkBackstageIndex = 17;
    private static final int endSuspendedIndex = 18;
    private static final int endLandingZoneIndex = 19;
    private static final int matchResultIndex = 20;

    private static final int additionalNotesIndex = 21;
    private static final int defenseCountIndex = 22;


    private String getStr(String[] columns, int columnIndex){
        return columns[columnIndex];
    }

    private int getInt(String[] columns, int columnIndex){
        return Integer.parseInt(columns[columnIndex]);
    }

    private boolean getBool(String[] columns, int columnIndex){
        boolean val = false;
        if(columns[columnIndex].equalsIgnoreCase("TRUE")) {
            val = true;
        }
        return val;
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

            String eventKey = getStr(columns, eventKeyIndex);
            String matchKey = getStr(columns, matchKeyIndex);
            String teamKey = getStr(columns, teamKeyIndex);
            int matchNum = getInt(columns, matchNumIndex);
            int teamNum = getInt(columns,teamNumIndex);

            boolean autoParkBackstage = getBool(columns, autoParkBackstageIndex);
            boolean autoWhitePxlPurplePxl = getBool(columns, autoWhitePxlPurplePxlIndex);
            boolean autoWhitePxlYellowPxl = getBool(columns, autoWhitePxlYellowPxlIndex);
            boolean autoTeamPurplePxl = getBool(columns, autoTeamPurplePxlIndex);
            boolean autoTeamYellowPxl = getBool(columns, autoTeamYellowPxlIndex);
            int autoPxlBackdrop = getInt(columns, autoPxlBackdropIndex);
            int autoPxlBackstage = getInt(columns, autoPxlBackstageIndex);

            int teleOpPxlBackstage = getInt(columns, teleOpPxlBackstageIndex);
            int teleOpPxlBackdrop = getInt(columns, teleOpPxlBackdropIndex);
            int teleOpArtist = getInt(columns, teleOpArtistIndex);
            int teleOpSet = getInt(columns, teleOpSetIndex);
            int teleOpDrop = getInt(columns, teleOpDropIndex);

            boolean endParkBackstage = getBool(columns, endParkBackstageIndex);
            boolean endSuspended = getBool(columns, endSuspendedIndex);
            int endLandingZone = getInt(columns, endLandingZoneIndex);
            String matchResultKey = getStr(columns, matchResultIndex);

            String additionalNotes = getStr(columns, additionalNotesIndex);
            int defenseCount = getInt(columns, defenseCountIndex);


            MatchResult matchResult = new MatchResult( matchResultKey,
                    eventKey,
                    matchKey,
                    teamKey,
                    false,
                    autoParkBackstage,
                    autoWhitePxlPurplePxl,
                    autoWhitePxlYellowPxl,
                    autoTeamPurplePxl,
                    autoTeamYellowPxl,
                    autoPxlBackdrop,
                    autoPxlBackstage,
                    teleOpPxlBackstage,
                    teleOpPxlBackdrop,
                    teleOpArtist,
                    teleOpSet,
                    teleOpDrop,
                    endParkBackstage,
                    endSuspended,
                    endLandingZone,
                   additionalNotes,
                   defenseCount
            );



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
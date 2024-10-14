package org.hartlandrobotics.echelonFTC;

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

//import org.hartlandrobotics.echelonFTC.database.currentGame.CurrentGameCounts;
import org.hartlandrobotics.echelonFTC.database.entities.Match;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResultWithTeamMatch;
import org.hartlandrobotics.echelonFTC.database.entities.PitScout;
import org.hartlandrobotics.echelonFTC.database.entities.Team;
import org.hartlandrobotics.echelonFTC.models.MatchResultViewModel;
import org.hartlandrobotics.echelonFTC.models.PitScoutViewModel;
import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;

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
        //exportMatchResults();
        setupExportCSVButton();
        exportPitScoutResultsButton = findViewById(R.id.exportPitScouting);
        exportPitScoutResults();
        importCSVMatchButton = findViewById(R.id.importMatchCSV);
        setupCSVImportButton();

    }

    public void exportMatchResults() throws RuntimeException {
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

            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                String header = "Match_Result_Key,Event_Key,Match_Key,Team_Key,has_been_synced"
                        + ",AutoFlag1 ,AutoFlag2, AutoFlag3, AutoFlag4, AutoFlag5"
                        + ",AutoInt6 ,AutoInt7 ,AutoInt8 ,AutoInt9 ,AutoInt10"
                        + ",TeleOpInt1,TeleOpInt2,TeleOpInt3, TeleOpInt4, TeleOpInt5"
                        + ",EndFlag1,EndFlag2,EndFlag3,EndFlag4, EndInt6"
                        + ",AdditionalNotes, DefensesCount\n";
                outputStream.write(header.getBytes());
                for (MatchResultWithTeamMatch matchResultWithTeamMatch : matchResults) {

                    MatchResult mr = matchResultWithTeamMatch.matchResult;
                    //Match m = matchResultWithTeamMatch.match;
                    //Team t = matchResultWithTeamMatch.team;

                    List<String> dataForFile = new ArrayList<>();
                    dataForFile.add(mr.getMatchResultKey());
                    dataForFile.add(mr.getEventKey());
                    dataForFile.add(mr.getMatchKey());
                    dataForFile.add(mr.getTeamKey());
                    dataForFile.add(String.valueOf(mr.getHasBeenSynced()));
                    //dataForFile.add(String.valueOf(m.getMatchName()));
                    //dataForFile.add(String.valueOf(t.getTeamNumber()));

                    dataForFile.add(String.valueOf(mr.getAutoFlag1()));
                    dataForFile.add(String.valueOf(mr.getAutoFlag2()));
                    dataForFile.add(String.valueOf(mr.getAutoFlag3()));
                    dataForFile.add(String.valueOf(mr.getAutoFlag4()));
                    dataForFile.add(String.valueOf(mr.getAutoFlag5()));

                    dataForFile.add(String.valueOf(mr.getAutoInt6()));
                    dataForFile.add(String.valueOf(mr.getAutoInt7()));
                    dataForFile.add(String.valueOf(mr.getAutoInt8()));
                    dataForFile.add(String.valueOf(mr.getAutoInt9()));
                    dataForFile.add(String.valueOf(mr.getAutoInt10()));

                    dataForFile.add(String.valueOf(mr.getTeleOpInt1()));
                    dataForFile.add(String.valueOf(mr.getTeleOpInt2()));
                    dataForFile.add(String.valueOf(mr.getTeleOpInt3()));
                    dataForFile.add(String.valueOf(mr.getTeleOpInt4()));
                    dataForFile.add(String.valueOf(mr.getTeleOpInt5()));

                    dataForFile.add(String.valueOf(mr.getEndFlag1()));
                    dataForFile.add(String.valueOf(mr.getEndFlag2()));
                    dataForFile.add(String.valueOf(mr.getEndFlag3()));
                    dataForFile.add(String.valueOf(mr.getEndFlag4()));
                    dataForFile.add(String.valueOf(mr.getEndInt6()));

                    dataForFile.add("test");//(mr.getAdditionalNotes());
                    dataForFile.add(String.valueOf(mr.getDefenseCount()));

                    String outputString = dataForFile.stream().collect(Collectors.joining(",")) + "\n";
                    outputStream.write(outputString.getBytes());
                }
                outputStream.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public void setupExportCSVButton(){
        exportMatchResultsButton.setOnClickListener((view) -> {
            try {
                exportMatchResults();
                Toast.makeText(this, "export Matches: ", Toast.LENGTH_LONG).show();

            } catch (RuntimeException e) {
                String message = e.getLocalizedMessage();
                Toast.makeText(this, "export Matches error: " + message, Toast.LENGTH_LONG).show();
            }
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
                    String header = "Team_Key" +
                            ",Has_Autonomous,Help_With_Auto,Coding_Language,Shoots_Auto,Percent_Auto_Shots,Balls_Picked_Or_Shot_Auto" +
                            ",Can_Shoot,Shooting_Accuracy,Preferred_Goal,Can_Play_Defense" +
                            ",Can_Robot_Hang,Highest_Hang_Bar,Hang_Time,Preferred_Hanging_Spot,Side_Swing" +
                            ",Driver_Experience,Operator_Experience,Human_Player_Accuracy,Extra_Notes\n";
                    outputStream.write(header.getBytes());
                    for(PitScout ps: pitScoutResults){
                        List<String> psData = new ArrayList<>();
                        psData.add(ps.getTeamKey());
                        psData.add(String.valueOf(ps.getHasAutonomous()));

                        psData.add(String.valueOf(ps.getCanRobotHang()));
                        psData.add(String.valueOf(ps.getHighestHangBar()));
                        psData.add(String.valueOf(ps.getHangTime()));
                        psData.add(ps.getPreferredHangingSpot());
                        psData.add(String.valueOf(ps.getSideSwing()));
                        psData.add(ps.getExtraNotes());
                        String outputString = psData.stream().collect(Collectors.joining(",")) + "\n";
                        outputStream.write(outputString.getBytes());
                    }
                    outputStream.close();

                    Toast.makeText(this, "export Pit Scout: ", Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    Log.e("In Catch for Pit Scout", "Exception trying to export pitscout data", e);
                    String message = e.getLocalizedMessage();
                    Toast.makeText(this, "export Pit Scout error: " + message, Toast.LENGTH_LONG).show();
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
            //String header = "Event_Key,Match_Key,Team_Key"

            String header = "Match_Result_Key, Event_Key,Match_Key,Team_Key,has_been_synced"
                    + ",AutoFlag1 ,AutoFlag2, AutoFlag3, AutoFlag4, AutoFlag5"
                    + ",AutoInt6 ,AutoInt7 ,AutoInt8 ,AutoInt9 ,AutoInt10"
                    + ",TeleOpInt1,TeleOpInt2,TeleOpInt3, TeleOpInt4, TeleOpInt5"
                    + ",EndFlag1,EndFlag2,EndFlag3,EndFlag4, EndInt6"
                    //+ ",DefensesCount,Match_Result_Key"
                    + ", AdditionalNotes\n";

            String matchResultKey = columns[0];
            String eventKey = columns[1];
            String matchKey = columns[2];
            String teamKey = columns[3];
            String has_been_synced = columns[4];

            String AutoFlag1 = columns[5];
            String AutoFlag2 = columns[6];
            String AutoFlag3 = columns[7];
            String AutoFlag4 = columns[8];
            String AutoFlag5 = columns[9];

            String AutoInt6 = columns[10];
            String AutoInt7 = columns[11];
            String AutoInt8 = columns[12];
            String AutoInt9 = columns[13];
            String AutoInt10 = columns[14];

            String TeleOpInt1 =columns[15];
            String TeleOpInt2 =columns[16];
            String TeleOpInt3 =columns[17];
            String TeleOpInt4 =columns[18];
            String TeleOpInt5 =columns[19];

            String EndFlag1 =columns[20];
            String EndFlag2 =columns[21];
            String EndFlag3 =columns[22];
            String EndFlag4 =columns[23];
            String EndInt6 =columns[24];

            String teleDef = "0";
            //String matchResultKey = columns[26];
            String AdditionalNotes = columns[25];

            MatchResult matchResult = new MatchResult(
                    matchResultKey,
                    eventKey,
                    matchKey,
                    teamKey,
                    false,

                    AutoFlag1.equalsIgnoreCase("true"),
                    AutoFlag2.equalsIgnoreCase("true"),
                    AutoFlag3.equalsIgnoreCase("true"),
                    AutoFlag4.equalsIgnoreCase("true"),
                    AutoFlag5.equalsIgnoreCase("true"),

                    Integer.parseInt(AutoInt6),
                    Integer.parseInt(AutoInt7),
                    Integer.parseInt(AutoInt8),
                    Integer.parseInt(AutoInt9),
                    Integer.parseInt(AutoInt10),

                    Integer.parseInt(TeleOpInt1),
                    Integer.parseInt(TeleOpInt2),
                    Integer.parseInt(TeleOpInt3),
                    Integer.parseInt(TeleOpInt4),
                    Integer.parseInt(TeleOpInt5),

                    EndFlag1.equalsIgnoreCase("true"),
                    EndFlag2.equalsIgnoreCase("true"),
                    EndFlag3.equalsIgnoreCase("true"),
                    EndFlag4.equalsIgnoreCase("true"),
                    Integer.parseInt(EndInt6),


                    AdditionalNotes,
                    //,
                    Integer.parseInt(teleDef)
            );
            matchResultViewModel.upsert(matchResult);
        }
        Log.e(this.getLocalClassName(), "Times ran: " + timesRan);
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
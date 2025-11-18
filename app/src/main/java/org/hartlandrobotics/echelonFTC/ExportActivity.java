package org.hartlandrobotics.echelonFTC;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

//import org.hartlandrobotics.echelonFTC.database.currentGame.CurrentGameCounts;
import org.apache.commons.lang3.StringEscapeUtils;
import org.hartlandrobotics.echelonFTC.bluetooth.BluetoothSyncService;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.database.entities.Match;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResultWithTeamMatch;
import org.hartlandrobotics.echelonFTC.database.entities.PitScout;
import org.hartlandrobotics.echelonFTC.database.entities.Team;
import org.hartlandrobotics.echelonFTC.models.MatchResultViewModel;
import org.hartlandrobotics.echelonFTC.models.PitScoutViewModel;
import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;
import org.hartlandrobotics.echelonFTC.utilities.DeviceUtilities;
import org.hartlandrobotics.echelonFTC.utilities.FileUtilities;

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

    private Button exportToTableaButton;
    private Button exportCaptainMatchResultsButton;
    private Button exportRed1MatchResultsButton;
    private Button exportRed2MatchResultsButton;
    private Button exportBlue1MatchResultsButton;
    private Button exportBlue2MatchResultsButton;
    private Button exportPitScoutResultsButton;
    private Button importCSVMatchButton;

    private MatchResultViewModel matchResultViewModel;
    AdminSettings adminSettings;
    String role;
    DeviceUtilities utilities;

    public static void launch(Context context) {
        Intent intent = new Intent(context, ExportActivity.class);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        setupToolbar("Export Data");

        adminSettings = AdminSettingsProvider.getAdminSettings(this);
        role = adminSettings.getDeviceRole();


        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);

        exportCaptainMatchResultsButton = findViewById(R.id.exportCaptainMatchResults);
        exportToTableaButton = findViewById(R.id.exportToTableu);

        Context appContext = getApplicationContext();
        String deviceName = DeviceUtilities.getDeviceName(appContext);
        if (!deviceName.contains("aptain") && !deviceName.contains("oach")) {
            exportCaptainMatchResultsButton.setVisibility(View.GONE);
            exportToTableaButton.setVisibility(View.GONE);
        }
        //if (!deviceName.contains("aptain")) {
        //    exportCaptainMatchResultsButton.setVisibility(View.GONE);
        //}

        exportRed1MatchResultsButton = findViewById(R.id.exportRed1MatchResults);
        if (!role.equalsIgnoreCase("Red1") || deviceName.contains("aptain") ) {
            exportRed1MatchResultsButton.setVisibility(View.GONE);
        }
        exportRed2MatchResultsButton = findViewById(R.id.exportRed2MatchResults);
        if (!role.equalsIgnoreCase("Red2")) {
            exportRed2MatchResultsButton.setVisibility(View.GONE);
        }

        exportBlue1MatchResultsButton = findViewById(R.id.exportBlue1MatchResults);
        if (!role.equalsIgnoreCase("Blue1")) {
            exportBlue1MatchResultsButton.setVisibility(View.GONE);
        }

        exportBlue2MatchResultsButton = findViewById(R.id.exportBlue2MatchResults);
        if (!role.equalsIgnoreCase("Blue2")) {
            exportBlue2MatchResultsButton.setVisibility(View.GONE);
        }


        //exportMatchResults();
        setupExportCSVButton();
        exportPitScoutResultsButton = findViewById(R.id.exportPitScouting);
        //exportPitScoutResults();
        importCSVMatchButton = findViewById(R.id.importMatchCSV);
        setupCSVImportButton();

    }

    public void exportMatchResults(String fileName) throws RuntimeException {
        Context appContext = getApplicationContext();
        OrangeAllianceStatus status = new OrangeAllianceStatus(appContext);
        File externalFilesDir = getFilePathForMatch();
        externalFilesDir.mkdirs();
        MatchResultViewModel matchResultViewModel = new MatchResultViewModel(getApplication());
        File file = new File(externalFilesDir, fileName);

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

                    dataForFile.add(StringEscapeUtils.escapeCsv(mr.getAdditionalNotes().trim()));
                    //dataForFile.add("test");//(mr.getAdditionalNotes());
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

    public void exportMatchResultsToTableu(String fileName) throws RuntimeException {
        Context appContext = getApplicationContext();
        OrangeAllianceStatus status = new OrangeAllianceStatus(appContext);
        File externalFilesDir = getFilePathForMatch();
        externalFilesDir.mkdirs();
        MatchResultViewModel matchResultViewModel = new MatchResultViewModel(getApplication());
        File file = new File(externalFilesDir, fileName);

        matchResultViewModel.getMatchResultsWithTeamMatchByEvent(status.getEventKey()).observe(this, matchResults -> {

            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                String header = "Match_Result_Key,Event_Key,Match_Key,Team_Key,has_been_synced"
                        + ",Auto_Leave ,Auto_NotUsed1, Auto_NotUsed2, Auto_NotUsed3, Auto_NotUsed4"
                        + ",Auto_Artifact_Classified ,Auto_Artifact_Overflow ,Auto_Motif ,Auto_Pattern ,Auto_Missed"
                        + ",TeleOp_Artifact_Classified,TeleOp_Artifact_Overflow,TeleOp_Artifact_Depot, TeleOp_Pattern, TeleOp_Missed"
                        + ",End_BaseReturnString, End_Base_TwoBots,End_NotUsed1,End_NotUsed2,End_NotUsed3, End_BaseReturn"
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
                    //dataForFile.add(String.valueOf(mr.getAutoInt9()));
                    int autoPattern = mr.getAutoInt9();
                    if(autoPattern == 0){
                        dataForFile.add("GPP");
                    }else if(autoPattern==1){
                        dataForFile.add("PGP");
                    }else if(autoPattern==2) {
                        dataForFile.add("PPG");
                    }else{
                        dataForFile.add("not 0:1:2");
                    }
                    dataForFile.add(String.valueOf(mr.getAutoInt10()));

                    dataForFile.add(String.valueOf(mr.getTeleOpInt1()));
                    dataForFile.add(String.valueOf(mr.getTeleOpInt2()));
                    dataForFile.add(String.valueOf(mr.getTeleOpInt3()));
                    dataForFile.add(String.valueOf(mr.getTeleOpInt4()));
                    dataForFile.add(String.valueOf(mr.getTeleOpInt5()));

                    int endBaseReturn = mr.getEndInt6();
                    if(endBaseReturn == 0){
                        dataForFile.add("none");
                    }else if(endBaseReturn==1){
                        dataForFile.add("partial");
                    }else if(endBaseReturn==2) {
                        dataForFile.add("full");
                    }else{
                        dataForFile.add("not 0:1:2");
                    }
                    dataForFile.add(String.valueOf(mr.getEndFlag1()));
                    dataForFile.add(String.valueOf(mr.getEndFlag2()));
                    dataForFile.add(String.valueOf(mr.getEndFlag3()));
                    dataForFile.add(String.valueOf(mr.getEndFlag4()));
                    dataForFile.add(String.valueOf(mr.getEndInt6()));

                    dataForFile.add(StringEscapeUtils.escapeCsv(mr.getAdditionalNotes().trim()));
                    //dataForFile.add("test");//(mr.getAdditionalNotes());
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

    public void setupExportCSVButton() {

        exportCaptainMatchResultsButton.setOnClickListener((view) -> {
            try {
                exportMatchResults("matchResultsCaptain.csv");
                Toast.makeText(this, "export Matches: ", Toast.LENGTH_LONG).show();

            } catch (RuntimeException e) {
                String message = e.getLocalizedMessage();
                Toast.makeText(this, "export Matches error: " + message, Toast.LENGTH_LONG).show();
            }
        });
        exportToTableaButton.setOnClickListener((view) -> {
            try {
                exportMatchResultsToTableu("matchResultsTableu.csv");
                Toast.makeText(this, "export Matches: ", Toast.LENGTH_LONG).show();

            } catch (RuntimeException e) {
                String message = e.getLocalizedMessage();
                Toast.makeText(this, "export Matches error: " + message, Toast.LENGTH_LONG).show();
            }
        });
        exportRed1MatchResultsButton.setOnClickListener((view) -> {
            try {
                exportMatchResults("matchResultsRed1.csv");
                Toast.makeText(this, "export Matches: " + "matchResultsRed1", Toast.LENGTH_LONG).show();

            } catch (RuntimeException e) {
                String message = e.getLocalizedMessage();
                Toast.makeText(this, "export Matches error: " + message, Toast.LENGTH_LONG).show();
            }
        });
        exportRed2MatchResultsButton.setOnClickListener((view) -> {
            try {
                exportMatchResults("matchResultsRed2.csv");
                Toast.makeText(this, "export Matches: " + "matchResultsRed2", Toast.LENGTH_LONG).show();

            } catch (RuntimeException e) {
                String message = e.getLocalizedMessage();
                Toast.makeText(this, "export Matches error: " + message, Toast.LENGTH_LONG).show();
            }
        });
        exportBlue1MatchResultsButton.setOnClickListener((view) -> {
            try {
                exportMatchResults("matchResultsBlue1.csv");
                Toast.makeText(this, "export Matches: " + "matchResultsBlue1", Toast.LENGTH_LONG).show();

            } catch (RuntimeException e) {
                String message = e.getLocalizedMessage();
                Toast.makeText(this, "export Matches error: " + message, Toast.LENGTH_LONG).show();
            }
        });
        exportBlue2MatchResultsButton.setOnClickListener((view) -> {
            try {
                exportMatchResults("matchResultsBlue2.csv");
                Toast.makeText(this, "export Matches: " + "matchResultsBlue2", Toast.LENGTH_LONG).show();

            } catch (RuntimeException e) {
                String message = e.getLocalizedMessage();
                Toast.makeText(this, "export Matches error: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void exportPitScoutResults() {
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
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    String header = "Team_Key" +
                            ",Has_Autonomous,Help_With_Auto,Coding_Language,Shoots_Auto,Percent_Auto_Shots,Balls_Picked_Or_Shot_Auto" +
                            ",Can_Shoot,Shooting_Accuracy,Preferred_Goal,Can_Play_Defense" +
                            ",Can_Robot_Hang,Highest_Hang_Bar,Hang_Time,Preferred_Hanging_Spot,Side_Swing" +
                            ",Driver_Experience,Operator_Experience,Human_Player_Accuracy,Extra_Notes\n";
                    outputStream.write(header.getBytes());
                    for (PitScout ps : pitScoutResults) {
                        List<String> psData = new ArrayList<>();
                        psData.add(ps.getTeamKey());
                        psData.add(String.valueOf(ps.getHasAutonomous()));

                        psData.add(String.valueOf(ps.getEndHangTime()));

                        psData.add(ps.getExtraNotes());
                        String outputString = psData.stream().collect(Collectors.joining(",")) + "\n";
                        outputStream.write(outputString.getBytes());
                    }
                    outputStream.close();

                    Toast.makeText(this, "export Pit Scout: ", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
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
        String[] filePaths = {"matchResultsRed1.csv", "matchResultsRed2.csv", "matchResultsBlue1.csv", "matchResultsBlue2.csv", "matchResultsCaptain.csv", "matchResultsCaptain2.csv"};
        for (String fileName : filePaths) {
            File newFile = new File(importPath.getAbsolutePath().concat("/" + fileName));
            if(!newFile.exists()){
                continue;
            }
            Stream<String> lines = Files.lines(newFile.toPath());
            List<String> inputLines = lines.collect(Collectors.toList());

            for (int lineIndex = 1; lineIndex < inputLines.size(); lineIndex++) {
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

                String TeleOpInt1 = columns[15];
                String TeleOpInt2 = columns[16];
                String TeleOpInt3 = columns[17];
                String TeleOpInt4 = columns[18];
                String TeleOpInt5 = columns[19];

                String EndFlag1 = columns[20];
                String EndFlag2 = columns[21];
                String EndFlag3 = columns[22];
                String EndFlag4 = columns[23];
                String EndInt6 = columns[24];

                String teleDef = "0";
                //String matchResultKey = columns[26];
                String AdditionalNotes = StringEscapeUtils.unescapeCsv(columns[25]);

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
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setupCSVImportButton() {
        importCSVMatchButton.setOnClickListener(v -> {
            try {
                importCSVMatches();
                Log.e("under setupCSVImportButton", "imported match results");
                Toast.makeText(this, "imported matches", Toast.LENGTH_LONG).show();
            } catch (Exception E) {
                E.printStackTrace();
            }
        });
    }

    //String transferString = "transfer";

    public File getImportPath() {
        return FileUtilities.ensureDirectory(getApplicationContext(),  "transfer");
    }

    private File[] getFilePathsForMatch() {
        return getFilePathForMatch().listFiles();
    }

    private File getFilePathForMatch() {

        return FileUtilities.ensureDirectory(getApplicationContext(), "transfer");
    }

    private File[] getFilePathsForPitScout() {
        return getFilePathForPitScout().listFiles();
    }

    private File getFilePathForPitScout() {
        return FileUtilities.ensureDirectory(getApplicationContext(), "pitscout_data");
    }
}
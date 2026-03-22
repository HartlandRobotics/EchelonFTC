package org.hartlandrobotics.echelonFTC.database;

import android.content.Context;

import org.hartlandrobotics.echelonFTC.database.dao.MatchScoreDao;
import org.hartlandrobotics.echelonFTC.database.dao.OprDao;
import org.hartlandrobotics.echelonFTC.database.dao.RgnDao;
import org.hartlandrobotics.echelonFTC.database.dao.RgnWithEventsDao;
import org.hartlandrobotics.echelonFTC.database.dao.EvtWithMatchesDao;
import org.hartlandrobotics.echelonFTC.database.dao.EvtWithTeamsDao;
import org.hartlandrobotics.echelonFTC.database.dao.MatchDao;
import org.hartlandrobotics.echelonFTC.database.dao.MatchResultDao;
import org.hartlandrobotics.echelonFTC.database.dao.PitScoutDao;
import org.hartlandrobotics.echelonFTC.database.dao.SeasonDao;
import org.hartlandrobotics.echelonFTC.database.dao.TeamDao;
import org.hartlandrobotics.echelonFTC.database.entities.MatchScore;
import org.hartlandrobotics.echelonFTC.database.entities.Opr;
import org.hartlandrobotics.echelonFTC.database.entities.Rgn;
import org.hartlandrobotics.echelonFTC.database.entities.RgnEvtCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.Evt;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import org.hartlandrobotics.echelonFTC.database.dao.EvtDao;
import org.hartlandrobotics.echelonFTC.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.EvtTeamCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.Match;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.database.entities.PitScout;
import org.hartlandrobotics.echelonFTC.database.entities.Season;
import org.hartlandrobotics.echelonFTC.database.entities.Team;
import org.hartlandrobotics.echelonFTC.database.entities.MatchScore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Evt.class,
        Rgn.class,
        Team.class,
        PitScout.class,
        Season.class,
        EvtTeamCrossRef.class,
        EvtMatchCrossRef.class,
        RgnEvtCrossRef.class,
        Match.class,
        MatchResult.class,
        MatchScore.class,
        Opr.class

}, version = 8,
        exportSchema = false
)
public abstract class EchelonDatabase extends RoomDatabase {
    public abstract EvtDao eventDao();
    public abstract TeamDao teamDao();
    public abstract RgnDao regionDao();
    public abstract PitScoutDao pitScoutDao();
    public abstract SeasonDao seasonDao();
    public abstract EvtWithTeamsDao eventTeamsDao();
    public abstract EvtWithMatchesDao eventMatchesDao();
    public abstract RgnWithEventsDao districtEventsDao();
    public abstract MatchDao matchDao();
    public abstract MatchResultDao matchResultDao();
    public abstract MatchScoreDao matchScoreDao();
    public abstract OprDao oprDao();


    private static volatile EchelonDatabase _instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static EchelonDatabase getDatabase(final Context context){
        if(_instance == null){
            synchronized ( EchelonDatabase.class){
                if(_instance == null){
                    _instance = Room.databaseBuilder(context.getApplicationContext(),
                            EchelonDatabase.class, "echelon_ftc_database.db")
                            .fallbackToDestructiveMigration()
                            .setJournalMode(JournalMode.TRUNCATE)
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return _instance;
    }



    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);

            //databaseWriteExecutor.execute(() -> {
             //   Season intoTheDeepSeason = new Season("Center Stage", 2324);
             //   SeasonDao sd = _instance.seasonDao();
             //   sd.insert(intoTheDeepSeason);
            //});


            databaseWriteExecutor.execute(() -> {
                Season intoTheDeepSeason = new Season("Into the Deep", 2024);
                SeasonDao sd = _instance.seasonDao();
                sd.insert(intoTheDeepSeason);
            });

            databaseWriteExecutor.execute(() -> {
                Season decodeSeason = new Season("Decode", 2025);
                SeasonDao sd = _instance.seasonDao();
                sd.insert(decodeSeason);
            });

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){

            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                //any initialization stuff goes here
                EvtDao evtDao = _instance.eventDao();
                TeamDao teamDao = _instance.teamDao();
                RgnDao rgnDao = _instance.regionDao();
                PitScoutDao pitScoutDao = _instance.pitScoutDao();
                SeasonDao seasonDao = _instance.seasonDao();
                MatchResultDao matchResultDao = _instance.matchResultDao();
                MatchScoreDao matchScoreDao = _instance.matchScoreDao();
                OprDao oprDao = _instance.oprDao();
            } );
        }
    };
}
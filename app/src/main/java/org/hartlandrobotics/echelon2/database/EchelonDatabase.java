package org.hartlandrobotics.echelon2.database;

import android.content.Context;

import org.hartlandrobotics.echelon2.database.dao.DistrictDao;
import org.hartlandrobotics.echelon2.database.dao.DistrictWithEventsDao;
import org.hartlandrobotics.echelon2.database.dao.EvtWithMatchesDao;
import org.hartlandrobotics.echelon2.database.dao.EvtWithTeamsDao;
import org.hartlandrobotics.echelon2.database.dao.MatchDao;
import org.hartlandrobotics.echelon2.database.dao.PitScoutDao;
import org.hartlandrobotics.echelon2.database.dao.SeasonDao;
import org.hartlandrobotics.echelon2.database.dao.TeamDao;
import org.hartlandrobotics.echelon2.database.entities.District;
import org.hartlandrobotics.echelon2.database.entities.DistrictEvtCrossRef;
import org.hartlandrobotics.echelon2.database.entities.Evt;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import org.hartlandrobotics.echelon2.database.dao.EvtDao;
import org.hartlandrobotics.echelon2.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelon2.database.entities.EvtTeamCrossRef;
import org.hartlandrobotics.echelon2.database.entities.Match;
import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.database.entities.Season;
import org.hartlandrobotics.echelon2.database.entities.Team;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Evt.class,
        District.class,
        Team.class,
        PitScout.class,
        Season.class,
        EvtTeamCrossRef.class,
        EvtMatchCrossRef.class,
        DistrictEvtCrossRef.class,
        Match.class

}, version = 7,
        exportSchema = false
)
public abstract class EchelonDatabase extends RoomDatabase {
    public abstract EvtDao eventDao();
    public abstract TeamDao teamDao();
    public abstract DistrictDao districtDao();
    public abstract PitScoutDao pitScoutDao();
    public abstract SeasonDao seasonDao();
    public abstract EvtWithTeamsDao eventTeamsDao();
    public abstract EvtWithMatchesDao eventMatchesDao();
    public abstract DistrictWithEventsDao districtEventsDao();
    public abstract MatchDao matchDao();


    private static volatile EchelonDatabase _instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static EchelonDatabase getDatabase(final Context context){
        if(_instance == null){
            synchronized ( EchelonDatabase.class){
                if(_instance == null){
                    _instance = Room.databaseBuilder(context.getApplicationContext(),
                            EchelonDatabase.class, "echelon_database")
                            .fallbackToDestructiveMigration()
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

            databaseWriteExecutor.execute(() -> {
                Season s1 = new Season("Rapid React", 2022);
                Season s2 = new Season("Infinite Recharge", 2020);
                SeasonDao sd = _instance.seasonDao();
                sd.insert(s1);
                sd.insert(s2);
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){

            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                //any initialization stuff goes here
                //DistrictDao districtDao = _instance.districtDao();
                EvtDao evtDao = _instance.eventDao();
                TeamDao teamDao = _instance.teamDao();
                DistrictDao districtDao = _instance.districtDao();
                PitScoutDao pitScoutDao = _instance.pitScoutDao();
                SeasonDao seasonDao = _instance.seasonDao();
            } );
        }
    };
}
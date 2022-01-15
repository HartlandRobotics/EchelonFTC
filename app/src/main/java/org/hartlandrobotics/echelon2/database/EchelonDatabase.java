package org.hartlandrobotics.echelon2.database;

import android.content.Context;

import org.hartlandrobotics.echelon2.database.dao.DistrictDao;
import org.hartlandrobotics.echelon2.database.dao.TeamDao;
import org.hartlandrobotics.echelon2.database.entities.District;
import org.hartlandrobotics.echelon2.database.entities.Evt;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import org.hartlandrobotics.echelon2.database.dao.EvtDao;
import org.hartlandrobotics.echelon2.database.entities.Team;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Evt.class,
        District.class,
        Team.class

}, version = 2,
        exportSchema = false
)
public abstract class EchelonDatabase extends RoomDatabase {
    public abstract EvtDao eventDao();
    public abstract TeamDao teamDao();
    public abstract DistrictDao districtDao();

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
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                //any initialization stuff goes here
                //DistrictDao districtDao = _instance.districtDao();
                EvtDao eventDao = _instance.eventDao();
            } );
        }
    };
}
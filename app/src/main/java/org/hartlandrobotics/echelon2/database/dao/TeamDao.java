package org.hartlandrobotics.echelon2.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelon2.database.entities.Team;

import java.util.List;

@Dao
public abstract class TeamDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Team team);

    @Transaction
    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Team team);

    @Transaction
    @Query("SELECT * FROM team")
    public abstract LiveData<List<Team>> getTeams();

    @Transaction
    @Query("SELECT * FROM team WHERE team_key = :teamKey")
    public abstract LiveData<Team> getTeams(String teamKey);

    @Transaction
    public void upsert(Team team){
        long id = insert(team);
        if(id == -1){
            update(team);
        }
    }

}

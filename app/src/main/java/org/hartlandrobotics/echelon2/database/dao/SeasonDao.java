package org.hartlandrobotics.echelon2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import org.hartlandrobotics.echelon2.database.entities.Season;
import java.util.List;

@Dao
public abstract class SeasonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Season season);

    @Query("SELECT * FROM season")
    public abstract LiveData<List<Season>> getSeasons();
}

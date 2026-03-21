package org.hartlandrobotics.echelonFTC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;

@Entity(tableName="match_score")
public class MatchScore {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "match_score_key")
    private String matchScoreKey;

    @NonNull
    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "event_code")
    private String eventCode;

    @ColumnInfo(name = "match_number")
    private int matchNumber;

    @ColumnInfo(name="tournamentLevel")
    private String tournamentLevel;

    @ColumnInfo(name="match_played")
    private boolean played;

    @ColumnInfo(name="red_total")
    private int redTotal;

    @ColumnInfo(name = "red_penalty")
    private int redPenalty;

    @ColumnInfo(name="blue_total")
    private int blueTotal;

    @ColumnInfo(name = "blue_penalty")
    private int bluePenalty;

    public MatchScore( int year,
                       String eventCode,
                       int matchNumber,
                       String tournamentLevel,
                       boolean played,
                       int redTotal,
                       int redPenalty,
                       int blueTotal,
                       int bluePenalty){
        this.matchScoreKey = StringUtils.EMPTY + year + "_" + eventCode + "_" + matchNumber;
        this.year = year;
        this.eventCode = eventCode;
        this.matchNumber = matchNumber;
        this.tournamentLevel = tournamentLevel;
        this.played = played;
        this.redTotal = redTotal;
        this.redPenalty = redPenalty;
        this.blueTotal = blueTotal;
        this.bluePenalty = bluePenalty;
    }

    public void setMatchScoreKey(@NonNull String matchScoreKey) {
        this.matchScoreKey = matchScoreKey;
    }

    @NonNull
    public String getMatchScoreKey() { return matchScoreKey; }
    public int getYear() { return year; }
    public String getEventCode() { return eventCode; }
    public int getMatchNumber() { return matchNumber; }
    public String getTournamentLevel() { return tournamentLevel; }
    public boolean isPlayed() { return played; }
    public int getRedTotal() { return redTotal; }
    public int getRedPenalty() { return redPenalty; }
    public int getBlueTotal() { return blueTotal; }
    public int getBluePenalty() { return bluePenalty; }

}

package org.hartlandrobotics.echelonFTC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "match")
public class Match {
   @PrimaryKey
   @NonNull
   @ColumnInfo(name = "match_key")
   private String matchKey;

   @ColumnInfo(name = "match_name")
   private String matchName;

   @ColumnInfo(name = "tournament_level")
   private int tournamentLevel;

   @ColumnInfo(name = "match_time")
   private String matchTime;

   @ColumnInfo(name = "scheduled_time")
   private String scheduledTime;

   @ColumnInfo(name = "red_1_team_key")
   private String red1TeamKey;

   @ColumnInfo(name = "red_2_team_key")
   private String red2TeamKey;

   @ColumnInfo(name = "blue_1_team_key")
   private String blue1TeamKey;

   @ColumnInfo(name = "blue_2_team_key")
   private String blue2TeamKey;

   public Match(String matchKey, String matchName,
                int tournamentLevel, String matchTime, String scheduledTime,
                String red1TeamKey, String red2TeamKey,
                String blue1TeamKey, String blue2TeamKey
   ) {
      this.matchKey = matchKey;
      this.matchName = matchName;
      this.tournamentLevel = tournamentLevel;
      this.matchTime = matchTime;
      this.scheduledTime = scheduledTime;
      this.red1TeamKey = red1TeamKey;
      this.red2TeamKey =  red2TeamKey;
      this.blue1TeamKey = blue1TeamKey;
      this.blue2TeamKey = blue2TeamKey;
   }

   public String getMatchKey() {
      return matchKey;
   }

   public String getMatchName() {
      return matchName;
   }

   public int getTournamentLevel() {
      return tournamentLevel;
   }

   public String getMatchTime() {
      return matchTime;
   }

   public String getScheduledTime() {
      return scheduledTime;
   }

   public String getRed1TeamKey(){
      return red1TeamKey;
   }

   public String getRed2TeamKey(){
      return red2TeamKey;
   }

   public String getBlue1TeamKey(){
      return blue1TeamKey;
   }

   public String getBlue2TeamKey(){
      return blue2TeamKey;
   }

   public Integer getMatchNumber(){
      //return Integer.parseInt( matchName.split(" ")[1]);
      return Integer.parseInt( matchName );

   }
}

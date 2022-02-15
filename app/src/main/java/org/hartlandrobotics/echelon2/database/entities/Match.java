package org.hartlandrobotics.echelon2.database.entities;

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

   @ColumnInfo(name = "match_number")
   private int matchNumber;

   @ColumnInfo(name = "comp_level")
   private String compLevel;

   @ColumnInfo(name = "match_time")
   private int matchTime;

   @ColumnInfo(name = "predicted_time")
   private int predictedTime;

   @ColumnInfo(name = "red_1_team_key")
   private String red1TeamKey;

   @ColumnInfo(name = "red_2_team_key")
   private String red2TeamKey;

   @ColumnInfo(name = "red_3_team_key")
   private String red3TeamKey;

   @ColumnInfo(name = "blue_1_team_key")
   private String blue1TeamKey;

   @ColumnInfo(name = "blue_2_team_key")
   private String blue2TeamKey;

   @ColumnInfo(name = "blue_3_team_key")
   private String blue3TeamKey;

   public Match(String matchKey, int matchNumber,
                String compLevel, int matchTime, int predictedTime,
                String red1TeamKey, String red2TeamKey, String red3TeamKey,
                String blue1TeamKey, String blue2TeamKey, String blue3TeamKey
   ) {
      this.matchKey = matchKey;
      this.matchNumber = matchNumber;
      this.compLevel = compLevel;
      this.matchTime = matchTime;
      this.predictedTime = predictedTime;
      this.red1TeamKey = red1TeamKey;
      this.red2TeamKey =  red2TeamKey;
      this.red3TeamKey = red3TeamKey;
      this.blue1TeamKey = blue1TeamKey;
      this.blue2TeamKey = blue2TeamKey;
      this.blue3TeamKey = blue3TeamKey;
   }

   public String getMatchKey() {
      return matchKey;
   }

   public int getMatchNumber() {
      return matchNumber;
   }

   public String getCompLevel() {
      return compLevel;
   }

   public int getMatchTime() {
      return matchTime;
   }

   public int getPredictedTime() {
      return predictedTime;
   }

   public String getRed1TeamKey(){
      return red1TeamKey;
   }

   public String getRed2TeamKey(){
      return red2TeamKey;
   }

   public String getRed3TeamKey(){
      return red3TeamKey;
   }

   public String getBlue1TeamKey(){
      return blue1TeamKey;
   }

   public String getBlue2TeamKey(){
      return blue2TeamKey;
   }

   public String getBlue3TeamKey(){
      return blue3TeamKey;
   }
}

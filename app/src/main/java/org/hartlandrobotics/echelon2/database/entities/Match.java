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
   private String mMatchKey;

   @ColumnInfo(name = "match_number")
   private int mMatchNumber;

   @ColumnInfo(name = "comp_level")
   private String mCompLevel;

   @ColumnInfo(name = "match_time")
   private int mMatchTime;

   @ColumnInfo(name = "predicted_time")
   private int mPredictedTime;

   @ColumnInfo(name = "red_1_team_key")
   private String mRed1TeamKey;

   @ColumnInfo(name = "red_2_team_key")
   private String mRed2TeamKey;

   @ColumnInfo(name = "red_3_team_key")
   private String mRed3TeamKey;

   @ColumnInfo(name = "blue_1_team_key")
   private String mBlue1TeamKey;

   @ColumnInfo(name = "blue_2_team_key")
   private String mBlue2TeamKey;

   @ColumnInfo(name = "blue_3_team_key")
   private String mBlue3TeamKey;

   public Match(String matchKey, int matchNumber,
                String compLevel, int matchTime, int predictedTime,
                String red1TeamKey, String red2TeamKey, String red3TeamKey,
                String blue1TeamKey, String blue2TeamKey, String blue3TeamKey
   ) {
      mMatchKey = matchKey;
      mMatchNumber = matchNumber;
      mCompLevel = compLevel;
      mMatchTime = matchTime;
      mPredictedTime = predictedTime;
      mRed1TeamKey = red1TeamKey;
      mRed2TeamKey =  red2TeamKey;
      mRed3TeamKey = red3TeamKey;
      mBlue1TeamKey = blue1TeamKey;
      mBlue2TeamKey = blue2TeamKey;
      mBlue3TeamKey = blue3TeamKey;
   }

   public String getMatchKey() {
      return mMatchKey;
   }

   public int getMatchNumber() {
      return mMatchNumber;
   }

   public String getCompLevel() {
      return mCompLevel;
   }

   public int getMatchTime() {
      return mMatchTime;
   }

   public int getPredictedTime() {
      return mPredictedTime;
   }

   public String getRed1TeamKey(){
      return mRed1TeamKey;
   }

   public String getRed2TeamKey(){
      return mRed2TeamKey;
   }

   public String getRed3TeamKey(){
      return mRed3TeamKey;
   }

   public String getBlue1TeamKey(){
      return mBlue1TeamKey;
   }

   public String getBlue2TeamKey(){
      return mBlue2TeamKey;
   }

   public String getBlue3TeamKey(){
      return mBlue3TeamKey;
   }
}

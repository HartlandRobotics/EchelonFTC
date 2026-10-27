package org.hartlandrobotics.echelonFTC.charts;

import android.app.Application;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.database.currentGame.CurrentGame;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamData {
    private final Integer teamNumber;
    private final int size;
    private int totalPoints = 0;
    private boolean isVisible = true;

    private int autoTotalPoints = 0;
    private final Map<Integer, Integer> autoScores = new HashMap<>();

    private int teleOpPoints = 0;
    private final Map<Integer, Integer> teleOpScores = new HashMap<>();

    private int endGamePoints = 0;
    private final Map<Integer, Integer> endGameScores = new HashMap<>();

    private int oprPoints = 0;
    private final Map<Integer, Integer> oprScores = new HashMap<>();

    private int item1Counts = 0;
    private int item2Counts = 0;
    private final Map<Integer, Integer> scoringElementCounts = new HashMap<>();

    public TeamData(Application application, Integer teamNumber, List<MatchResult> matchResults) {
        this.teamNumber = teamNumber;
        this.size = matchResults.size();
        FtcApiStatus apiStatus = new FtcApiStatus(application);
        List<String> currentInvisibleTeams = new ArrayList<>(Arrays.asList(StringUtils.defaultIfBlank(apiStatus.getInvisibleTeams(), StringUtils.EMPTY).split(",")));
        this.isVisible = !currentInvisibleTeams.contains(String.valueOf(teamNumber));

        for (MatchResult matchResult : matchResults) {
            CurrentGame currentGame = MatchResult.toCurrentGamePoints(matchResult);
            Integer matchNumber = Integer.valueOf(
                    matchResult.getMatchKey().split("_")[2]
            );


            item1Counts += currentGame.getItem1Counts();
            item2Counts += currentGame.getItem2Counts();
            scoringElementCounts.put(matchNumber, item1Counts + item2Counts);

            int matchAuto = 0;
            int matchTeleOp = 0;
            int matchEndGame = 0;
            int matchOpr = 0;
            if (currentGame.getContribution() == 0) {

                matchAuto = currentGame.getAutoPoints();
                autoScores.put(matchNumber, matchAuto);
                autoTotalPoints += matchAuto;

                matchTeleOp = currentGame.getTeleOpPoints();
                teleOpScores.put(matchNumber, matchTeleOp);
                teleOpPoints += matchTeleOp;

                matchEndGame = currentGame.getEndPoints();
                endGameScores.put(matchNumber, matchEndGame);
                endGamePoints += matchEndGame;

                oprScores.put(matchNumber, matchOpr);

            } else {
                autoScores.put(matchNumber, 0);
                teleOpScores.put(matchNumber, 0);
                endGameScores.put(matchNumber, 0);

                matchOpr = currentGame.getContribution();
                oprPoints += matchOpr;
                oprScores.put(matchNumber, matchOpr);
            }

            totalPoints += matchAuto + matchTeleOp + matchEndGame + matchOpr;
        }
    }

    public Integer getTeamNumber() {
        return teamNumber;
    }

    public int getSize(){ return size; }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getAutoTotalPoints() {
        return autoTotalPoints;
    }

    public Map<Integer, Integer> getAutoScores() {
        return autoScores;
    }

    public int getTeleOpPoints() {
        return teleOpPoints;
    }

    public Map<Integer, Integer> getTeleOpScores() {
        return teleOpScores;
    }

    public int getEndGamePoints() {
        return endGamePoints;
    }

    public Map<Integer, Integer> getEndGameScores() {
        return endGameScores;
    }

    public int getOprPoints() {
        return oprPoints;
    }

    public Map<Integer, Integer> getOprScores() {
        return oprScores;
    }

    public boolean getVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Map<Integer, Integer> getScoringElementCounts() {
        return scoringElementCounts;
    }

    public int getItem1Counts() {
        return item1Counts;
    }

    public int getItem2Counts() {
        return item2Counts;
    }
}

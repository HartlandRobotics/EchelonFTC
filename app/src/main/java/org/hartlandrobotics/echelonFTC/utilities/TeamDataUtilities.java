package org.hartlandrobotics.echelonFTC.utilities;

import android.app.Application;

import org.hartlandrobotics.echelonFTC.charts.TeamData;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeamDataUtilities {
    public static List<TeamData> toTeamDataList(Application application, List<MatchResult> matchResults) {
        Map<String, List<MatchResult>> matchResultsByTeam =  new HashMap<>();

        for( MatchResult matchResult : matchResults ){
            String teamKey = matchResult.getTeamKey();
            matchResultsByTeam.computeIfAbsent(teamKey,key -> new ArrayList<>());
            matchResultsByTeam.get(teamKey).add(matchResult);
        }

        List<TeamData> teamDataList = matchResultsByTeam.entrySet()
                .stream()
                .map(entry -> new TeamData(application, Integer.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());

        return teamDataList;
    }
}

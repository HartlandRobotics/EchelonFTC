package org.hartlandrobotics.echelonFTC.utilities;

import android.app.Application;
import android.content.Context;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.charts.TeamData;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TeamDataUtilities {
    public static List<TeamData> toTeamDataList(List<MatchResult> matchResults) {
        Map<String, List<MatchResult>> matchResultsByTeam =  new HashMap<>();

        for( MatchResult matchResult : matchResults ){
            String teamKey = matchResult.getTeamKey();
            matchResultsByTeam.computeIfAbsent(teamKey,key -> new ArrayList<>());
            matchResultsByTeam.get(teamKey).add(matchResult);
        }

        return matchResultsByTeam.entrySet()
                .stream()
                .map(entry -> new TeamData(Integer.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static Set<Integer> invisibleTeamNumbers(Context context) {
        FtcApiStatus apiStatus = new FtcApiStatus(context);

        Set<Integer> invisibleTeamNumbers = Arrays.stream(
                        StringUtils.defaultIfBlank(apiStatus.getInvisibleTeams(), StringUtils.EMPTY)
                                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .collect(Collectors.toSet());

        return invisibleTeamNumbers;
    }

}

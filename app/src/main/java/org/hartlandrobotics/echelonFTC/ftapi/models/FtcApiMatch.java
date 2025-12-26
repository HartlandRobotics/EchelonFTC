package org.hartlandrobotics.echelonFTC.ftapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.database.entities.Match;

import java.util.Arrays;
import java.util.List;

public class FtcApiMatch {
    @JsonProperty("description")
    private String description;

    @JsonProperty("matchNumber")
    private int matchNumber;

    @JsonProperty("teams")
    private List<FtcApiMatchTeam> teams;

    public String getDescription() {
        return description;
    }
    public int getMatchNumber() {
        return matchNumber;
    }

    public List<FtcApiMatchTeam> getTeams() {
        return teams;
    }


    public Match toMatch(String year, String event) {
        List<FtcApiMatchTeam> teams = getTeams();
        Match match = new Match(
                year + "_" + event + "_" + getMatchNumber(),
                String.valueOf(getMatchNumber()),
                1,
                StringUtils.EMPTY,
                StringUtils.EMPTY,
                teams.stream().filter(team -> team.getStation().equals("Red1")).findFirst().get().getDisplayTeamNumber(),
                teams.stream().filter(team -> team.getStation().equals("Red2")).findFirst().get().getDisplayTeamNumber(),
                teams.stream().filter(team -> team.getStation().equals("Blue1")).findFirst().get().getDisplayTeamNumber(),
                teams.stream().filter(team -> team.getStation().equals("Blue2")).findFirst().get().getDisplayTeamNumber()
        );
        return match;
    }

}

/*

{
	"schedule": [
		{
			"description": "Qualification 1",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T11:00:00",
			"series": 0,
			"matchNumber": 1,
			"teams": [
				{
					"teamNumber": 31875,
					"displayTeamNumber": "31875",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robotic Ramen",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23623,
					"displayTeamNumber": "23623",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hydra Dragons 23623",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 30837,
					"displayTeamNumber": "30837",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Ann Arbor Androids",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23416,
					"displayTeamNumber": "23416",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Toasters Red",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T16:04:14.006"
		},
		{
			"description": "Qualification 2",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T11:07:00",
			"series": 0,
			"matchNumber": 2,
			"teams": [
				{
					"teamNumber": 8492,
					"displayTeamNumber": "8492",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Titanium Trojans",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7643,
					"displayTeamNumber": "7643",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Tungsten Tigers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 19561,
					"displayTeamNumber": "19561",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Chromium Tigers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 20332,
					"displayTeamNumber": "20332",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Just Add Logic",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T16:09:15.411"
		},
		{
			"description": "Qualification 3",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T11:14:00",
			"series": 0,
			"matchNumber": 3,
			"teams": [
				{
					"teamNumber": 21340,
					"displayTeamNumber": "21340",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Crimson Claws",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7043,
					"displayTeamNumber": "7043",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Golden Gears",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 28804,
					"displayTeamNumber": "28804",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "CyberSmiths",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15256,
					"displayTeamNumber": "15256",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Knights",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T16:15:31.393"
		},
		{
			"description": "Qualification 4",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T11:21:00",
			"series": 0,
			"matchNumber": 4,
			"teams": [
				{
					"teamNumber": 17993,
					"displayTeamNumber": "17993",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robot Crusaders",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 21914,
					"displayTeamNumber": "21914",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Team KARMA",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15078,
					"displayTeamNumber": "15078",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Reuthenators",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15358,
					"displayTeamNumber": "15358",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robo-COLTS",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T16:24:33.135"
		},
		{
			"description": "Qualification 5",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T11:28:00",
			"series": 0,
			"matchNumber": 5,
			"teams": [
				{
					"teamNumber": 7031,
					"displayTeamNumber": "7031",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Linden Battle Blazers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10538,
					"displayTeamNumber": "10538",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Team KILTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8533,
					"displayTeamNumber": "8533",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "CSA MS Miners",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15182,
					"displayTeamNumber": "15182",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Magmatrons",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T16:29:08.281"
		},
		{
			"description": "Qualification 6",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T11:35:00",
			"series": 0,
			"matchNumber": 6,
			"teams": [
				{
					"teamNumber": 23834,
					"displayTeamNumber": "23834",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Pine Knob Pilots",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 27242,
					"displayTeamNumber": "27242",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Linden Electric Eagles",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 13643,
					"displayTeamNumber": "13643",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Rebel Wreckers 13643",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10645,
					"displayTeamNumber": "10645",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hornet Hackers",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.211"
		},
		{
			"description": "Qualification 7",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T11:42:00",
			"series": 0,
			"matchNumber": 7,
			"teams": [
				{
					"teamNumber": 10136,
					"displayTeamNumber": "10136",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Frost RoboFalcons",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 20159,
					"displayTeamNumber": "20159",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Archangel Holy Gears",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31261,
					"displayTeamNumber": "31261",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hack Attack",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8487,
					"displayTeamNumber": "8487",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robo Toasters Orange",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.187"
		},
		{
			"description": "Qualification 8",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T11:49:00",
			"series": 0,
			"matchNumber": 8,
			"teams": [
				{
					"teamNumber": 16668,
					"displayTeamNumber": "16668",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "4-Gear Clovers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23168,
					"displayTeamNumber": "23168",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Wired Warriors",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10164,
					"displayTeamNumber": "10164",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Toasters Blue",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 12548,
					"displayTeamNumber": "12548",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Rebel Droids 12548",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.161"
		},
		{
			"description": "Qualification 9",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T11:56:00",
			"series": 0,
			"matchNumber": 9,
			"teams": [
				{
					"teamNumber": 27191,
					"displayTeamNumber": "27191",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Jurassic Bots",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 14739,
					"displayTeamNumber": "14739",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Metal Benders",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31124,
					"displayTeamNumber": "31124",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Cyber-COLTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23416,
					"displayTeamNumber": "23416",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Toasters Red",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.143"
		},
		{
			"description": "Qualification 10",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T12:03:00",
			"series": 0,
			"matchNumber": 10,
			"teams": [
				{
					"teamNumber": 21340,
					"displayTeamNumber": "21340",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Crimson Claws",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23623,
					"displayTeamNumber": "23623",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hydra Dragons 23623",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10538,
					"displayTeamNumber": "10538",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Team KILTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15078,
					"displayTeamNumber": "15078",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Reuthenators",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.125"
		},
		{
			"description": "Qualification 11",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T12:10:00",
			"series": 0,
			"matchNumber": 11,
			"teams": [
				{
					"teamNumber": 28804,
					"displayTeamNumber": "28804",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "CyberSmiths",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10136,
					"displayTeamNumber": "10136",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Frost RoboFalcons",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 20332,
					"displayTeamNumber": "20332",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Just Add Logic",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7031,
					"displayTeamNumber": "7031",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Linden Battle Blazers",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.107"
		},
		{
			"description": "Qualification 12",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T12:17:00",
			"series": 0,
			"matchNumber": 12,
			"teams": [
				{
					"teamNumber": 8487,
					"displayTeamNumber": "8487",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robo Toasters Orange",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15256,
					"displayTeamNumber": "15256",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Knights",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 21914,
					"displayTeamNumber": "21914",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Team KARMA",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23834,
					"displayTeamNumber": "23834",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Pine Knob Pilots",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.099"
		},
		{
			"description": "Qualification 13",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T13:00:00",
			"series": 0,
			"matchNumber": 13,
			"teams": [
				{
					"teamNumber": 7043,
					"displayTeamNumber": "7043",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Golden Gears",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7643,
					"displayTeamNumber": "7643",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Tungsten Tigers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10645,
					"displayTeamNumber": "10645",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hornet Hackers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 20159,
					"displayTeamNumber": "20159",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Archangel Holy Gears",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.091"
		},
		{
			"description": "Qualification 14",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T13:06:00",
			"series": 0,
			"matchNumber": 14,
			"teams": [
				{
					"teamNumber": 31875,
					"displayTeamNumber": "31875",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robotic Ramen",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 16668,
					"displayTeamNumber": "16668",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "4-Gear Clovers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15358,
					"displayTeamNumber": "15358",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robo-COLTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15182,
					"displayTeamNumber": "15182",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Magmatrons",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.082"
		},
		{
			"description": "Qualification 15",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T13:12:00",
			"series": 0,
			"matchNumber": 15,
			"teams": [
				{
					"teamNumber": 27191,
					"displayTeamNumber": "27191",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Jurassic Bots",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8492,
					"displayTeamNumber": "8492",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Titanium Trojans",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 27242,
					"displayTeamNumber": "27242",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Linden Electric Eagles",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 12548,
					"displayTeamNumber": "12548",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Rebel Droids 12548",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.073"
		},
		{
			"description": "Qualification 16",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T13:18:00",
			"series": 0,
			"matchNumber": 16,
			"teams": [
				{
					"teamNumber": 17993,
					"displayTeamNumber": "17993",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robot Crusaders",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 14739,
					"displayTeamNumber": "14739",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Metal Benders",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 13643,
					"displayTeamNumber": "13643",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Rebel Wreckers 13643",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23168,
					"displayTeamNumber": "23168",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Wired Warriors",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.054"
		},
		{
			"description": "Qualification 17",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T13:24:00",
			"series": 0,
			"matchNumber": 17,
			"teams": [
				{
					"teamNumber": 19561,
					"displayTeamNumber": "19561",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Chromium Tigers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 30837,
					"displayTeamNumber": "30837",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Ann Arbor Androids",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8533,
					"displayTeamNumber": "8533",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "CSA MS Miners",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10164,
					"displayTeamNumber": "10164",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Toasters Blue",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.282"
		},
		{
			"description": "Qualification 18",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T13:30:00",
			"series": 0,
			"matchNumber": 18,
			"teams": [
				{
					"teamNumber": 15358,
					"displayTeamNumber": "15358",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robo-COLTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 20332,
					"displayTeamNumber": "20332",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Just Add Logic",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31261,
					"displayTeamNumber": "31261",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hack Attack",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31124,
					"displayTeamNumber": "31124",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Cyber-COLTS",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.038"
		},
		{
			"description": "Qualification 19",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T13:36:00",
			"series": 0,
			"matchNumber": 19,
			"teams": [
				{
					"teamNumber": 31875,
					"displayTeamNumber": "31875",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robotic Ramen",
					"surrogate": true,
					"noShow": false
				},
				{
					"teamNumber": 10645,
					"displayTeamNumber": "10645",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hornet Hackers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10538,
					"displayTeamNumber": "10538",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Team KILTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 28804,
					"displayTeamNumber": "28804",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "CyberSmiths",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.266"
		},
		{
			"description": "Qualification 20",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T13:42:00",
			"series": 0,
			"matchNumber": 20,
			"teams": [
				{
					"teamNumber": 15078,
					"displayTeamNumber": "15078",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Reuthenators",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 27242,
					"displayTeamNumber": "27242",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Linden Electric Eagles",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7043,
					"displayTeamNumber": "7043",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Golden Gears",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8487,
					"displayTeamNumber": "8487",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robo Toasters Orange",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.021"
		},
		{
			"description": "Qualification 21",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T13:48:00",
			"series": 0,
			"matchNumber": 21,
			"teams": [
				{
					"teamNumber": 21340,
					"displayTeamNumber": "21340",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Crimson Claws",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15182,
					"displayTeamNumber": "15182",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Magmatrons",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 14739,
					"displayTeamNumber": "14739",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Metal Benders",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23834,
					"displayTeamNumber": "23834",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Pine Knob Pilots",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.239"
		},
		{
			"description": "Qualification 22",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T13:54:00",
			"series": 0,
			"matchNumber": 22,
			"teams": [
				{
					"teamNumber": 15256,
					"displayTeamNumber": "15256",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Knights",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7643,
					"displayTeamNumber": "7643",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Tungsten Tigers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 27191,
					"displayTeamNumber": "27191",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Jurassic Bots",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 30837,
					"displayTeamNumber": "30837",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Ann Arbor Androids",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:30.999"
		},
		{
			"description": "Qualification 23",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T14:00:00",
			"series": 0,
			"matchNumber": 23,
			"teams": [
				{
					"teamNumber": 31261,
					"displayTeamNumber": "31261",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hack Attack",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 13643,
					"displayTeamNumber": "13643",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Rebel Wreckers 13643",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10164,
					"displayTeamNumber": "10164",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Toasters Blue",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23623,
					"displayTeamNumber": "23623",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hydra Dragons 23623",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.222"
		},
		{
			"description": "Qualification 24",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T14:06:00",
			"series": 0,
			"matchNumber": 24,
			"teams": [
				{
					"teamNumber": 7031,
					"displayTeamNumber": "7031",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Linden Battle Blazers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 20159,
					"displayTeamNumber": "20159",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Archangel Holy Gears",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23168,
					"displayTeamNumber": "23168",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Wired Warriors",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8492,
					"displayTeamNumber": "8492",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Titanium Trojans",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:30.991"
		},
		{
			"description": "Qualification 25",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T14:12:00",
			"series": 0,
			"matchNumber": 25,
			"teams": [
				{
					"teamNumber": 31124,
					"displayTeamNumber": "31124",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Cyber-COLTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 19561,
					"displayTeamNumber": "19561",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Chromium Tigers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 17993,
					"displayTeamNumber": "17993",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robot Crusaders",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 16668,
					"displayTeamNumber": "16668",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "4-Gear Clovers",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.203"
		},
		{
			"description": "Qualification 26",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T14:18:00",
			"series": 0,
			"matchNumber": 26,
			"teams": [
				{
					"teamNumber": 12548,
					"displayTeamNumber": "12548",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Rebel Droids 12548",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10136,
					"displayTeamNumber": "10136",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Frost RoboFalcons",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8533,
					"displayTeamNumber": "8533",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "CSA MS Miners",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 21914,
					"displayTeamNumber": "21914",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Team KARMA",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:30.984"
		},
		{
			"description": "Qualification 27",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T14:24:00",
			"series": 0,
			"matchNumber": 27,
			"teams": [
				{
					"teamNumber": 8487,
					"displayTeamNumber": "8487",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robo Toasters Orange",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15182,
					"displayTeamNumber": "15182",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Magmatrons",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23416,
					"displayTeamNumber": "23416",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Toasters Red",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10164,
					"displayTeamNumber": "10164",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Toasters Blue",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.195"
		},
		{
			"description": "Qualification 28",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T14:30:00",
			"series": 0,
			"matchNumber": 28,
			"teams": [
				{
					"teamNumber": 13643,
					"displayTeamNumber": "13643",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Rebel Wreckers 13643",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10538,
					"displayTeamNumber": "10538",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Team KILTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7043,
					"displayTeamNumber": "7043",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Golden Gears",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 27191,
					"displayTeamNumber": "27191",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Jurassic Bots",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:30.975"
		},
		{
			"description": "Qualification 29",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T14:36:00",
			"series": 0,
			"matchNumber": 29,
			"teams": [
				{
					"teamNumber": 20159,
					"displayTeamNumber": "20159",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Archangel Holy Gears",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 21340,
					"displayTeamNumber": "21340",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Crimson Claws",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 17993,
					"displayTeamNumber": "17993",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robot Crusaders",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 20332,
					"displayTeamNumber": "20332",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Just Add Logic",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.179"
		},
		{
			"description": "Qualification 30",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T14:42:00",
			"series": 0,
			"matchNumber": 30,
			"teams": [
				{
					"teamNumber": 10136,
					"displayTeamNumber": "10136",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Frost RoboFalcons",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31875,
					"displayTeamNumber": "31875",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robotic Ramen",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15078,
					"displayTeamNumber": "15078",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Reuthenators",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 19561,
					"displayTeamNumber": "19561",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Chromium Tigers",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:30.967"
		},
		{
			"description": "Qualification 31",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T14:48:00",
			"series": 0,
			"matchNumber": 31,
			"teams": [
				{
					"teamNumber": 15358,
					"displayTeamNumber": "15358",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robo-COLTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23623,
					"displayTeamNumber": "23623",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hydra Dragons 23623",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23834,
					"displayTeamNumber": "23834",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Pine Knob Pilots",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7031,
					"displayTeamNumber": "7031",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Linden Battle Blazers",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.17"
		},
		{
			"description": "Qualification 32",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T14:54:00",
			"series": 0,
			"matchNumber": 32,
			"teams": [
				{
					"teamNumber": 12548,
					"displayTeamNumber": "12548",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Rebel Droids 12548",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31261,
					"displayTeamNumber": "31261",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hack Attack",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 28804,
					"displayTeamNumber": "28804",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "CyberSmiths",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 30837,
					"displayTeamNumber": "30837",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Ann Arbor Androids",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:30.95"
		},
		{
			"description": "Qualification 33",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T15:00:00",
			"series": 0,
			"matchNumber": 33,
			"teams": [
				{
					"teamNumber": 8492,
					"displayTeamNumber": "8492",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Titanium Trojans",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10645,
					"displayTeamNumber": "10645",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hornet Hackers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15256,
					"displayTeamNumber": "15256",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Knights",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31124,
					"displayTeamNumber": "31124",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Cyber-COLTS",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.046"
		},
		{
			"description": "Qualification 34",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T15:06:00",
			"series": 0,
			"matchNumber": 34,
			"teams": [
				{
					"teamNumber": 23168,
					"displayTeamNumber": "23168",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Wired Warriors",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23416,
					"displayTeamNumber": "23416",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Toasters Red",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8533,
					"displayTeamNumber": "8533",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "CSA MS Miners",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7643,
					"displayTeamNumber": "7643",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Tungsten Tigers",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.152"
		},
		{
			"description": "Qualification 35",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T15:12:00",
			"series": 0,
			"matchNumber": 35,
			"teams": [
				{
					"teamNumber": 16668,
					"displayTeamNumber": "16668",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "4-Gear Clovers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 21914,
					"displayTeamNumber": "21914",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Team KARMA",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 14739,
					"displayTeamNumber": "14739",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Metal Benders",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 27242,
					"displayTeamNumber": "27242",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Linden Electric Eagles",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.274"
		},
		{
			"description": "Qualification 36",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T15:18:00",
			"series": 0,
			"matchNumber": 36,
			"teams": [
				{
					"teamNumber": 15182,
					"displayTeamNumber": "15182",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Magmatrons",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 28804,
					"displayTeamNumber": "28804",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "CyberSmiths",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 13643,
					"displayTeamNumber": "13643",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Rebel Wreckers 13643",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15078,
					"displayTeamNumber": "15078",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Reuthenators",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.322"
		},
		{
			"description": "Qualification 37",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T15:24:00",
			"series": 0,
			"matchNumber": 37,
			"teams": [
				{
					"teamNumber": 12548,
					"displayTeamNumber": "12548",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Rebel Droids 12548",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15358,
					"displayTeamNumber": "15358",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robo-COLTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8487,
					"displayTeamNumber": "8487",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robo Toasters Orange",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 19561,
					"displayTeamNumber": "19561",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Chromium Tigers",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.03"
		},
		{
			"description": "Qualification 38",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T15:30:00",
			"series": 0,
			"matchNumber": 38,
			"teams": [
				{
					"teamNumber": 10538,
					"displayTeamNumber": "10538",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Team KILTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23834,
					"displayTeamNumber": "23834",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Pine Knob Pilots",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 20159,
					"displayTeamNumber": "20159",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Archangel Holy Gears",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10164,
					"displayTeamNumber": "10164",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Toasters Blue",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.134"
		},
		{
			"description": "Qualification 39",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T15:36:00",
			"series": 0,
			"matchNumber": 39,
			"teams": [
				{
					"teamNumber": 20332,
					"displayTeamNumber": "20332",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Just Add Logic",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10645,
					"displayTeamNumber": "10645",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hornet Hackers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 27191,
					"displayTeamNumber": "27191",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Jurassic Bots",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23623,
					"displayTeamNumber": "23623",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hydra Dragons 23623",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.247"
		},
		{
			"description": "Qualification 40",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T15:42:00",
			"series": 0,
			"matchNumber": 40,
			"teams": [
				{
					"teamNumber": 14739,
					"displayTeamNumber": "14739",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Metal Benders",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8533,
					"displayTeamNumber": "8533",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "CSA MS Miners",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 8492,
					"displayTeamNumber": "8492",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Titanium Trojans",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31875,
					"displayTeamNumber": "31875",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robotic Ramen",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.314"
		},
		{
			"description": "Qualification 41",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T15:48:00",
			"series": 0,
			"matchNumber": 41,
			"teams": [
				{
					"teamNumber": 21914,
					"displayTeamNumber": "21914",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Team KARMA",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31124,
					"displayTeamNumber": "31124",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Cyber-COLTS",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23168,
					"displayTeamNumber": "23168",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Wired Warriors",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 30837,
					"displayTeamNumber": "30837",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Ann Arbor Androids",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.011"
		},
		{
			"description": "Qualification 42",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T15:54:00",
			"series": 0,
			"matchNumber": 42,
			"teams": [
				{
					"teamNumber": 21340,
					"displayTeamNumber": "21340",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Crimson Claws",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7643,
					"displayTeamNumber": "7643",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Tungsten Tigers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 27242,
					"displayTeamNumber": "27242",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Linden Electric Eagles",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 10136,
					"displayTeamNumber": "10136",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Frost RoboFalcons",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.116"
		},
		{
			"description": "Qualification 43",
			"field": "1",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T16:00:00",
			"series": 0,
			"matchNumber": 43,
			"teams": [
				{
					"teamNumber": 7031,
					"displayTeamNumber": "7031",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Linden Battle Blazers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 16668,
					"displayTeamNumber": "16668",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "4-Gear Clovers",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 23416,
					"displayTeamNumber": "23416",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Toasters Red",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 7043,
					"displayTeamNumber": "7043",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Golden Gears",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.231"
		},
		{
			"description": "Qualification 44",
			"field": "2",
			"tournamentLevel": "QUALIFICATION",
			"startTime": "2025-11-08T16:06:00",
			"series": 0,
			"matchNumber": 44,
			"teams": [
				{
					"teamNumber": 17993,
					"displayTeamNumber": "17993",
					"station": "Red1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robot Crusaders",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31261,
					"displayTeamNumber": "31261",
					"station": "Red2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Hack Attack",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 15256,
					"displayTeamNumber": "15256",
					"station": "Blue1",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Techno Knights",
					"surrogate": false,
					"noShow": false
				},
				{
					"teamNumber": 31875,
					"displayTeamNumber": "31875",
					"station": "Blue2",
					"team": "Database.FTCCloud.Entities.Team",
					"teamName": "Robotic Ramen",
					"surrogate": false,
					"noShow": false
				}
			],
			"modifiedOn": "2025-11-08T13:26:31.306"
		}
	]
}

 */

/*

package org.hartlandrobotics.echelonFTC.orangeAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelonFTC.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.Match;

import java.util.Arrays;
import java.util.Comparator;

public class SyncMatch {
    @JsonProperty("match_key")
    private String matchKey;

    @JsonProperty("tournament_level")
    private int tournamentLevel;
    // todo: determine what the values mean, was qf, ef, qf, sf, and f before

    @JsonProperty("match_name")
    private String matchName;

    @JsonProperty("match_start_time")
    private String matchStartTime;

    @JsonProperty("scheduled_time")
    private String scheduledTime;

    @JsonProperty("participants")
    private Participants[] participants;

    public static class Participants {
        @JsonProperty("station")
        private int station;
        // 11 and 12 are red 1 and 2, 21 and 21 are blue 1 and blue 2

        @JsonProperty("team")
        private Team team;
    }

    public static class Team {
        @JsonProperty("team_key")
        String teamKey;
    }


    public EvtMatchCrossRef toEvtMatch(String eventKey) {
        EvtMatchCrossRef crossRef = new EvtMatchCrossRef( eventKey, getMatchKey() );
        return crossRef;
    }

    //yyyy[EVENT_CODE]_[COMP_LEVEL]m[MATCH_NUMBER]
    public String getMatchKey() {
        return matchKey;
    }

    // possible values [ qm, ef, qf, sf, f ]
    public int getTournamentLevel() {
        return tournamentLevel;
    }

    public String getMatchName() {
        return matchName;
    }

    public String getMatchStartTime() {
        return matchStartTime;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }
}


 */

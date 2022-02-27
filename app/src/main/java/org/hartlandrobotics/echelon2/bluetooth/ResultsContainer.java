package org.hartlandrobotics.echelon2.bluetooth;

import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.database.entities.PitScout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class ResultsContainer {

    private String sourceDeviceName;
    private List<MatchResult> matchResults;
    private List<PitScout> pitScoutResults;

    public ResultsContainer(){}

    public ResultsContainer(String sourceDeviceName,
                            List<MatchResult> matchResults,
                            List<PitScout> pitScoutResults
    ){
        this.sourceDeviceName = sourceDeviceName;
        this.matchResults = matchResults;
        this.pitScoutResults = pitScoutResults;
    }

    public List<MatchResult> getMatchResults(){
        if( matchResults == null ){ return new ArrayList<>(); }
        return matchResults;
    }

    public void setMatchResults( List<MatchResult> matchResults ) {
        this.matchResults = matchResults;
    }

    public List<PitScout> getPitScoutResults(){
        if( pitScoutResults == null) { return new ArrayList<>(); }
        return pitScoutResults;
    }
    public void setPitScoutResults( List<PitScout> pitScoutResults ){
        this.pitScoutResults = pitScoutResults;
    }

    public String getSourceDeviceName(){ return sourceDeviceName; }
    public void setSourceDeviceName( String sourceDeviceName ){
        this.sourceDeviceName = sourceDeviceName;
    }
}

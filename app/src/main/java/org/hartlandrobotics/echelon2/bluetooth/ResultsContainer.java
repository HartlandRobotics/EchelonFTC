package org.hartlandrobotics.echelon2.bluetooth;

import org.hartlandrobotics.echelon2.database.entities.MatchResult;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class ResultsContainer {

    private String sourceDeviceName;
    private List<MatchResult> matchResults;

    public ResultsContainer(){}

    public ResultsContainer(String sourceDeviceName, List<MatchResult> matchResults){
        this.sourceDeviceName = sourceDeviceName;
        this.matchResults = matchResults;
    }

    public List<MatchResult> getMatchResults(){
        if( matchResults == null ){ return new ArrayList<MatchResult>(); }
        return matchResults;
    }
    public void setMatchResults( List<MatchResult> matchResults ){
        matchResults = matchResults;
    }

    public String getSourceDeviceName(){ return sourceDeviceName; }
    public void setSourceDeviceName( String sourceDeviceName ){
        this.sourceDeviceName = sourceDeviceName;
    }
}

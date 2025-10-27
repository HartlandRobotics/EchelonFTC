package org.hartlandrobotics.echelonFTC.orangeAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SyncStatus {

    @JsonProperty("name")
    public String name;

    @JsonProperty("version")
    private String version;

    @JsonProperty("status")
    public String status;

    public void setName(String n) { name=n; }
    public String getName(){ return name; }

    public void setVersion(String v){
    version=v;
}
    public String getVersion(){
    return version;
}

    public void setStatus(String s){ status=s; }
    public String getStatus(){ return status; }
}

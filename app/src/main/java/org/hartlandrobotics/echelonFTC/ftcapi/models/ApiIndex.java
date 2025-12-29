package org.hartlandrobotics.echelonFTC.ftcapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiIndex {
    @JsonProperty("name")
    private String name;

    @JsonProperty("apiVersion")
    private String apiVersion;

    @JsonProperty("status")
    private String status;

    public void setName(String name){ this.name = name; }
    public String getName(){
        return this.name;
    }

    public void setApiVersion(String apiVersion){ this.apiVersion = apiVersion; }
    public String getApiVersion(){
        return this.apiVersion;
    }

    public void setStatus(String status){ this.status = status; }
    public String getStatus(){
        return this.status;
    }

}

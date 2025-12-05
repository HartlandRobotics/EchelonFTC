package org.hartlandrobotics.echelonFTC.ftapi;

import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiEvents;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiIndex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FtcApiInterface {
    @GET(" ")
    Call<FtcApiIndex> getStatus();

    @GET("{year}/events")
    Call<FtcApiEvents> getEvents( @Path("year") String year );

}


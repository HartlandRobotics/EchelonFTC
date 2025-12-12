package org.hartlandrobotics.echelonFTC.ftapi;

import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiEvents;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiIndex;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiTeams;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FtcApiInterface {
    @GET(" ")
    Call<FtcApiIndex> getStatus();

    @GET("{year}/events")
    Call<FtcApiEvents> getEvents( @Path("year") String year );

    @GET("{year}/teams")
    Call<FtcApiTeams> getTeamsByEvent(@Path("year") String year, @Query("eventCode") String eventCode);

}


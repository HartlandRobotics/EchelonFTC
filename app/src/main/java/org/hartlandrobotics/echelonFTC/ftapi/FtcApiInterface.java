package org.hartlandrobotics.echelonFTC.ftapi;

import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiEvents;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiIndex;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiMatch;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiSchedule;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiTeams;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface FtcApiInterface {
    @GET(" ")
    Call<FtcApiIndex> getStatus();

    @GET("{year}/events")
    Call<FtcApiEvents> getEvents( @Path("year") String year );

    @GET("{year}/teams")
    Call<FtcApiTeams> getTeamsByEvent(@Path("year") String year, @Query("eventCode") String eventCode);

    @GET("{year}/schedule/{eventCode}")
    Call<FtcApiSchedule> getScheduleByEvent( @Path("year") String year,
                                             @Path("eventCode") String eventCode,
                                             @QueryMap Map<String, String> queryStringMap);
}


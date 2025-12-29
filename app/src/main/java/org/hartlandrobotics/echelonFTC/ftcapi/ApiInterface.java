package org.hartlandrobotics.echelonFTC.ftcapi;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import org.hartlandrobotics.echelonFTC.ftcapi.models.ApiIndex;
import org.hartlandrobotics.echelonFTC.ftcapi.models.ApiTeams;
import org.hartlandrobotics.echelonFTC.ftcapi.models.ApiEvents;
import org.hartlandrobotics.echelonFTC.ftcapi.models.ApiSchedule;

public interface ApiInterface {
    @GET("")
    Call<ApiIndex> getStatus();

    @GET("{year}/events")
    Call<ApiEvents> getEventsByYear( @Path("year") String year );

    @GET("{year}/teams")
    Call<ApiTeams> getTeamsByEvent(@Path("year") String year, @Query("eventCode") String eventCode);

    @GET("{year}/schedule/{eventCode}")
    Call<ApiSchedule> getScheduleByEvent( @Path("year") String year,
                                             @Path("eventCode") String eventCode,
                                             @QueryMap Map<String, String> queryStringMap);
}


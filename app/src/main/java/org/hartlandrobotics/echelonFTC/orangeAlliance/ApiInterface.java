package org.hartlandrobotics.echelonFTC.orangeAlliance;
//TODO: change to orange alliance

import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncEvent;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncMatch;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncRegions;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncStatus;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncTeam;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET(" ")
    Call<SyncStatus> getStatus();

    @GET("/{season}/events")
    Call<List<SyncEvent>> getEventsBySeason(@Path("season") String seasonKey);

    // same for orange alliance
    //@GET("event/{event_key}")
    //Call<SyncEvent> getEventByKey(@Path("event_key") String eventKey);

    // same for orange alliance
    //@GET("event/{event_key}/teams")
    //Call<List<SyncTeam>> getTeamsByEvent(@Path("event_key") String eventKey);


    //tba
    //@GET("event/{event_key}/matches/simple")
    //Call<List<SyncMatch>> getMatchesByEvent(@Path("event_key") String eventKey);

    //@GET("event/{event_key}/matches")
    //Call<List<SyncMatch>> getMatchesByEvent(@Path("event_key") String eventKey);
}

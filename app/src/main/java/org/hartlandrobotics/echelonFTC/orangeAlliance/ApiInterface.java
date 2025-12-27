/*
package org.hartlandrobotics.echelonFTC.orangeAlliance;
//TODO: change to orange alliance

import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncDistrict;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncEvent;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncMatch;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncStatus;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncTeam;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    //tba
    //@GET("status")
    //Call<SyncStatus> getStatus();

    @GET(" ")
    Call<SyncStatus> getStatus();

    //tba
    //@GET("districts/{year}")

    //Call<List<SyncDistrict>> getDistrictsByYear(@Path("year") int year);
    //<List<SyncDistrict>> getDistrictsByYear(@Path("year") int year);

    @GET("regions")
    Call<List<SyncDistrict>> getRegions();
    // gets all FTC regions

    //todo: look into /seasons because year isn't available on region

    //tba
    //@GET("district/{district_key}/events")
    //Call<List<SyncEvent>> getEventsByDistrict(@Path("district_key") String districtKey);

    //todo: add query parameter for season key
    @GET("event")
    Call<List<SyncEvent>> getEventsByRegion(@QueryMap Map<String,String> filters);

    // same for orange alliance
    @GET("event/{event_key}")
    Call<SyncEvent> getEventByKey(@Path("event_key") String eventKey);

    // same for orange alliance
    @GET("event/{event_key}/teams")
    Call<List<SyncTeam>> getTeamsByEvent(@Path("event_key") String eventKey);


    //tba
    //@GET("event/{event_key}/matches/simple")
    //Call<List<SyncMatch>> getMatchesByEvent(@Path("event_key") String eventKey);

    @GET("event/{event_key}/matches")
    Call<List<SyncMatch>> getMatchesByEvent(@Path("event_key") String eventKey);
}
*/

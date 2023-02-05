package org.hartlandrobotics.echelon2.blueAlliance;
//TODO: change to orange alliance

import org.hartlandrobotics.echelon2.blueAlliance.models.SyncRegion;
import org.hartlandrobotics.echelon2.blueAlliance.models.SyncEvent;
import org.hartlandrobotics.echelon2.blueAlliance.models.SyncMatch;
import org.hartlandrobotics.echelon2.blueAlliance.models.SyncStatus;
import org.hartlandrobotics.echelon2.blueAlliance.models.SyncTeam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    //@GET("status")
    //Call<SyncStatus> getStatus();

    @GET("/")
    Call<SyncStatus> getStatus();

    //@GET("districts/{year}")
    Call<List<SyncRegion>> getDistrictsByYear(@Path("year") int year);

    @GET("regions")
    Call<List<SyncRegion>> getRegions();

    //@GET("district/{district_key}/events")
    //Call<List<SyncEvent>> getEventsByDistrict(@Path("district_key") String districtKey);

    @GET("event/{region_key}")
    Call<List<SyncEvent>> getEventsByRegion(@Path("region_key") String regionKey);

    // same for orange alliance
    @GET("event/{event_key}")
    Call<SyncEvent> getEventByKey(@Path("event_key") String eventKey);

    // same for orange alliance
    @GET("event/{event_key}/teams")
    Call<List<SyncTeam>> getTeamsByEvent(@Path("event_key") String eventKey);


//    @GET("event/{event_key}/matches/simple")
//    Call<List<SyncMatch>> getMatchesByEvent(@Path("event_key") String eventKey);

    @GET("event/{event_key}/matches")
    Call<List<SyncMatch>> getMatchesByEvent(@Path("event_key") String eventKey);
}

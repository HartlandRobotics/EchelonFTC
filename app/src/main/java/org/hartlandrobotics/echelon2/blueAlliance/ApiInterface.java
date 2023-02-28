package org.hartlandrobotics.echelon2.blueAlliance;
//TODO: change to orange alliance

import org.hartlandrobotics.echelon2.blueAlliance.models.SyncDistrict;
import org.hartlandrobotics.echelon2.blueAlliance.models.SyncEvent;
import org.hartlandrobotics.echelon2.blueAlliance.models.SyncMatch;
import org.hartlandrobotics.echelon2.blueAlliance.models.SyncStatus;
import org.hartlandrobotics.echelon2.blueAlliance.models.SyncTeam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    //tba
    //@GET("status")
    //Call<SyncStatus> getStatus();

    @GET("/")
    Call<SyncStatus> getStatus();

    //tba
    //@GET("districts/{year}")
<<<<<<< Updated upstream
    //Call<List<SyncDistrict>> getDistrictsByYear(@Path("year") int year);
=======
    //<List<SyncDistrict>> getDistrictsByYear(@Path("year") int year);
>>>>>>> Stashed changes

    @GET("regions")
    Call<List<SyncDistrict>> getRegions();
    // gets all FTC regions

    //todo: look into /seasons because year isn't available on region

    //tba
    //@GET("district/{district_key}/events")
    //Call<List<SyncEvent>> getEventsByDistrict(@Path("district_key") String districtKey);

    // todo: this doesn't actually exist
    @GET("event/{region_key}")
    Call<List<SyncEvent>> getEventsByRegion(@Path("region_key") String regionKey);

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

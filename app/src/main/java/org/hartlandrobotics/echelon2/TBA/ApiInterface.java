package org.hartlandrobotics.echelon2.TBA;

import org.hartlandrobotics.echelon2.TBA.models.SyncDistrict;
import org.hartlandrobotics.echelon2.TBA.models.SyncStatus;
import org.hartlandrobotics.echelon2.TBA.models.SyncTeam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("status")
    Call<SyncStatus> getStatus();

    @GET("districts/{year}")
    Call<List<SyncDistrict>> getDistrictsByYear(@Path("year") int year);

    @GET("event/{event_key}/teams")
    Call<List<SyncTeam>> getTeamsByEvent(@Path("event_key") String eventKey);

}

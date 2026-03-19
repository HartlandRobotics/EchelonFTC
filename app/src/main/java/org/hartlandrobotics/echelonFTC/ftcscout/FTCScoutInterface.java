package org.hartlandrobotics.echelonFTC.ftcscout;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FTCScoutInterface {
    @GET("events/{year}/{eventCode}/matches")
    Call<List<FtcScoutMatchScore>> getMatchScore(@Path("year") String year, @Path("eventCode") String eventCode);
}

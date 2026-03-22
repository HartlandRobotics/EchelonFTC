package org.hartlandrobotics.echelonFTC.ftcscout;

import org.hartlandrobotics.echelonFTC.ftcscout.models.FtcScoutMatchScore;
import org.hartlandrobotics.echelonFTC.ftcscout.models.FtcScoutTeamOpr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FTCScoutInterface {
    @GET("events/{year}/{eventCode}/matches")
    Call<List<FtcScoutMatchScore>> getMatchScores(@Path("year") String year, @Path("eventCode") String eventCode);

    @GET("events/{year}/{eventCode}/teams")
    Call<List<FtcScoutTeamOpr>> getOpr(@Path("year") String year, @Path("eventCode") String eventCode);
}

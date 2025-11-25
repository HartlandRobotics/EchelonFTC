package org.hartlandrobotics.echelonFTC.ftapi;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiStatus;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FtcApiInterface {
    @GET(StringUtils.EMPTY)
    Call<FtcApiStatus> getStatus();
}


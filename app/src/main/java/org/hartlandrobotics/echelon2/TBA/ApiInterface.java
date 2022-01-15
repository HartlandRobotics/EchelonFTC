package org.hartlandrobotics.echelon2.TBA;

import org.hartlandrobotics.echelon2.TBA.models.SyncStatus;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("status")
    Call<SyncStatus> getStatus();


}

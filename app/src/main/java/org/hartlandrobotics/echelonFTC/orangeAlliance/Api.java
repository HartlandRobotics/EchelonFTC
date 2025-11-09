package org.hartlandrobotics.echelonFTC.orangeAlliance;
//TODO: change to orange alliance

import android.content.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsViewModel;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Api {
    //private static String toaApiKey;
    private static String apiHeader;
    private static String userAgentPrefix = "Echelon/2.0 ";
    private static ApiInterface api;

    public static ApiInterface getApiClient(Context context) {
        if(api == null) {
            AdminSettingsViewModel vm = AdminSettingsProvider.getAdminSettings(context);
            assert vm != null;

            String userAgent = userAgentPrefix + " " + vm.getTeamNumber();
            apiHeader = vm.getApiHeader();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("User-Agent", userAgent)
                        .header("Authorization", "Basic " + apiHeader)
                        .build();
                return chain.proceed(request);
            });

            OkHttpClient client = httpClient.build();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ftc-api.firstinspires.org/v2.0/")
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .client(client)
                    .build();

            api = retrofit.create(ApiInterface.class);
        }
        return api;
    }
}

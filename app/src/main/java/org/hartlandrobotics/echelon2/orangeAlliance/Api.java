package org.hartlandrobotics.echelon2.orangeAlliance;
//TODO: change to orange alliance

import android.content.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsViewModel;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Api {
    //private static String toaApiKey;
    private static String apiKey;
    private static String userAgentPrefix = "Echelon/1.0 ";
    private static ApiInterface api;

    public static ApiInterface getApiClient(Context context) {
        if(api == null) {
            AdminSettingsViewModel vm = AdminSettingsProvider.getAdminSettings(context);
            String userAgent = userAgentPrefix + " " + vm.getTeamNumber();
            apiKey = vm.getOrangeAllianceApiKey();
            //tbaApiKey = vm.getBlueAllianceApiKey();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("User-Agent", userAgent)
                        //.header("X-TBA-Auth-Key", tbaApiKey)
                        .header("X-TOA-Key", apiKey)
                        .header("X-Application-Origin", apiKey)
                        .build();
                return chain.proceed(request);
            });

            OkHttpClient client = httpClient.build();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://theorangealliance.org/api/")
                    //.baseUrl("https://thebluealliance.com/api/v3/")
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .client(client)
                    .build();

            api = retrofit.create(ApiInterface.class);
        }
        return api;
    }
}

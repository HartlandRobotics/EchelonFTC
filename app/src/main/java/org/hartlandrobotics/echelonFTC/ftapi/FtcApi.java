package org.hartlandrobotics.echelonFTC.ftapi;

import android.content.Context;
import android.util.Base64;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsViewModel;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FtcApi {
    private static FtcApiInterface ftcApi;

    public static FtcApiInterface getApiClient(Context context) {

        if (ftcApi == null) {
            AdminSettingsViewModel vm = AdminSettingsProvider.getAdminSettings(context);
            String ftcApiKey = vm.getFtcApiKey();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", Credentials.basic("hartlandftc", ftcApiKey))
                        .build();
                return chain.proceed(request);
            });

            OkHttpClient client = httpClient.build();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://ftc-api.firstinspires.org/v2.0/")
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .client(client)
                    .build();

            ftcApi = retrofit.create(FtcApiInterface.class);
        }
        return ftcApi;
    }
}
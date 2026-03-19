package org.hartlandrobotics.echelonFTC.ftcscout;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FTCScoutApi {
    static String TAG = "FTCScoutApi";

    private static FTCScoutInterface ftcScoutInterface;

    public static FTCScoutInterface getFtcScoutClient(Context context) {
        if( ftcScoutInterface == null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                String path = chain.request().url().uri().getPath();
                Log.e(TAG,path);
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .build();
                return chain.proceed(request);
            });

            OkHttpClient client = httpClient.build();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.ftcscout.org/rest/v1/")
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .client(client)
                    .build();

            ftcScoutInterface = retrofit.create(FTCScoutInterface.class);
        }

        return ftcScoutInterface;
    }
}

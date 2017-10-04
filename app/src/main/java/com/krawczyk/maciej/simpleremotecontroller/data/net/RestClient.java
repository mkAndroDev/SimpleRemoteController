package com.krawczyk.maciej.simpleremotecontroller.data.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maciek on 21.08.17.
 */
public class RestClient {

    private static WeatherService weatherService;

    static {
        setupRestClient();
    }

    private RestClient() {
    }

    public static WeatherService getWeatherService() {
        return weatherService;
    }

    private static void setupRestClient() {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .serializeNulls()
                .setLenient()
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        okHttpClient.interceptors().add(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConfigEndpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient.build())
                .build();

        weatherService = retrofit.create(WeatherService.class);

    }
}

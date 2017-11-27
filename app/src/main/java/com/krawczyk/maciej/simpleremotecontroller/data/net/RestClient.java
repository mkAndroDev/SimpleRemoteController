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

    private RestClient() {
    }

    public static WeatherService getWeatherService() {
        return setupRestClient(ConfigEndpoints.getBaseUrl());
    }

    private static WeatherService setupRestClient(String url) {

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
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient.build())
                .build();

        return retrofit.create(WeatherService.class);
    }
}

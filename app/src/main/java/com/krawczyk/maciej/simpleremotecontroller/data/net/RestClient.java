package com.krawczyk.maciej.simpleremotecontroller.data.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
                .setLenient()
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor networkInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request newRequest;

                newRequest = request.newBuilder().addHeader("Authorization",
                        Credentials.basic("", ""))
                        .addHeader("Accept", ConfigEndpoints.HEADER_ACCEPT_TYPE)
                        .build();

                return chain.proceed(newRequest);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().
                addNetworkInterceptor(networkInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(35, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .writeTimeout(35, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        return retrofit.create(WeatherService.class);

    }
}

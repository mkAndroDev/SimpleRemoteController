package com.krawczyk.maciej.simpleremotecontroller.data.net;

import com.krawczyk.maciej.simpleremotecontroller.data.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Created by maciek on 03.10.17.
 */

public interface WeatherService {

    @GET("/getWeather")
    Call<Weather> getWeather();

    @PUT("/furnaceOnNow")
    Call<Weather> putFurnaceOnNow();

    @PUT("/furnaceOffNow")
    Call<Weather> putFurnaceOffNow();

    @PUT("/airingOnNow")
    Call<Weather> putAiringOnNow();

    @PUT("/airingOffNow")
    Call<Weather> putAiringOffNow();
}

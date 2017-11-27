package com.krawczyk.maciej.simpleremotecontroller.data.net;

import com.krawczyk.maciej.simpleremotecontroller.data.model.Weather;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Created by maciek on 03.10.17.
 */

public interface WeatherService {

    @GET("/getCurrentWeather")
    Call<Weather> getWeather();

    @GET("/getSetWeather")
    Call<Weather> getSetWeather();

    @PUT("/setTemperatureAndAiring")
    Call<Weather> setTemperatureAndAiring(@Body Weather weather);

    @GET("/getFurnace")
    Call<Boolean> getFurnaceOnOff();

    @PUT("/putFurnace")
    Call<Boolean> putFurnaceOnOff();

    @GET("/getAiring")
    Call<Boolean> getAiringOnOff();

    @PUT("/putAiring")
    Call<Boolean> putAiringOffNow();
}

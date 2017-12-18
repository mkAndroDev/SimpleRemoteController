package com.krawczyk.maciej.simpleremotecontroller.data.net;

import com.krawczyk.maciej.simpleremotecontroller.data.model.AiringModel;
import com.krawczyk.maciej.simpleremotecontroller.data.model.FurnaceModel;
import com.krawczyk.maciej.simpleremotecontroller.data.model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Created by maciek on 03.10.17.
 */

public interface WeatherService {

    @GET("/getCurrentWeather")
    Call<WeatherModel> getWeather();

    @GET("/getCurrentSet")
    Call<WeatherModel> getSetWeather();

    @PUT("/putCurrentSet")
    Call<WeatherModel> setTemperatureAndAiring(@Body WeatherModel weather);

    @GET("/isLaunched")
    Call<FurnaceModel> getFurnaceOnOff();

    @PUT("/putFurnace")
    Call<FurnaceModel> putFurnaceOnOff();

    @GET("/isLaunched")
    Call<AiringModel> getAiringOnOff();

    @PUT("/putAiring")
    Call<AiringModel> putAiringOffNow();
}

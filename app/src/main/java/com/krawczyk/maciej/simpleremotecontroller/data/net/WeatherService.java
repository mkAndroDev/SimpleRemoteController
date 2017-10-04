package com.krawczyk.maciej.simpleremotecontroller.data.net;

import com.krawczyk.maciej.simpleremotecontroller.data.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by maciek on 03.10.17.
 */

public interface WeatherService {

    @GET("/getWeather")
    Call<Weather> getWeather();

}

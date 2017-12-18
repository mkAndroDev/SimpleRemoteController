package com.krawczyk.maciej.simpleremotecontroller.domain.mappers

import com.krawczyk.maciej.simpleremotecontroller.data.model.Weather
import com.krawczyk.maciej.simpleremotecontroller.data.model.WeatherModel

/**
 * Created by maciek on 18.12.17.
 */
class WeatherMapper {

    companion object {
        public fun getWeatherRealm(weatherModel: WeatherModel): Weather {
            val weather = Weather()
            weather.temperature = weatherModel.temperature
            weather.humidity = weatherModel.humidity
            return weather
        }

        public fun getWeatherModel(weather: Weather): WeatherModel {
            val weatherModel = WeatherModel()
            weatherModel.temperature = weather.temperature
            weatherModel.humidity = weather.humidity
            return weatherModel
        }
    }
}
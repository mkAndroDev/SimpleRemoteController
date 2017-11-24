package com.krawczyk.maciej.simpleremotecontroller.android.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.krawczyk.maciej.simpleremotecontroller.data.net.RestClient
import com.krawczyk.maciej.simpleremotecontroller.data.net.WeatherService

/**
 * Created by maciek on 24.11.17.
 */
open class BaseFragment : Fragment() {

    lateinit var weatherService: WeatherService

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherService = RestClient.getWeatherService()
    }

}

package com.krawczyk.maciej.simpleremotecontroller.data.model

import com.google.gson.annotations.Expose

/**
 * Created by maciek on 03.10.17.
 */
class Weather {

    @Expose
    var temperature: Double = 0.0
    @Expose
    var humidity: Double = 0.0
}
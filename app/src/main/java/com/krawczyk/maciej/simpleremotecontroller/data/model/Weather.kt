package com.krawczyk.maciej.simpleremotecontroller.data.model

import io.realm.RealmObject

/**
 * Created by maciek on 03.10.17.
 */
open class Weather : RealmObject() {

    var temperature: Double = 0.0
    var humidity: Double = 0.0
    var date: String = ""

}
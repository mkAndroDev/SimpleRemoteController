package com.krawczyk.maciej.simpleremotecontroller.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by maciek on 03.10.17.
 */
class AiringModel {

    @Expose
    @SerializedName("airing")
    var isLaunched: Int = 0
}
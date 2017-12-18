package com.krawczyk.maciej.simpleremotecontroller.android.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.krawczyk.maciej.simpleremotecontroller.data.model.Weather
import com.krawczyk.maciej.simpleremotecontroller.data.net.RestClient
import com.krawczyk.maciej.simpleremotecontroller.data.net.WeatherService
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by maciek on 24.11.17.
 */
open class BaseFragment : Fragment() {

    lateinit var realm: Realm
    lateinit var weatherService: WeatherService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRealm()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherService = RestClient.getWeatherService()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun setupRealm() {
        Realm.init(activity)
        val config = RealmConfiguration.Builder()
                .name("weather.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build()
        Realm.setDefaultConfiguration(config)
        realm = Realm.getDefaultInstance()
    }

    fun saveWeather(weather: Weather) {
        realm.beginTransaction()
        realm.copyToRealm(weather)
        realm.commitTransaction()
    }

    fun getWeathers(): List<Weather> {
        return realm.where(Weather::class.java).findAll()
    }

}

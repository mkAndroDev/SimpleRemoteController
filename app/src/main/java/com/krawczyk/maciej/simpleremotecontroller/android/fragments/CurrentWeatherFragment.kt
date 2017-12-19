package com.krawczyk.maciej.simpleremotecontroller.android.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krawczyk.maciej.simpleremotecontroller.R
import com.krawczyk.maciej.simpleremotecontroller.android.activities.MainActivity
import com.krawczyk.maciej.simpleremotecontroller.data.model.Weather
import com.krawczyk.maciej.simpleremotecontroller.data.model.WeatherModel
import kotlinx.android.synthetic.main.fragment_current_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeatherFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

        val weatherService = weatherService.weather
        weatherService.enqueue(getCallback())

        this.btn_immediately_on_off.setOnClickListener {
            (activity as MainActivity).loadFragment(ImmediatelyOnOffFragment.newInstance())
        }

        this.btn_adjustable_on_off.setOnClickListener {
            (activity as MainActivity).loadFragment(AdjustableOnOffFragment.newInstance())
        }
    }

    private fun getCallback(): Callback<WeatherModel> {
        return object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.isSuccessful && response.body() != null) {
                    val weather = Weather()
                    weather.temperature = response.body()!!.temperature
                    weather.humidity = response.body()!!.humidity

                    val current = Calendar.getInstance()
                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

                    weather.date = simpleDateFormat.format(current.time)

                    saveWeather(weather)

                    tv_current_temperature.text = weather.temperature.toString() + getString(R.string.celsius_degree)
                    tv_current_humidity.text = weather.humidity.toString() + "%"
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.d("Weather Response: ", t.message)
            }
        }
    }

    companion object {
        fun newInstance(): CurrentWeatherFragment {
            return CurrentWeatherFragment()
        }
    }
}

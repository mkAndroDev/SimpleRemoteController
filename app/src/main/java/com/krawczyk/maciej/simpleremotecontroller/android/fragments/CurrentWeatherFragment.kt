package com.krawczyk.maciej.simpleremotecontroller.android.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krawczyk.maciej.simpleremotecontroller.R
import com.krawczyk.maciej.simpleremotecontroller.android.activities.MainActivity
import com.krawczyk.maciej.simpleremotecontroller.data.model.Weather
import kotlinx.android.synthetic.main.fragment_current_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private fun getCallback(): Callback<Weather> {
        return object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful && response.body() != null) {
                    tv_current_temperature.text = response.body()!!.temperature.toString() + getString(R.string.celsius_degree)
                    tv_current_humidity.text = response.body()!!.humidity.toString() + "%"
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d("Weather Response: ", t.message)

                val weather = Weather()
                weather.temperature = 23.3
                weather.humidity = 73.1

                tv_current_temperature.text = weather.temperature.toString() + getString(R.string.celsius_degree)
                tv_current_humidity.text = weather.humidity.toString() + "%"
            }
        }
    }

    companion object {
        fun newInstance(): CurrentWeatherFragment {
            return CurrentWeatherFragment()
        }
    }
}

package com.krawczyk.maciej.simpleremotecontroller.android.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krawczyk.maciej.simpleremotecontroller.R
import com.krawczyk.maciej.simpleremotecontroller.android.activities.MainActivity
import com.krawczyk.maciej.simpleremotecontroller.data.model.WeatherModel
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

    private fun getCallback(): Callback<WeatherModel> {
        return object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.isSuccessful && response.body() != null) {
                    tv_current_temperature.text = response.body()!!.temperature.toString() + getString(R.string.celsius_degree)
                    tv_current_humidity.text = response.body()!!.humidity.toString() + "%"
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.d("WeatherModel Response: ", t.message)
            }
        }
    }

    companion object {
        fun newInstance(): CurrentWeatherFragment {
            return CurrentWeatherFragment()
        }
    }
}

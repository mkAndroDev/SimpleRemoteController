package com.krawczyk.maciej.simpleremotecontroller.android.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krawczyk.maciej.simpleremotecontroller.R
import com.krawczyk.maciej.simpleremotecontroller.data.model.Weather
import kotlinx.android.synthetic.main.fragment_adjustable_on_off.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdjustableOnOffFragment : BaseFragment() {

    private val weather = Weather()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_adjustable_on_off, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {

        val getSetWeather = weatherService.setWeather
        getSetWeather.enqueue(getCallback())

        btn_set.setOnClickListener({
            weather.temperature = et_furnace_when.text.toString().toDouble()
            weather.humidity = et_airing_when.text.toString().toDouble()
            val setWeather = weatherService.setTemperatureAndAiring(weather)
            setWeather.enqueue(getCallback())
        })

    }

    private fun getCallback(): Callback<Weather> {
        return object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful && response.body() != null) {
                    et_furnace_when.setText(response.body()!!.temperature.toString())
                    et_airing_when.setText(response.body()!!.humidity.toString())
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d("Weather Response: ", t.message)

                val weather = Weather()
                weather.temperature = 23.3
                weather.humidity = 73.1

                et_furnace_when.setText(weather.temperature.toString())
                et_airing_when.setText(weather.humidity.toString())
            }
        }
    }

    companion object {
        fun newInstance(): AdjustableOnOffFragment {
            return AdjustableOnOffFragment()
        }
    }
}

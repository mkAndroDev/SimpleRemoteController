package com.krawczyk.maciej.simpleremotecontroller.android.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krawczyk.maciej.simpleremotecontroller.R
import com.krawczyk.maciej.simpleremotecontroller.data.model.Weather
import kotlinx.android.synthetic.main.weathers_item.view.*

/**
 * Created by maciek on 02.01.18.
 */
class HistoryWeatherAdapter(private var context: Context, private var historyWeathers: List<Weather>) : RecyclerView.Adapter<HistoryWeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.weathers_item, parent, false)

        return ViewHolder(itemView, historyWeathers, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setupWeather(position)
    }

    override fun getItemCount(): Int {
        return historyWeathers.size
    }

    class ViewHolder(view: View, private val weathers: List<Weather>, private val context: Context) : RecyclerView.ViewHolder(view) {

        private val dateTv = view.tv_date
        private val temperatureTv = view.tv_temperature
        private val humidityTv = view.tv_humidity

        internal fun setupWeather(position: Int) {

            val weather = weathers[position]

            dateTv.text = weather.date
            temperatureTv.text = weather.temperature.toString() + context.getString(R.string.celsius_degree)
            humidityTv.text = weather.humidity.toString() + "%"
        }
    }


}
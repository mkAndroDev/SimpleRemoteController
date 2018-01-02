package com.krawczyk.maciej.simpleremotecontroller.android.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krawczyk.maciej.simpleremotecontroller.R
import com.krawczyk.maciej.simpleremotecontroller.android.adapters.HistoryWeatherAdapter
import kotlinx.android.synthetic.main.fragment_history_weather.*

class HistoryWeatherFragment : BaseFragment() {

    private lateinit var historyWeatherRv: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_history_weather, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyWeatherRv = rv_history_weather

        setupViews()
    }

    private fun setupViews() {
        val componentsLayoutManager = LinearLayoutManager(context)
        historyWeatherRv.setHasFixedSize(true)
        historyWeatherRv.layoutManager = componentsLayoutManager

        historyWeatherRv.adapter = HistoryWeatherAdapter(context, getWeathers())
    }

    companion object {
        fun newInstance(): HistoryWeatherFragment {
            return HistoryWeatherFragment()
        }
    }
}

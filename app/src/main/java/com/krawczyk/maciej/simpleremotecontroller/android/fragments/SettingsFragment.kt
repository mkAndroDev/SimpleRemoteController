package com.krawczyk.maciej.simpleremotecontroller.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krawczyk.maciej.simpleremotecontroller.R
import com.krawczyk.maciej.simpleremotecontroller.data.net.ConfigEndpoints
import com.krawczyk.maciej.simpleremotecontroller.data.net.RestClient
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {

        et_url.setText(ConfigEndpoints.getBaseUrl())

        btn_set_url.setOnClickListener({
            ConfigEndpoints.setBaseUrl(et_url.text.toString())
            activity.onBackPressed()
        })

        weatherService = RestClient.getWeatherService()

    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}

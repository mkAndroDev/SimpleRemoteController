package com.krawczyk.maciej.simpleremotecontroller.android.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.krawczyk.maciej.simpleremotecontroller.R
import kotlinx.android.synthetic.main.fragment_immediately_on_off.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImmediatelyOnOffFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_immediately_on_off, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    fun setupViews() {

        val airingOnOff = weatherService.airingOnOff
        airingOnOff.enqueue(getCallback("Airing"))

        val furnaceOnOff = weatherService.furnaceOnOff
        furnaceOnOff.enqueue(getCallback("Furnace"))

        setupFurnaceChangeListener(false)
        setupAiringChangeListener(false)

    }

    private fun getCallback(type: String): Callback<Boolean> {
        return object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful && response.body() != null) {
                    if (type == "Airing") {
                        switch_start_airing.isChecked = response.body()!!
                    } else {
                        switch_start_furnace.isChecked = response.body()!!
                    }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d(type + " Response: ", t.message)
                if (isAdded) {
                    Toast.makeText(context, type + " Response: " + t.message, Toast.LENGTH_LONG).show()
                    if (type == "Airing") {
                        setupAiringChangeListener(true)
                        switch_start_airing.isChecked = !switch_start_airing.isChecked
                        setupAiringChangeListener(false)
                    } else {
                        setupFurnaceChangeListener(true)
                        switch_start_furnace.isChecked = !switch_start_furnace.isChecked
                        setupFurnaceChangeListener(false)
                    }
                }
            }
        }
    }

    private fun setupAiringChangeListener(listenerNull: Boolean) {
        if (listenerNull) {
            switch_start_airing.setOnCheckedChangeListener(null)
        } else {
            this.switch_start_airing.setOnCheckedChangeListener { switch, isChecked ->
                val putAiringOnOff = weatherService.putAiringOffNow()
                putAiringOnOff.enqueue(getCallback("Airing"))
            }
        }
    }

    private fun setupFurnaceChangeListener(listenerNull: Boolean) {
        if (listenerNull) {
            switch_start_furnace.setOnCheckedChangeListener(null)
        } else {
            switch_start_furnace.setOnCheckedChangeListener { switch, isChecked ->
                val putFurnaceOnOff = weatherService.putFurnaceOnOff()
                putFurnaceOnOff.enqueue(getCallback("Furnace"))
            }
        }
    }

    companion object {
        fun newInstance(): ImmediatelyOnOffFragment {
            return ImmediatelyOnOffFragment()
        }
    }
}

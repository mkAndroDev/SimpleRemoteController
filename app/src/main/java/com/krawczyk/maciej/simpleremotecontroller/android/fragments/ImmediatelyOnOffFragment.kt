package com.krawczyk.maciej.simpleremotecontroller.android.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.krawczyk.maciej.simpleremotecontroller.R
import com.krawczyk.maciej.simpleremotecontroller.data.model.AiringModel
import com.krawczyk.maciej.simpleremotecontroller.data.model.FurnaceModel
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

    private fun setupViews() {

        val airingOnOff = weatherService.airingOnOff
        airingOnOff.enqueue(getAiringCallback())

        val furnaceOnOff = weatherService.furnaceOnOff
        furnaceOnOff.enqueue(getFurnaceCallback())

        setupFurnaceChangeListener(false)
        setupAiringChangeListener(false)

    }

    private fun getAiringCallback(): Callback<AiringModel> {
        return object : Callback<AiringModel> {
            override fun onResponse(call: Call<AiringModel>, response: Response<AiringModel>) {
                if (response.isSuccessful && response.body() != null) {
                    setupAiringChangeListener(true)
                    switch_start_airing.isChecked = response.body()?.isLaunched == 1
                    setupAiringChangeListener(false)
                }
            }

            override fun onFailure(call: Call<AiringModel>, t: Throwable) {
                Log.d("Airing Response: ", t.message)
                if (isAdded) {
                    Toast.makeText(context, "Airing Response: " + t.message, Toast.LENGTH_LONG).show()
                    setupAiringChangeListener(true)
                    switch_start_airing.isChecked = !switch_start_airing.isChecked
                    setupAiringChangeListener(false)
                }
            }
        }
    }

    private fun getFurnaceCallback(): Callback<FurnaceModel> {
        return object : Callback<FurnaceModel> {
            override fun onResponse(call: Call<FurnaceModel>, response: Response<FurnaceModel>) {
                if (response.isSuccessful && response.body() != null) {
                    setupFurnaceChangeListener(true)
                    switch_start_furnace.isChecked = response.body()?.isLaunched == 1
                    setupFurnaceChangeListener(false)
                }
            }

            override fun onFailure(call: Call<FurnaceModel>, t: Throwable) {
                Log.d("Airing Response: ", t.message)
                if (isAdded) {
                    Toast.makeText(context, "Airing Response: " + t.message, Toast.LENGTH_LONG).show()
                    setupFurnaceChangeListener(true)
                    switch_start_furnace.isChecked = !switch_start_furnace.isChecked
                    setupFurnaceChangeListener(false)
                }
            }
        }
    }

    private fun setupAiringChangeListener(listenerNull: Boolean) {
        if (listenerNull) {
            switch_start_airing.setOnCheckedChangeListener(null)
        } else {
            this.switch_start_airing.setOnCheckedChangeListener { _, isChecked ->
                val putAiringOnOff = weatherService.putAiringOffNow()
                putAiringOnOff.enqueue(getAiringCallback())
            }
        }
    }

    private fun setupFurnaceChangeListener(listenerNull: Boolean) {
        if (listenerNull) {
            switch_start_furnace.setOnCheckedChangeListener(null)
        } else {
            switch_start_furnace.setOnCheckedChangeListener { _, isChecked ->
                val putFurnaceOnOff = weatherService.putFurnaceOnOff()
                putFurnaceOnOff.enqueue(getFurnaceCallback())
            }
        }
    }

    companion object {
        fun newInstance(): ImmediatelyOnOffFragment {
            return ImmediatelyOnOffFragment()
        }
    }
}

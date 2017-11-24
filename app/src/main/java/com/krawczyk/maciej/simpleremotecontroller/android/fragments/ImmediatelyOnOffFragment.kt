package com.krawczyk.maciej.simpleremotecontroller.android.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.krawczyk.maciej.simpleremotecontroller.R
import kotlinx.android.synthetic.main.fragment_immediately_on_off.*

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
        this.switch_start_furnace.setOnCheckedChangeListener { switch, isChecked ->
            Toast.makeText(context, "Furnace is: " + if (isChecked) "ON" else "OFF", Toast.LENGTH_SHORT).show()
        }

        this.switch_start_airing.setOnCheckedChangeListener { switch, isChecked ->
            Toast.makeText(context, "Airing is: " + if (isChecked) "ON" else "OFF", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance(): ImmediatelyOnOffFragment {
            return ImmediatelyOnOffFragment()
        }
    }
}

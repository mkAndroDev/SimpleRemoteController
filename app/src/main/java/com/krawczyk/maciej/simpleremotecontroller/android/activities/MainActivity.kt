package com.krawczyk.maciej.simpleremotecontroller.android.activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.krawczyk.maciej.simpleremotecontroller.R
import com.krawczyk.maciej.simpleremotecontroller.android.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setNavigationItemSelectedListener(this)

        loadFragment(CurrentWeatherFragment.newInstance(), false)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (supportFragmentManager.findFragmentByTag(CurrentWeatherFragment::class.java.simpleName) == null) {
                loadFragment(CurrentWeatherFragment.newInstance(), false)
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_actual_weather -> {
                loadFragment(CurrentWeatherFragment.newInstance(), false)
            }
            R.id.nav_history -> {
                loadFragment(HistoryWeatherFragment.newInstance(), false)
            }
            R.id.nav_instant_switch -> {
                loadFragment(ImmediatelyOnOffFragment.newInstance(), false)
            }
            R.id.nav_adjustable_switch -> {
                loadFragment(AdjustableOnOffFragment.newInstance(), false)
            }
            R.id.nav_settings -> {
                loadFragment(SettingsFragment.newInstance(), false)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadFragment(fragment: Fragment, addToBackStack: Boolean) {
        if (window.currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
        }

        val fragmentTransaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_content, fragment, fragment::class.java.simpleName)

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.getName())
        } else {
            clearFragmentBackStack()
        }

        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun clearFragmentBackStack() {
        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }
}

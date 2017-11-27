package com.krawczyk.maciej.simpleremotecontroller.android.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.krawczyk.maciej.simpleremotecontroller.R
import com.krawczyk.maciej.simpleremotecontroller.android.fragments.CurrentWeatherFragment
import com.krawczyk.maciej.simpleremotecontroller.android.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(CurrentWeatherFragment.newInstance())
    }

    private fun addFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.activity_content, fragment)
        transaction.commitAllowingStateLoss()
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_content, fragment)
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commitAllowingStateLoss()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.getItemId()

        if (id == R.id.action_settings) {

            loadFragment(SettingsFragment.newInstance())

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

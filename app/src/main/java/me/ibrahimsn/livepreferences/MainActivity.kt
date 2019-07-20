package me.ibrahimsn.livepreferences

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import me.ibrahimsn.library.LiveSharedPreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val liveSharedPreferences = LiveSharedPreferences(preferences)

        liveSharedPreferences.getString("exampleString", "default").observe(this, Observer<String> {
            //Log.d("###", it)
        })

        liveSharedPreferences.getInt("exampleInt", 0).observe(this, Observer<Int> {
            //Log.d("###", it.toString())
        })

        liveSharedPreferences.getBoolean("exampleBoolean", false).observe(this, Observer<Boolean> {
            //Log.d("###", it.toString())
        })

        liveSharedPreferences.listenMultiple(listOf("bool1", "bool2", "bool3"), false).observe(this, Observer {

        })
    }
}

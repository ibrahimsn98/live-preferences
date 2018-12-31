package me.ibrahimsn.livepreferences

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import me.ibrahimsn.library.LiveSharedPreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val liveSharedPreferences = LiveSharedPreferences(preferences)

        liveSharedPreferences.getString("exampleString", "default").observe(this, Observer<String> { value ->
            Log.d("###", value)
        })

        liveSharedPreferences.getInt("exampleInt", 0).observe(this, Observer<Int> { value ->
            Log.d("###", value.toString())
        })

        liveSharedPreferences.getBoolean("exampleBoolean", false).observe(this, Observer<Boolean> { value ->
            Log.d("###", value.toString())
        })

        liveSharedPreferences.listenMultiple(listOf("bool1", "bool2", "bool3"), false).observe(this, Observer<Pair<String, Boolean>> { value ->
            Log.d("###", "key: ${value!!.first} value: ${value.second}")
        })

        liveSharedPreferences.listenUpdatesOnly(listOf("pref1", "pref2", "pref3")).observe(this, Observer { key ->
            Log.d("###", "$key updated!")
        })
    }
}

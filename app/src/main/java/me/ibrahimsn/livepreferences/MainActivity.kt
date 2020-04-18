package me.ibrahimsn.livepreferences

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import me.ibrahimsn.library.LiveSharedPreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val liveSharedPreferences = LiveSharedPreferences(preferences)

        liveSharedPreferences
            .getString("exampleString", "default")
            .observe(this, Observer {
                //Log.d("###", it)
            })

        liveSharedPreferences
            .getInt("exampleInt", 0)
            .observe(this, Observer {
                //Log.d("###", it.toString())
            })

        liveSharedPreferences
            .getBoolean("exampleBoolean", false)
            .observe(this, Observer {
                //Log.d("###", it.toString())
            })

        liveSharedPreferences
            .listenMultiple(listOf("bool1", "bool2", "bool3"), false)
            .observe(this, Observer {

            })
    }
}
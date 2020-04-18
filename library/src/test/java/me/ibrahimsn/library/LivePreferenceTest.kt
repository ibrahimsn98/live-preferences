package me.ibrahimsn.library

import android.preference.PreferenceManager
import androidx.lifecycle.Observer
import me.ibrahimsn.library.support.BasePreferenceTest
import me.ibrahimsn.library.support.TestActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

class LivePreferenceTest : BasePreferenceTest() {

    companion object {

        private const val stringTypeKey = "string"
        private const val stringTypeValue = "string"

        private const val booleanTypeKey = "boolean"
        private const val booleanTypeValue = true

        private const val intTypeKey = "int"
        private const val intTypeValue = Int.MAX_VALUE

        private const val longTypeKey = "long"
        private const val longTypeValue = Long.MAX_VALUE

        private const val floatTypeKey = "float"
        private val floatTypeValue = Float.MAX_VALUE

        private const val stringSetTypeKey = "stringSet"
        private val stringSetTypeValue = setOf("first", "second", "third")

    }

    private lateinit var activityController: ActivityController<TestActivity>
    private lateinit var activity: TestActivity

    private lateinit var liveSharedPreferences: LiveSharedPreferences

    @Before
    fun initializePreferences() {
        activityController = Robolectric.buildActivity(TestActivity::class.java).create()
        activity = activityController.start().get()

        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)

        preferences.edit()
            .putString(stringTypeKey, null)
            .putBoolean(booleanTypeKey, false)
            .putInt(intTypeKey, Int.MIN_VALUE)
            .putLong(longTypeKey, Long.MIN_VALUE)
            .putFloat(floatTypeKey, Float.MIN_VALUE)
            .putStringSet(stringSetTypeKey, setOf())
            .apply()

        liveSharedPreferences = LiveSharedPreferences(
            preferences
        )
    }

    @Test
    fun stringValueCheck() {
        liveSharedPreferences.preferences
            .edit()
            .putString(stringTypeKey, stringTypeValue)
            .apply()
        liveSharedPreferences.getString(stringTypeKey, null).observe(activity, Observer {
            assertEquals(
                stringTypeValue,
                it
            )
        })
    }

    @Test
    fun booleanValueCheck() {
        liveSharedPreferences.preferences
            .edit()
            .putBoolean(booleanTypeKey, booleanTypeValue)
            .apply()
        liveSharedPreferences.getBoolean(booleanTypeKey, false).observe(activityController.destroy().get(), Observer {
            assertEquals(
                booleanTypeValue,
                it
            )
        })
    }

    @Test
    fun intValueCheck() {
        liveSharedPreferences.preferences
            .edit()
            .putInt(intTypeKey, intTypeValue)
            .apply()
        liveSharedPreferences.getInt(intTypeKey, Int.MIN_VALUE).observe(activity, Observer {
            assertEquals(
                intTypeValue,
                it
            )
        })
    }

    @Test
    fun longValueCheck() {
        liveSharedPreferences.preferences
            .edit()
            .putLong(longTypeKey, longTypeValue)
            .apply()
        liveSharedPreferences.getLong(longTypeKey, Long.MIN_VALUE).observe(activity, Observer {
            assertEquals(
                longTypeValue,
                it
            )
        })
    }

    @Test
    fun floatValueCheck() {
        liveSharedPreferences.preferences
            .edit()
            .putFloat(floatTypeKey, floatTypeValue)
            .apply()
        liveSharedPreferences.getFloat(floatTypeKey, Float.MIN_VALUE).observe(activity, Observer {
            assertEquals(
                floatTypeValue,
                it
            )
        })
    }

    @Test
    fun stringSetValueCheck() {
        liveSharedPreferences.preferences
            .edit()
            .putStringSet(stringSetTypeKey, stringSetTypeValue)
            .apply()
        liveSharedPreferences.getStringSet(stringSetTypeKey, setOf()).observe(activity, Observer {
            assertEquals(
                stringSetTypeValue,
                it
            )
        })
    }

    @Test
    fun nullCheck() {
        liveSharedPreferences.getString("stringNull", null).observe(activity, Observer {
            assertNull(
                it
            )
        })
    }
}
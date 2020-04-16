package me.ibrahimsn.library

import android.preference.PreferenceManager
import androidx.lifecycle.Observer
import me.ibrahimsn.library.support.BasePreferenceTest
import me.ibrahimsn.library.support.TestActivity
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsIterableContainingInOrder.contains
import org.junit.Before
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

class MultiPreferenceTest : BasePreferenceTest() {

    companion object {

        private const val firstStringValueKey = "fsvk"
        private const val firstStringValue = "Red"

        private const val secondStringValueKey = "ssvk"
        private const val secondStringValue = "Green"

        private const val thirdStringValueKey = "tsvk"
        private const val thirdStringValue = "Blue"

    }

    private lateinit var activityController: ActivityController<TestActivity>
    private lateinit var activity: TestActivity

    private lateinit var liveSharedPreferences: LiveSharedPreferences

    @Before
    fun initializePreferences() {
        activityController = Robolectric.buildActivity(TestActivity::class.java).create()
        activity = activityController.start().get()
        val preferences = PreferenceManager.getDefaultSharedPreferences(
            activity
        )
        preferences.edit()
            .putString(firstStringValueKey, firstStringValue)
            .putString(secondStringValueKey, secondStringValue)
            .putString(thirdStringValueKey, thirdStringValue)
            .apply()

        liveSharedPreferences = LiveSharedPreferences(
            preferences
        )
    }

    @Test
    fun multipleStringsCheck() {
        liveSharedPreferences.listenMultiple(
            listOf(
                firstStringValueKey,
                secondStringValueKey,
                thirdStringValueKey
            ),
            null
        ).observe(activity, Observer {
            assertThat(
                it.values,
                contains(
                    firstStringValue,
                    secondStringValue,
                    thirdStringValue
                )
            )
        })
    }

}

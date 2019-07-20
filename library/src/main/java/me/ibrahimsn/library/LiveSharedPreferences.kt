package me.ibrahimsn.library

import android.content.SharedPreferences
import io.reactivex.Observable
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.util.Log
import io.reactivex.ObservableOnSubscribe
import io.reactivex.subjects.PublishSubject

class LiveSharedPreferences constructor(private val preferences: SharedPreferences) {

    private val publisher = PublishSubject.create<String>()
    private val listener = OnSharedPreferenceChangeListener { _, key -> publisher.onNext(key) }

    /**
     * Detect subscription and dispose events
     */
    private val updates = publisher.doOnSubscribe {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }.doOnDispose {
        if (!publisher.hasObservers())
            preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun getPreferences(): SharedPreferences {
        return preferences
    }

    fun getString(key: String, defaultValue: String): LivePreference<String> {
        return LivePreference(updates, preferences, key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): LivePreference<Int> {
        return LivePreference(updates, preferences, key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): LivePreference<Boolean> {
        return LivePreference(updates, preferences, key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): LivePreference<Float> {
        return LivePreference(updates, preferences, key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): LivePreference<Long> {
        return LivePreference(updates, preferences, key, defaultValue)
    }

    fun getStringSet(key: String, defaultValue: Set<String>): LivePreference<Set<String>> {
        return LivePreference(updates, preferences, key, defaultValue)
    }

    fun <T> listenMultiple(keys: List<String>, defaultValue: T): MultiPreference<T> {
        return MultiPreference(updates, preferences, keys, defaultValue)
    }
}
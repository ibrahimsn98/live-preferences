package me.ibrahimsn.library

import android.content.SharedPreferences
import io.reactivex.Observable
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import io.reactivex.ObservableOnSubscribe

class LiveSharedPreferences constructor(private val preferences: SharedPreferences) {

    private lateinit var listener: OnSharedPreferenceChangeListener
    private val updates: Observable<String>

    init {
        updates = Observable.create(ObservableOnSubscribe<String> { emitter ->
            listener = OnSharedPreferenceChangeListener { _, key -> emitter.onNext(key) }

            emitter.setCancellable { preferences.unregisterOnSharedPreferenceChangeListener(listener) }

            preferences.registerOnSharedPreferenceChangeListener(listener)
        }).share()
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

    fun listenUpdatesOnly(keys: List<String>): MultiPreferenceAny {
        return MultiPreferenceAny(updates, keys)
    }
}
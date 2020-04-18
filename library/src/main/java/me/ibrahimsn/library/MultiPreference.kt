package me.ibrahimsn.library

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@Suppress("UNCHECKED_CAST")
class MultiPreference<T> constructor(
    private val updates: Observable<String>,
    private val preferences: SharedPreferences,
    private val keys: List<String>,
    private val defaultValue: T?
) : MutableLiveData<Map<String, T?>>() {

    private var disposable: Disposable? = null
    private val values = mutableMapOf<String, T?>()

    init {
        for (key in keys) {
            values[key] = (preferences.all[key] as T) ?: defaultValue
        }

        value = values
    }

    override fun onActive() {
        super.onActive()
        disposable = updates
            .filter { t -> keys.contains(t) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                values[it] = (preferences.all[it] as T) ?: defaultValue
                postValue(values)
            }
    }

    override fun onInactive() {
        super.onInactive()
        disposable?.dispose()
    }
}
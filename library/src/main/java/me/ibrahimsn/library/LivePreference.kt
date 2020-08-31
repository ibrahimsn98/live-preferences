package me.ibrahimsn.library

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@Suppress("UNCHECKED_CAST")
class LivePreference<T> constructor(
    private val updates: Observable<String>,
    private val preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: T?
) : MutableLiveData<T>() {

    private var disposable: Disposable? = null
    private var lastValue: T? = null

    init {
        lastValue = (preferences.all[key] as T) ?: defaultValue
        value = lastValue
    }

    override fun onActive() {
        super.onActive()

        if (lastValue != preferences.all[key]) {
            lastValue = preferences.all[key] as T
            postValue(lastValue)
        }

        disposable = updates
            .filter { t -> t == key }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                postValue((preferences.all[it] as T) ?: defaultValue)
            }
    }

    override fun onInactive() {
        super.onInactive()
        disposable?.dispose()
    }
}
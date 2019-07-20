package me.ibrahimsn.library

import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

@Suppress("UNCHECKED_CAST")
class MultiPreference<T> constructor(private val updates: Observable<String>,
                                     private val preferences: SharedPreferences,
                                     private val keys: List<String>,
                                     private val defaultValue: T) : MutableLiveData<Map<String, T>>() {

    private val disposable = CompositeDisposable()
    private val values = mutableMapOf<String, T>()

    init {
        for (key in keys)
            values[key] = preferences.all[key] as T ?: defaultValue
    }

    override fun onActive() {
        super.onActive()
        value = values

        disposable.add(updates.filter { t -> keys.contains(t) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object: DisposableObserver<String>() {
                override fun onComplete() {

                }

                override fun onNext(t: String) {
                    values[t] = preferences.all[t] as T ?: defaultValue
                    postValue(values)
                }

                override fun onError(e: Throwable) {

                }
            }))
    }

    override fun onInactive() {
        super.onInactive()
        disposable.dispose()
    }
}
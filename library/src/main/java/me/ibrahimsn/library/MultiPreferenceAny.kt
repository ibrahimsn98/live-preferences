package me.ibrahimsn.library

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MultiPreferenceAny constructor(private val updates: Observable<String>,
                                     private val keys: List<String>) : MutableLiveData<String>() {

    private val disposable = CompositeDisposable()

    override fun onActive() {
        super.onActive()
        disposable.add(updates.filter { t -> keys.contains(t) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object: DisposableObserver<String>() {
                override fun onComplete() {

                }

                override fun onNext(t: String) {
                    postValue(t)
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
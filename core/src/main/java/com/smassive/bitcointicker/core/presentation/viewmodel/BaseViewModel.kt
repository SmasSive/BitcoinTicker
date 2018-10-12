package com.smassive.bitcointicker.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.smassive.bitcointicker.core.util.addTo
import com.smassive.bitcointicker.core.util.onCompleteStub
import com.smassive.bitcointicker.core.util.onErrorStub
import com.smassive.bitcointicker.core.util.onNextStub
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class BaseViewModel(
    private val subscribeOn: Scheduler = Schedulers.io(),
    private val observeOn: Scheduler = AndroidSchedulers.mainThread()
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  override fun onCleared() {
    compositeDisposable.clear()
    super.onCleared()
  }

  protected fun Completable.execute(
      onComplete: () -> Unit = onCompleteStub,
      onError: (Throwable) -> Unit = onErrorStub
  ) {
    this.subscribeOn(subscribeOn).observeOn(observeOn).subscribe(onComplete, onError).addTo(compositeDisposable)
  }

  protected fun <T : Any> Single<T>.execute(
      onSuccess: (T) -> Unit = onNextStub,
      onError: (Throwable) -> Unit = onErrorStub
  ) {
    this.subscribeOn(subscribeOn).observeOn(observeOn).subscribe(onSuccess, onError).addTo(compositeDisposable)
  }

  protected fun <T : Any> Flowable<T>.execute(
      onNext: (T) -> Unit = onNextStub,
      onError: (Throwable) -> Unit = onErrorStub,
      onComplete: () -> Unit = onCompleteStub
  ) {
    this.subscribeOn(subscribeOn).observeOn(observeOn).subscribe(onNext, onError, onComplete).addTo(compositeDisposable)
  }
}
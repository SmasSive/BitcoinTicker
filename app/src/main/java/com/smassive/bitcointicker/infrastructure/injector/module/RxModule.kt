package com.smassive.bitcointicker.infrastructure.injector.module

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class RxModule {

  @Provides
  @Singleton
  @Named("subscribeOn")
  fun provideSubscribeOn() = Schedulers.io()

  @Provides
  @Singleton
  @Named("observeOn")
  fun provideObserveOn() = AndroidSchedulers.mainThread()
}

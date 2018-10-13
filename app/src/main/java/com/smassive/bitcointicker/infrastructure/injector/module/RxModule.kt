package com.smassive.bitcointicker.infrastructure.injector.module

import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.ObserveOn
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.SubscribeOn
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class RxModule {

  @Provides
  @Singleton
  @SubscribeOn
  fun provideSubscribeOn(): Scheduler = Schedulers.io()

  @Provides
  @Singleton
  @ObserveOn
  fun provideObserveOn(): Scheduler = AndroidSchedulers.mainThread()
}

package com.smassive.bitcointicker.infrastructure.injector.module

import android.content.Context
import com.smassive.bitcointicker.core.infrastructure.annotation.OpenClassOnDebug
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.ForApplication
import com.smassive.bitcointicker.infrastructure.BitcoinTickerApplication
import dagger.Module
import dagger.Provides

@Module
@OpenClassOnDebug
class ApplicationModule {

  @Provides
  @ForApplication
  fun provideApplicationContext(application: BitcoinTickerApplication): Context = application.applicationContext
}
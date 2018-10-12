package com.smassive.bitcointicker.infrastructure.injector.component

import com.smassive.bitcointicker.core.infrastructure.injector.module.ActivityModule
import com.smassive.bitcointicker.infrastructure.BitcoinTickerApplication
import com.smassive.bitcointicker.infrastructure.injector.module.ApplicationModule
import com.smassive.bitcointicker.infrastructure.injector.module.NetworkModule
import com.smassive.bitcointicker.infrastructure.injector.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {

  fun inject(application: BitcoinTickerApplication)

  fun createMainComponent(module: ActivityModule): MainComponent

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: BitcoinTickerApplication): Builder

    fun build(): ApplicationComponent
  }
}
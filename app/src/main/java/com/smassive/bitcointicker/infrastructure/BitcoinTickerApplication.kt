package com.smassive.bitcointicker.infrastructure

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import com.jakewharton.threetenabp.AndroidThreeTen
import com.smassive.bitcointicker.infrastructure.injector.component.ApplicationComponent
import com.smassive.bitcointicker.infrastructure.injector.component.DaggerApplicationComponent

class BitcoinTickerApplication : Application() {

  lateinit var component: ApplicationComponent

  override fun onCreate() {
    super.onCreate()
    component = DaggerApplicationComponent.builder().application(this).build()
    component.inject(this)
    AndroidThreeTen.init(this)
  }

  @VisibleForTesting
  fun updateComponent(newComponent: ApplicationComponent) {
    component = newComponent
  }
}

fun Context.asApp() = this.applicationContext as BitcoinTickerApplication
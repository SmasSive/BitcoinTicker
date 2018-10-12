package com.smassive.bitcointicker.infrastructure

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.smassive.bitcointicker.infrastructure.injector.component.ApplicationComponent
import com.smassive.bitcointicker.infrastructure.injector.component.DaggerApplicationComponent

class BitcoinTickerApplication : Application() {

  val component: ApplicationComponent by lazy { DaggerApplicationComponent.builder().application(this).build() }

  override fun onCreate() {
    super.onCreate()
    component.inject(this)
    AndroidThreeTen.init(this)
  }
}

fun Context.asApp() = this.applicationContext as BitcoinTickerApplication
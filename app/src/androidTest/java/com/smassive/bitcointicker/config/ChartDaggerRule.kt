package com.smassive.bitcointicker.config

import androidx.test.platform.app.InstrumentationRegistry
import com.smassive.bitcointicker.charts.infrastructure.injector.module.ChartDataModule
import com.smassive.bitcointicker.infrastructure.BitcoinTickerApplication
import com.smassive.bitcointicker.infrastructure.asApp
import com.smassive.bitcointicker.infrastructure.injector.component.ApplicationComponent
import com.smassive.bitcointicker.infrastructure.injector.module.ApplicationModule
import it.cosenonjaviste.daggermock.DaggerMock
import it.cosenonjaviste.daggermock.DaggerMockRule

fun getChartDaggerRule(): DaggerMockRule<ApplicationComponent> =
    DaggerMock.rule(ApplicationModule(), ChartDataModule()) {
      set { component -> app.updateComponent(component) }
      customizeBuilder<ApplicationComponent.Builder> { it.application(app) }
    }

val app: BitcoinTickerApplication = InstrumentationRegistry.getInstrumentation().targetContext.asApp()

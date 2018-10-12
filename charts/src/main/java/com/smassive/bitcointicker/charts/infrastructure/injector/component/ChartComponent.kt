package com.smassive.bitcointicker.charts.infrastructure.injector.component

import com.smassive.bitcointicker.charts.infrastructure.injector.module.ChartModule
import com.smassive.bitcointicker.charts.presentation.view.ChartFragment
import com.smassive.bitcointicker.core.infrastructure.injector.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ChartModule::class])
interface ChartComponent {

  fun inject(chartFragment: ChartFragment)

  interface ChartComponentCreator {
    fun createChartComponent(): ChartComponent
  }
}
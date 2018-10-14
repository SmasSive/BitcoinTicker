package com.smassive.bitcointicker.charts.infrastructure.injector.module

import com.smassive.bitcointicker.charts.presentation.model.formatter.DateValueFormatter
import com.smassive.bitcointicker.core.infrastructure.injector.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class ChartModule {

  @Provides
  @FragmentScope
  fun provideDateValueFormatter(): DateValueFormatter {
    return DateValueFormatter()
  }
}
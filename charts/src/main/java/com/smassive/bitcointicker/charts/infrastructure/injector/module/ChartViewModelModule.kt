package com.smassive.bitcointicker.charts.infrastructure.injector.module

import androidx.lifecycle.ViewModelProvider
import com.smassive.bitcointicker.charts.presentation.viewmodel.MarketPriceChartViewModel
import com.smassive.bitcointicker.core.presentation.viewmodel.ViewModelUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ChartViewModelModule {

  @Provides
  @Singleton
  @JvmStatic
  fun provideMarketPriceChartViewModel(viewModelUtil: ViewModelUtil, marketPriceChartViewModel: MarketPriceChartViewModel): ViewModelProvider.Factory {
    return viewModelUtil.createFor(marketPriceChartViewModel)
  }
}
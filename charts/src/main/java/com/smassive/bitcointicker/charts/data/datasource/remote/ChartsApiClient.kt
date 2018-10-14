package com.smassive.bitcointicker.charts.data.datasource.remote

import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartMarketPriceDto
import com.smassive.bitcointicker.core.infrastructure.annotation.OpenClassOnDebug
import io.reactivex.Maybe
import javax.inject.Inject

@OpenClassOnDebug
class ChartsApiClient @Inject constructor(private val chartsApi: ChartsApi) {

  fun getChart(chartName: String): Maybe<ChartMarketPriceDto> {
    return chartsApi.getChart(chartName)
  }
}
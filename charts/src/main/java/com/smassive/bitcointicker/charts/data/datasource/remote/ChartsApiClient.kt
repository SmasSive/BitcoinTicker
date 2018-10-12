package com.smassive.bitcointicker.charts.data.datasource.remote

import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartMarketPriceDto
import io.reactivex.Maybe
import javax.inject.Inject

class ChartsApiClient @Inject constructor(private val chartsApi: ChartsApi) {

  fun getChart(chartName: String): Maybe<ChartMarketPriceDto> {
    return chartsApi.getChart(chartName)
  }
}
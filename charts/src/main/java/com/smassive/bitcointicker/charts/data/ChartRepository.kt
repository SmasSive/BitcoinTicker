package com.smassive.bitcointicker.charts.data

import com.smassive.bitcointicker.charts.data.datasource.remote.ChartsApiClient
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartNameDto
import com.smassive.bitcointicker.charts.data.datasource.remote.model.mapper.ChartMarketPriceDtoMapper
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import io.reactivex.Flowable
import javax.inject.Inject

class ChartRepository @Inject constructor(private val chartsApiClient: ChartsApiClient,
                                          private val chartMarketPriceDtoMapper: ChartMarketPriceDtoMapper) {

  fun getMarketPriceChart(): Flowable<MarketPriceChart> {
    return chartsApiClient.getChart(ChartNameDto.MARKET_PRICE).map { chartMarketPriceDtoMapper.map(it) }.toFlowable()
  }
}

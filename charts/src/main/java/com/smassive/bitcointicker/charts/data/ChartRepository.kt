package com.smassive.bitcointicker.charts.data

import com.smassive.bitcointicker.charts.data.datasource.local.ChartLocalDatasource
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartValueEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartWithValuesEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartWithValuesMapper
import com.smassive.bitcointicker.charts.data.datasource.remote.ChartsApiClient
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartNameDto
import com.smassive.bitcointicker.charts.data.datasource.remote.model.mapper.ChartMarketPriceDtoMapper
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.core.data.exception.NoDataException
import com.smassive.bitcointicker.core.infrastructure.annotation.OpenClassOnDebug
import com.smassive.bitcointicker.core.util.toMillis
import io.reactivex.Flowable
import io.reactivex.Maybe

@OpenClassOnDebug
class ChartRepository(private val chartsApiClient: ChartsApiClient,
                      private val chartLocalDatasource: ChartLocalDatasource,
                      private val chartMarketPriceDtoMapper: ChartMarketPriceDtoMapper,
                      private val chartWithValuesEntityMapper: ChartWithValuesEntityMapper,
                      private val chartEntityMapper: ChartEntityMapper,
                      private val chartValueEntityMapper: ChartValueEntityMapper,
                      private val chartWithValuesMapper: ChartWithValuesMapper) {

  fun getMarketPriceChart(): Flowable<MarketPriceChart> {
    return fetchDataFromLocal().onErrorResumeNext { _: Throwable ->
      fetchDataFromRemote().onErrorResumeNext { _: Throwable -> fetchDataFromLocal(true) }
    }
  }

  private fun fetchDataFromLocal(forceLocal: Boolean = false): Flowable<MarketPriceChart> {
    return chartLocalDatasource.getChart(ChartNameDto.MARKET_PRICE, forceLocal).map { chartWithValuesMapper.map(it) }
  }

  private fun fetchDataFromRemote(): Flowable<MarketPriceChart> {
    return chartsApiClient.getChart(ChartNameDto.MARKET_PRICE)
        .switchIfEmpty(Maybe.error(NoDataException()))
        .doOnSuccess { chartMarketPriceDto ->
          val lastTimestamp = chartMarketPriceDto.values.sortedBy { it.timestamp }.last().timestamp.toMillis()
          chartLocalDatasource.saveMarketPriceChart(
              chartWithValuesEntityMapper.map(chartEntityMapper.map(chartMarketPriceDto, ChartNameDto.MARKET_PRICE, lastTimestamp),
                  chartValueEntityMapper.mapToEntity(chartMarketPriceDto.values, ChartNameDto.MARKET_PRICE)))
        }
        .map { chartMarketPriceDtoMapper.map(it) }
        .toFlowable()
  }
}

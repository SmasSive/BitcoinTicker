package com.smassive.bitcointicker.charts.data

import com.smassive.bitcointicker.charts.data.datasource.local.ChartDao
import com.smassive.bitcointicker.charts.data.datasource.local.ChartValueDao
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartValueEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartWithValuesEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.remote.ChartsApiClient
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartNameDto
import com.smassive.bitcointicker.charts.data.datasource.remote.model.mapper.ChartMarketPriceDtoMapper
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import io.reactivex.Flowable
import javax.inject.Inject

class ChartRepository @Inject constructor(private val chartsApiClient: ChartsApiClient,
                                          private val chartDao: ChartDao,
                                          private val chartValueDao: ChartValueDao,
                                          private val chartMarketPriceDtoMapper: ChartMarketPriceDtoMapper,
                                          private val chartWithValuesEntityMapper: ChartWithValuesEntityMapper,
                                          private val chartEntityMapper: ChartEntityMapper,
                                          private val chartValueEntityMapper: ChartValueEntityMapper) {

  fun getMarketPriceChart(): Flowable<MarketPriceChart> {
    return chartDao.getChart(ChartNameDto.MARKET_PRICE)
        .flatMap { chartEntity ->
          if (chartEntity.isEmpty()) fetchDataFromRemote()
          else completeDataFromLocal(chartEntity)
        }
  }

  private fun fetchDataFromRemote(): Flowable<MarketPriceChart> {
    return chartsApiClient.getChart(ChartNameDto.MARKET_PRICE)
        .doOnSuccess { chartMarketPriceDto ->
          chartDao.insert(chartEntityMapper.map(chartMarketPriceDto, ChartNameDto.MARKET_PRICE))
          chartValueDao.insertAll(chartValueEntityMapper.mapToEntity(chartMarketPriceDto.values, ChartNameDto.MARKET_PRICE))
        }
        .map { chartMarketPriceDtoMapper.map(it) }
        .toFlowable()
  }

  private fun completeDataFromLocal(chartEntity: List<ChartEntity>): Flowable<MarketPriceChart>? {
    return Flowable.just(chartEntity.first()).flatMap(
        { chartValueDao.getChartValues(ChartNameDto.MARKET_PRICE) },
        { chartEntities, chartValueEntities -> chartWithValuesEntityMapper.map(chartEntities, chartValueEntities) }
    )
  }
}

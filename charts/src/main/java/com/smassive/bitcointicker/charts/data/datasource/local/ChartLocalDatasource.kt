package com.smassive.bitcointicker.charts.data.datasource.local

import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartWithValuesEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartWithValuesEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartNameDto
import com.smassive.bitcointicker.core.data.exception.LocalDataExpiredException
import com.smassive.bitcointicker.core.data.exception.NoDataException
import com.smassive.bitcointicker.core.infrastructure.annotation.OpenClassOnDebug
import io.reactivex.Flowable
import javax.inject.Inject

@OpenClassOnDebug
class ChartLocalDatasource @Inject constructor(private val chartDao: ChartDao,
                                               private val chartValueDao: ChartValueDao,
                                               private val chartLocalDataValidator: ChartLocalDataValidator,
                                               private val chartWithValuesEntityMapper: ChartWithValuesEntityMapper) {

  fun getChart(chartName: String, forceLocal: Boolean): Flowable<ChartWithValuesEntity> {
    return chartDao.getChart(chartName)
        .flatMap { chartEntities ->
          if (chartEntities.isEmpty()) Flowable.error(NoDataException())
          else {
            if (forceLocal || chartLocalDataValidator.isValid(chartEntities)) completeDataFromLocal(chartEntities)
            else Flowable.error(LocalDataExpiredException())
          }
        }
  }

  private fun completeDataFromLocal(chartEntities: List<ChartEntity>): Flowable<ChartWithValuesEntity> {
    return Flowable.just(chartEntities.first()).flatMap(
        { chartValueDao.getChartValues(ChartNameDto.MARKET_PRICE) },
        { chartEntity, chartValueEntities -> chartWithValuesEntityMapper.map(chartEntity, chartValueEntities) }
    )
  }

  fun saveMarketPriceChart(chartWithValuesEntity: ChartWithValuesEntity) {
    chartDao.insert(chartWithValuesEntity.chart)
    chartValueDao.insertAll(chartWithValuesEntity.values)
  }
}
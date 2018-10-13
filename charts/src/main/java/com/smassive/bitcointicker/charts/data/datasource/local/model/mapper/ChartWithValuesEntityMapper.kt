package com.smassive.bitcointicker.charts.data.datasource.local.model.mapper

import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartValueEntity
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.core.data.model.mapper.PeriodMapper
import com.smassive.bitcointicker.core.data.model.mapper.UnitMapper
import javax.inject.Inject

class ChartWithValuesEntityMapper @Inject constructor(private val unitMapper: UnitMapper,
                                                      private val periodMapper: PeriodMapper,
                                                      private val chartValueEntityMapper: ChartValueEntityMapper) {

  fun map(chartEntity: ChartEntity, chartValueEntities: List<ChartValueEntity>): MarketPriceChart {
    return MarketPriceChart(
        unitMapper.map(chartEntity.unit),
        periodMapper.map(chartEntity.period),
        chartEntity.description,
        chartValueEntityMapper.map(chartValueEntities))
  }
}
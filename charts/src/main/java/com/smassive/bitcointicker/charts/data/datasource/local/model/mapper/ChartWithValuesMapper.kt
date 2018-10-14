package com.smassive.bitcointicker.charts.data.datasource.local.model.mapper

import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartWithValuesEntity
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.core.data.model.mapper.PeriodMapper
import com.smassive.bitcointicker.core.data.model.mapper.UnitMapper
import javax.inject.Inject

class ChartWithValuesMapper @Inject constructor(private val unitMapper: UnitMapper,
                                                private val periodMapper: PeriodMapper,
                                                private val chartValueEntityMapper: ChartValueEntityMapper) {

  fun map(chartWithValuesEntity: ChartWithValuesEntity): MarketPriceChart {
    return MarketPriceChart(
        unitMapper.map(chartWithValuesEntity.chart.unit),
        periodMapper.map(chartWithValuesEntity.chart.period),
        chartWithValuesEntity.chart.description,
        chartValueEntityMapper.map(chartWithValuesEntity.values))
  }
}
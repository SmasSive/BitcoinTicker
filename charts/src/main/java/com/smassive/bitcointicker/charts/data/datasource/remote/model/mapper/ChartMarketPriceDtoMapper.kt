package com.smassive.bitcointicker.charts.data.datasource.remote.model.mapper

import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartMarketPriceDto
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.core.data.model.mapper.UnitMapper
import com.smassive.bitcointicker.core.data.model.mapper.PeriodMapper
import javax.inject.Inject

class ChartMarketPriceDtoMapper @Inject constructor(private val chartValuesDtoMapper: ChartValuesDtoMapper,
                                                    private val unitMapper: UnitMapper,
                                                    private val periodMapper: PeriodMapper) {

  fun map(chartMarketPriceDto: ChartMarketPriceDto): MarketPriceChart {
    return with(chartMarketPriceDto) {
      MarketPriceChart(unitMapper.map(unit), periodMapper.map(period), description, chartValuesDtoMapper.map(values))
    }
  }
}
package com.smassive.bitcointicker.charts.data.datasource.local.model.mapper

import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartMarketPriceDto
import javax.inject.Inject

class ChartEntityMapper @Inject constructor() {

  fun map(chartMarketPriceDto: ChartMarketPriceDto, chartName: String): ChartEntity {
    return with(chartMarketPriceDto) { ChartEntity(chartName, unit, period, description) }
  }
}
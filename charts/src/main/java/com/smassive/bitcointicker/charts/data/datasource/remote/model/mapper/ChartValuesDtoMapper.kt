package com.smassive.bitcointicker.charts.data.datasource.remote.model.mapper

import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartValueDto
import com.smassive.bitcointicker.charts.domain.model.TimePriceChartValue
import com.smassive.bitcointicker.core.data.model.mapper.DateMapper
import javax.inject.Inject

class ChartValuesDtoMapper @Inject constructor(private val dateMapper: DateMapper) {

  fun map(chartValuesDto: List<ChartValueDto>): List<TimePriceChartValue> {
    return chartValuesDto.map { TimePriceChartValue(dateMapper.map(it.timestamp), it.y) }
  }
}
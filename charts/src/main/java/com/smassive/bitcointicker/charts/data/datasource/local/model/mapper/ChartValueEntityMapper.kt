package com.smassive.bitcointicker.charts.data.datasource.local.model.mapper

import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartValueEntity
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartValueDto
import com.smassive.bitcointicker.charts.domain.model.TimePriceChartValue
import com.smassive.bitcointicker.core.data.model.mapper.DateMapper
import javax.inject.Inject

class ChartValueEntityMapper @Inject constructor(private val dateMapper: DateMapper) {

  fun map(chartValueEntities: List<ChartValueEntity>): List<TimePriceChartValue> {
    return chartValueEntities.map { TimePriceChartValue(dateMapper.map(it.x.toLong()), it.y) }
  }

  fun maptoEntity(chartValuesDto: List<ChartValueDto>, chartName: String): List<ChartValueEntity> {
    return chartValuesDto.map { ChartValueEntity(it.timestamp.toDouble(), it.y, chartName) }
  }
}
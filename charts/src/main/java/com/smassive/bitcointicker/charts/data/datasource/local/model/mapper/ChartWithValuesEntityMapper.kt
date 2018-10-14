package com.smassive.bitcointicker.charts.data.datasource.local.model.mapper

import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartValueEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartWithValuesEntity
import javax.inject.Inject

class ChartWithValuesEntityMapper @Inject constructor() {

  fun map(chartEntity: ChartEntity, chartValueEntities: List<ChartValueEntity>): ChartWithValuesEntity {
    return ChartWithValuesEntity(chartEntity, chartValueEntities)
  }
}
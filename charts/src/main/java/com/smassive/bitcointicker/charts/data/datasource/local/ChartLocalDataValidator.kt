package com.smassive.bitcointicker.charts.data.datasource.local

import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.core.data.model.mapper.PeriodMillisMapper
import com.smassive.bitcointicker.core.data.provider.DateProvider
import com.smassive.bitcointicker.core.infrastructure.annotation.OpenClassOnDebug
import javax.inject.Inject

@OpenClassOnDebug
class ChartLocalDataValidator @Inject constructor(private val dateProvider: DateProvider,
                                                  private val periodMillisMapper: PeriodMillisMapper) {

  fun isValid(chartEntities: List<ChartEntity>): Boolean {
    return chartEntities.none { it.lastUpdate.plus(periodMillisMapper.map(it.period)) <= dateProvider.now() }
  }
}
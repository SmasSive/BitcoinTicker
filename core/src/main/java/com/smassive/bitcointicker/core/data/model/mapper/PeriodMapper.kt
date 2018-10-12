package com.smassive.bitcointicker.core.data.model.mapper

import com.smassive.bitcointicker.core.data.exception.PeriodNotSupportedException
import com.smassive.bitcointicker.core.data.model.PeriodDto
import com.smassive.bitcointicker.core.domain.model.Period
import javax.inject.Inject

class PeriodMapper @Inject constructor() {

  fun map(period: String): Period {
    return when (period) {
      PeriodDto.DAY -> Period.Day
      else -> throw PeriodNotSupportedException(period)
    }
  }
}
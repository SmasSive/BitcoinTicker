package com.smassive.bitcointicker.core.data.model.mapper

import com.smassive.bitcointicker.core.data.exception.PeriodNotSupportedException
import com.smassive.bitcointicker.core.data.model.PeriodDto
import javax.inject.Inject

class PeriodMillisMapper @Inject constructor() {

  fun map(period: String?): Long {
    return when (period) {
      PeriodDto.DAY -> 86400000
      else -> throw PeriodNotSupportedException(period)
    }
  }
}
package com.smassive.bitcointicker.core.data.model.mapper

import com.smassive.bitcointicker.core.data.exception.UnitNotSupportedException
import com.smassive.bitcointicker.core.data.model.UnitDto
import com.smassive.bitcointicker.core.domain.model.Unit
import javax.inject.Inject

class UnitMapper @Inject constructor() {

  fun map(unit: String): Unit {
    return when (unit) {
      UnitDto.USD -> Unit.Usd
      else -> throw UnitNotSupportedException(unit)
    }
  }
}
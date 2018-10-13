package com.smassive.bitcointicker.charts.presentation.model.mapper

import com.smassive.bitcointicker.core.R
import com.smassive.bitcointicker.core.domain.model.Unit
import com.smassive.bitcointicker.core.presentation.provider.StringProvider
import javax.inject.Inject

class UnitViewDataMapper @Inject constructor(private val stringProvider: StringProvider) {

  fun map(unit: Unit): String {
    return when (unit) {
      Unit.Usd -> stringProvider.provideString(R.string.unit_usd)
    }
  }
}
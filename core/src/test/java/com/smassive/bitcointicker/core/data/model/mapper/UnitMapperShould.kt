package com.smassive.bitcointicker.core.data.model.mapper

import com.smassive.bitcointicker.core.data.exception.UnitNotSupportedException
import com.smassive.bitcointicker.core.data.model.UnitDto
import com.smassive.bitcointicker.core.domain.model.Unit
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UnitMapperShould {

  private val unitMapper = UnitMapper()

  @Test
  fun return_unitUsd_for_usdServerValue() {
    assertThat(unitMapper.map(UnitDto.USD)).isEqualToComparingFieldByField(Unit.Usd)
  }

  @Test(expected = UnitNotSupportedException::class)
  fun throw_aException_whenUnitIsNotSupported() {
    assertThat(unitMapper.map(NOT_SUPPORTED_SERVER_VALUE))
  }
}

private const val NOT_SUPPORTED_SERVER_VALUE = "not supported"
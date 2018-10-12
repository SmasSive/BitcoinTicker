package com.smassive.bitcointicker.core.data.model.mapper

import com.smassive.bitcointicker.core.data.exception.PeriodNotSupportedException
import com.smassive.bitcointicker.core.data.model.PeriodDto
import com.smassive.bitcointicker.core.domain.model.Period
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PeriodMapperShould {

  private val periodMapper = PeriodMapper()

  @Test
  fun return_periodDay_for_dayServerValue() {
    assertThat(periodMapper.map(PeriodDto.DAY)).isEqualTo(Period.Day)
  }

  @Test(expected = PeriodNotSupportedException::class)
  fun throw_aException_whenPeriodIsNotSupported() {
    periodMapper.map(NOT_SUPPORTED_SERVER_VALUE)
  }
}

private const val NOT_SUPPORTED_SERVER_VALUE = "not supported"
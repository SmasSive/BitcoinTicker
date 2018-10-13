package com.smassive.bitcointicker.charts.presentation.model.formatter

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DateValueFormatterShould {

  private val dateValueFormatter = DateValueFormatter()

  @Test
  fun return_aShortMonthDateFormat() {
    val formattedDate = dateValueFormatter.getFormattedValue(A_SEPTEMBER_EIGHTEENTH_TWO_THOUSAND_FIFTEEN_TIMESTAMP, null)

    assertThat(formattedDate).isEqualTo(A_SHORT_SEPTEMBER_FIFTEEN_DATE)
  }
}

private const val A_SEPTEMBER_EIGHTEENTH_TWO_THOUSAND_FIFTEEN_TIMESTAMP = 1442534400000F
private const val A_SHORT_SEPTEMBER_FIFTEEN_DATE = "Sep 15"
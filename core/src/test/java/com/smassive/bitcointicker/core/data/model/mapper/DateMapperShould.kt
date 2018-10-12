package com.smassive.bitcointicker.core.data.model.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.text.SimpleDateFormat

class DateMapperShould {

  @Test
  fun return_aDate_forATimestamp() {
    assertThat(DateMapper().map(TIMESTAMP)).isEqualTo(SEPTEMBER_EIGHTEENTH_TWO_THOUSAND_FIFTEEN)
  }
}

private const val DATE_OUTPUT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssX"
private const val TIMESTAMP = 1442534400L
private val SEPTEMBER_EIGHTEENTH_TWO_THOUSAND_FIFTEEN = SimpleDateFormat(DATE_OUTPUT_PATTERN).parse("2015-09-18T00:00:00+00:00")
package com.smassive.bitcointicker.charts.data.datasource.local

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartNameDto
import com.smassive.bitcointicker.core.data.model.mapper.PeriodMillisMapper
import com.smassive.bitcointicker.core.data.provider.DateProvider
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.threeten.bp.Clock
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId

class ChartLocalDataValidatorShould {

  private val dateProvider: DateProvider = mock()
  private val periodMillisMapper = PeriodMillisMapper()

  private val chartLocalDataValidator = ChartLocalDataValidator(dateProvider, periodMillisMapper)

  @Before
  fun setUp() {
    val todayFixedClock = Clock.fixed(Instant.ofEpochMilli(A_TODAY_TIMESTAMP), ZoneId.systemDefault())
    given { dateProvider.now() }.willReturn(DateTimeUtils.toDate(Instant.now(todayFixedClock)).time)
  }

  @Test
  fun return_true_for_aNotExpiredData() {
    assertTrue(chartLocalDataValidator.isValid(A_ONE_ELEMENT_VALID_CHART_ENTITY_LIST))
  }

  @Test
  fun return_false_for_aExpiredData() {
    assertFalse(chartLocalDataValidator.isValid(A_ONE_ELEMENT_INVALID_CHART_ENTITY_LIST))
  }
}

private const val A_TODAY_UNIX_TIMESTAMP = 1442534400.toDouble()
private const val A_TODAY_TIMESTAMP = (A_TODAY_UNIX_TIMESTAMP * 1000).toLong()

private const val A_YESTERDAY_UNIX_TIMESTAMP = 1442448000.toDouble()
private const val A_YESTERDAY_TIMESTAMP = (A_YESTERDAY_UNIX_TIMESTAMP * 1000).toLong()

private const val A_MARKET_PRICE_CHART_NAME = ChartNameDto.MARKET_PRICE
private const val A_UNIT_USD = "USD"
private const val A_PERIOD_DAY = "day"
private const val A_DUMMY_DESCRIPTION = "A dummy description"
private val A_VALID_CHART_ENTITY = ChartEntity(A_MARKET_PRICE_CHART_NAME, A_UNIT_USD, A_PERIOD_DAY, A_DUMMY_DESCRIPTION, A_TODAY_TIMESTAMP)
private val A_ONE_ELEMENT_VALID_CHART_ENTITY_LIST = listOf(A_VALID_CHART_ENTITY)

private val A_INVALID_CHART_ENTITY = ChartEntity(A_MARKET_PRICE_CHART_NAME, A_UNIT_USD, A_PERIOD_DAY, A_DUMMY_DESCRIPTION, A_YESTERDAY_TIMESTAMP)
private val A_ONE_ELEMENT_INVALID_CHART_ENTITY_LIST = listOf(A_INVALID_CHART_ENTITY)
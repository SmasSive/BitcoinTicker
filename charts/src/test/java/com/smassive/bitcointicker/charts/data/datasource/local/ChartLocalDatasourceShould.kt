package com.smassive.bitcointicker.charts.data.datasource.local

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartValueEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartWithValuesEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartWithValuesEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartNameDto
import com.smassive.bitcointicker.core.data.exception.LocalDataExpiredException
import com.smassive.bitcointicker.core.data.exception.NoDataException
import io.reactivex.Flowable
import org.junit.Test

class ChartLocalDatasourceShould {

  private val chartDao: ChartDao = mock()
  private val chartValueDao: ChartValueDao = mock()
  private val chartLocalDataValidator: ChartLocalDataValidator = mock()

  private val chartWithValuesEntityMapper = ChartWithValuesEntityMapper()

  private val chartLocalDatasource = ChartLocalDatasource(chartDao, chartValueDao, chartLocalDataValidator, chartWithValuesEntityMapper)

  @Test
  fun getData_from_localDataSource_when_dataIsValid() {
    given { chartDao.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(A_ONE_ELEMENT_VALID_CHART_ENTITY_LIST))
    given { chartValueDao.getChartValues(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(A_ONE_ELEMENT_LIST_OF_CHART_VALUES))
    given { chartLocalDataValidator.isValid(A_ONE_ELEMENT_VALID_CHART_ENTITY_LIST) }.willReturn(true)

    val testSubscriber = chartLocalDatasource.getChart(A_MARKET_PRICE_CHART_NAME, forceLocal = false).test()

    testSubscriber.assertValue { assertMarketPriceChartEntity(it) }
  }

  @Test
  fun throw_noDataException_when_thereIsNoData() {
    given { chartDao.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(emptyList()))

    val testSubscriber = chartLocalDatasource.getChart(A_MARKET_PRICE_CHART_NAME, forceLocal = false).test()

    testSubscriber.assertError(NoDataException::class.java)
  }

  @Test
  fun throw_noDataException_when_thereIsNoData_evenIfItsForced() {
    given { chartDao.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(emptyList()))

    val testSubscriber = chartLocalDatasource.getChart(A_MARKET_PRICE_CHART_NAME, forceLocal = true).test()

    testSubscriber.assertError(NoDataException::class.java)
  }

  @Test
  fun getData_from_localDataSource_when_dataIsNotValid_but_itsForced() {
    given { chartDao.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(A_ONE_ELEMENT_VALID_CHART_ENTITY_LIST))
    given { chartValueDao.getChartValues(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(A_ONE_ELEMENT_LIST_OF_CHART_VALUES))
    given { chartLocalDataValidator.isValid(A_ONE_ELEMENT_VALID_CHART_ENTITY_LIST) }.willReturn(false)

    val testSubscriber = chartLocalDatasource.getChart(A_MARKET_PRICE_CHART_NAME, forceLocal = true).test()

    testSubscriber.assertValue { assertMarketPriceChartEntity(it) }
  }

  @Test
  fun throw_localDataExpiredException_when_dataIsNotValid() {
    given { chartDao.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(A_ONE_ELEMENT_VALID_CHART_ENTITY_LIST))
    given { chartValueDao.getChartValues(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(A_ONE_ELEMENT_LIST_OF_CHART_VALUES))
    given { chartLocalDataValidator.isValid(A_ONE_ELEMENT_VALID_CHART_ENTITY_LIST) }.willReturn(false)

    val testSubscriber = chartLocalDatasource.getChart(A_MARKET_PRICE_CHART_NAME, forceLocal = false).test()

    testSubscriber.assertError(LocalDataExpiredException::class.java)
  }

  private fun assertMarketPriceChartEntity(chartWithValuesEntity: ChartWithValuesEntity): Boolean {
    return chartWithValuesEntity.chart.description == A_DUMMY_DESCRIPTION &&
        chartWithValuesEntity.chart.period == A_PERIOD_DAY &&
        chartWithValuesEntity.chart.unit == A_UNIT_USD &&
        assertMarketPriceChartValueEntities(chartWithValuesEntity.values)
  }

  private fun assertMarketPriceChartValueEntities(values: List<ChartValueEntity>): Boolean {
    return values.asSequence().map { timePriceChartValue ->
      timePriceChartValue.x == A_DUMMY_X_VALUE &&
          timePriceChartValue.y == A_DUMMY_Y_VALUE
    }.contains(false).not()
  }
}

private const val A_MARKET_PRICE_CHART_NAME = ChartNameDto.MARKET_PRICE
private const val A_UNIT_USD = "USD"
private const val A_PERIOD_DAY = "day"
private const val A_DUMMY_DESCRIPTION = "A dummy description"
private const val A_DUMMY_UNIX_TIMESTAMP = 123L
private val A_VALID_CHART_ENTITY = ChartEntity(A_MARKET_PRICE_CHART_NAME, A_UNIT_USD, A_PERIOD_DAY, A_DUMMY_DESCRIPTION, A_DUMMY_UNIX_TIMESTAMP)
private val A_ONE_ELEMENT_VALID_CHART_ENTITY_LIST = listOf(A_VALID_CHART_ENTITY)

private const val A_DUMMY_Y_VALUE = 123.toDouble()
private const val A_DUMMY_X_VALUE = 123.toDouble()
private val A_DUMMY_CHART_VALUE = ChartValueEntity(A_DUMMY_X_VALUE, A_DUMMY_Y_VALUE, A_MARKET_PRICE_CHART_NAME)
private val A_ONE_ELEMENT_LIST_OF_CHART_VALUES = listOf(A_DUMMY_CHART_VALUE)
package com.smassive.bitcointicker.charts.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.smassive.bitcointicker.charts.data.datasource.local.ChartDao
import com.smassive.bitcointicker.charts.data.datasource.local.ChartValueDao
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartValueEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartValueEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartWithValuesEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.remote.ChartsApiClient
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartMarketPriceDto
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartNameDto
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartValueDto
import com.smassive.bitcointicker.charts.data.datasource.remote.model.mapper.ChartMarketPriceDtoMapper
import com.smassive.bitcointicker.charts.data.datasource.remote.model.mapper.ChartValuesDtoMapper
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.model.TimePriceChartValue
import com.smassive.bitcointicker.core.data.model.mapper.DateMapper
import com.smassive.bitcointicker.core.data.model.mapper.PeriodMapper
import com.smassive.bitcointicker.core.data.model.mapper.UnitMapper
import com.smassive.bitcointicker.core.domain.model.Period
import com.smassive.bitcointicker.core.domain.model.Unit
import io.reactivex.Flowable
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant

class ChartRepositoryShould {

  @get:Rule
  val rule = InstantTaskExecutorRule()

  private val chartsApiClient: ChartsApiClient = mock()
  private val chartDao: ChartDao = mock()
  private val chartValueDao: ChartValueDao = mock()

  private val dateMapper = DateMapper()
  private val chartValuesDtoMapper = ChartValuesDtoMapper(dateMapper)
  private val unitMapper = UnitMapper()
  private val periodMapper = PeriodMapper()
  private val chartMarketPriceDtoMapper = ChartMarketPriceDtoMapper(chartValuesDtoMapper, unitMapper, periodMapper)
  private val chartValueEntityMapper = ChartValueEntityMapper(dateMapper)
  private val chartWithValuesEntityMapper = ChartWithValuesEntityMapper(unitMapper, periodMapper, chartValueEntityMapper)
  private val chartEntityMapper = ChartEntityMapper()

  private val chartRepository = ChartRepository(chartsApiClient, chartDao, chartValueDao, chartMarketPriceDtoMapper,
      chartWithValuesEntityMapper, chartEntityMapper, chartValueEntityMapper)

  @Before
  fun setUp() {
    reset(chartsApiClient, chartDao, chartValueDao)
  }

  @Test
  fun getAMarketPriceChart_from_localDatasource() {
    given { chartDao.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(A_ONE_ELEMENT_CHART_ENTITY_LIST))
    given { chartValueDao.getChartValues(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(A_ONE_ELEMENT_LIST_OF_CHART_VALUES))

    val testSubscriber = chartRepository.getMarketPriceChart().test()

    testSubscriber.assertValue { assertMarketPriceChart(it) }
  }

  @Test
  fun getAMarketPriceChart_from_remoteDatasource() {
    given { chartDao.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(emptyList()))
    given { chartsApiClient.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Maybe.just(A_CHART_MARKET_PRICE_DTO))
    doNothing().`when`(chartDao).insert(A_DUMMY_CHART_ENTITY)
    doNothing().`when`(chartValueDao).insertAll(A_ONE_ELEMENT_LIST_OF_CHART_VALUES)

    val testSubscriber = chartRepository.getMarketPriceChart().test()

    testSubscriber.assertValue { assertMarketPriceChart(it) }
    verify(chartDao).insert(A_DUMMY_CHART_ENTITY)
  }

  @Test
  fun insertDataInLocalDatasource_when_requestingFromRemoteDatasource() {
    given { chartDao.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Flowable.just(emptyList()))
    given { chartsApiClient.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Maybe.just(A_CHART_MARKET_PRICE_DTO))
    doNothing().`when`(chartDao).insert(A_DUMMY_CHART_ENTITY)
    doNothing().`when`(chartValueDao).insertAll(A_ONE_ELEMENT_LIST_OF_CHART_VALUES)

    chartRepository.getMarketPriceChart().test()

    verify(chartDao).insert(A_DUMMY_CHART_ENTITY)
    verify(chartValueDao).insertAll(A_ONE_ELEMENT_LIST_OF_CHART_VALUES)
  }

  private fun assertMarketPriceChart(marketPriceChart: MarketPriceChart): Boolean {
    return marketPriceChart.description == A_DUMMY_DESCRIPTION &&
        marketPriceChart.period == Period.Day &&
        marketPriceChart.unit == Unit.Usd &&
        assertMarketPriceChartValues(marketPriceChart.values)
  }

  private fun assertMarketPriceChartValues(values: List<TimePriceChartValue>): Boolean {
    return values.asSequence().map { timePriceChartValue ->
      timePriceChartValue.date == DateTimeUtils.toDate(Instant.ofEpochMilli(A_SEPTEMBER_EIGHTEENTH_TWO_THOUSAND_FIFTEEN_TIMESTAMP)) &&
          timePriceChartValue.price == A_DUMMY_Y_VALUE
    }.contains(false).not()
  }
}

private const val A_MARKET_PRICE_CHART_NAME = ChartNameDto.MARKET_PRICE
private const val A_UNIT_USD = "USD"
private const val A_PERIOD_DAY = "day"
private const val A_DUMMY_DESCRIPTION = "A dummy description"
private val A_DUMMY_CHART_ENTITY = ChartEntity(A_MARKET_PRICE_CHART_NAME, A_UNIT_USD, A_PERIOD_DAY, A_DUMMY_DESCRIPTION)
private val A_ONE_ELEMENT_CHART_ENTITY_LIST = listOf(A_DUMMY_CHART_ENTITY)

private const val A_SEPTEMBER_EIGHTEENTH_TWO_THOUSAND_FIFTEEN_UNIX_TIMESTAMP = 1442534400.toDouble()
private const val A_SEPTEMBER_EIGHTEENTH_TWO_THOUSAND_FIFTEEN_TIMESTAMP = 1442534400000L
private const val A_DUMMY_Y_VALUE = 123.toDouble()
private val A_DUMMY_CHART_VALUE = ChartValueEntity(A_SEPTEMBER_EIGHTEENTH_TWO_THOUSAND_FIFTEEN_UNIX_TIMESTAMP, A_DUMMY_Y_VALUE,
    A_MARKET_PRICE_CHART_NAME)
private val A_ONE_ELEMENT_LIST_OF_CHART_VALUES = listOf(A_DUMMY_CHART_VALUE)

private const val A_STATUS = "ok"
private val A_CHART_VALUE_DTO = ChartValueDto(A_SEPTEMBER_EIGHTEENTH_TWO_THOUSAND_FIFTEEN_UNIX_TIMESTAMP.toLong(), A_DUMMY_Y_VALUE)
private val A_ONE_ELEMENT_CHART_VALUE_DTO_LIST = listOf(A_CHART_VALUE_DTO)
private val A_CHART_MARKET_PRICE_DTO = ChartMarketPriceDto(A_STATUS, A_MARKET_PRICE_CHART_NAME, A_UNIT_USD, A_PERIOD_DAY,
    A_DUMMY_DESCRIPTION, A_ONE_ELEMENT_CHART_VALUE_DTO_LIST)

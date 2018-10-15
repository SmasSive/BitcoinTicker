package com.smassive.bitcointicker.charts.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.smassive.bitcointicker.charts.data.datasource.local.ChartLocalDatasource
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartValueEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartWithValuesEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartValueEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartWithValuesEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartWithValuesMapper
import com.smassive.bitcointicker.charts.data.datasource.remote.ChartsApiClient
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartMarketPriceDto
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartNameDto
import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartValueDto
import com.smassive.bitcointicker.charts.data.datasource.remote.model.mapper.ChartMarketPriceDtoMapper
import com.smassive.bitcointicker.charts.data.datasource.remote.model.mapper.ChartValuesDtoMapper
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.model.TimePriceChartValue
import com.smassive.bitcointicker.core.data.exception.LocalDataExpiredException
import com.smassive.bitcointicker.core.data.exception.NoDataException
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

class ChartDataRepositoryShould {

  @get:Rule
  val rule = InstantTaskExecutorRule()

  private val chartsApiClient: ChartsApiClient = mock()
  private val chartLocalDataSource: ChartLocalDatasource = mock()

  private val dateMapper = DateMapper()
  private val chartValuesDtoMapper = ChartValuesDtoMapper(dateMapper)
  private val unitMapper = UnitMapper()
  private val periodMapper = PeriodMapper()
  private val chartMarketPriceDtoMapper = ChartMarketPriceDtoMapper(chartValuesDtoMapper, unitMapper, periodMapper)
  private val chartValueEntityMapper = ChartValueEntityMapper(dateMapper)
  private val chartWithValuesEntityMapper = ChartWithValuesEntityMapper()
  private val chartEntityMapper = ChartEntityMapper()
  private val chartWithValuesMapper = ChartWithValuesMapper(unitMapper, periodMapper, chartValueEntityMapper)

  private val chartRepository = ChartDataRepository(chartsApiClient, chartLocalDataSource, chartMarketPriceDtoMapper,
      chartWithValuesEntityMapper, chartEntityMapper, chartValueEntityMapper, chartWithValuesMapper)

  @Before
  fun setUp() {
    reset(chartsApiClient, chartLocalDataSource)
  }

  @Test
  fun getAMarketPriceChart_from_localDatasource() {
    given { chartLocalDataSource.getChart(ChartNameDto.MARKET_PRICE, forceLocal = false) }
        .willReturn(Flowable.just(A_CHART_WITH_VALUES_ENTITY))

    val testSubscriber = chartRepository.getMarketPriceChart().test()

    testSubscriber.assertValue { assertMarketPriceChart(it) }
    verifyZeroInteractions(chartsApiClient)
  }

  @Test
  fun getAMarketPriceChart_from_remoteDatasource_when_thereIsNoLocalData() {
    given { chartLocalDataSource.getChart(ChartNameDto.MARKET_PRICE, forceLocal = false) }.willReturn(Flowable.error(NoDataException()))
    given { chartsApiClient.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Maybe.just(A_CHART_MARKET_PRICE_DTO))
    doNothing().`when`(chartLocalDataSource).saveMarketPriceChart(A_CHART_WITH_VALUES_ENTITY)

    val testSubscriber = chartRepository.getMarketPriceChart().test()

    testSubscriber.assertValue { assertMarketPriceChart(it) }
  }

  @Test
  fun insertDataInLocalDatasource_when_requestingFromRemoteDatasource() {
    given { chartLocalDataSource.getChart(ChartNameDto.MARKET_PRICE, forceLocal = false) }.willReturn(Flowable.error(NoDataException()))
    given { chartsApiClient.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Maybe.just(A_CHART_MARKET_PRICE_DTO))

    chartRepository.getMarketPriceChart().test()

    verify(chartLocalDataSource).saveMarketPriceChart(A_CHART_WITH_VALUES_ENTITY)
  }

  @Test
  fun getAMarketPriceChart_from_localDatasource_when_remoteErrored() {
    given { chartLocalDataSource.getChart(ChartNameDto.MARKET_PRICE, forceLocal = false) }
        .willReturn(Flowable.error(LocalDataExpiredException()))
    given { chartsApiClient.getChart(A_MARKET_PRICE_CHART_NAME) }.willReturn(Maybe.error(Exception()))
    given { chartLocalDataSource.getChart(ChartNameDto.MARKET_PRICE, forceLocal = true) }
        .willReturn(Flowable.just(A_CHART_WITH_VALUES_ENTITY))

    val testSubscriber = chartRepository.getMarketPriceChart().test()

    testSubscriber.assertValue { assertMarketPriceChart(it) }
  }

  private fun assertMarketPriceChart(marketPriceChart: MarketPriceChart): Boolean {
    return marketPriceChart.description == A_DUMMY_DESCRIPTION &&
        marketPriceChart.period == Period.Day &&
        marketPriceChart.unit == Unit.Usd &&
        assertMarketPriceChartValues(marketPriceChart.values)
  }

  private fun assertMarketPriceChartValues(values: List<TimePriceChartValue>): Boolean {
    return values.asSequence().map { timePriceChartValue ->
      timePriceChartValue.date == DateTimeUtils.toDate(Instant.ofEpochMilli(A_TODAY_TIMESTAMP)) &&
          timePriceChartValue.price == A_DUMMY_Y_VALUE
    }.contains(false).not()
  }
}

private const val A_TODAY_UNIX_TIMESTAMP = 1442534400.toDouble()
private const val A_TODAY_TIMESTAMP = (A_TODAY_UNIX_TIMESTAMP * 1000).toLong()

private const val A_MARKET_PRICE_CHART_NAME = ChartNameDto.MARKET_PRICE
private const val A_UNIT_USD = "USD"
private const val A_PERIOD_DAY = "day"
private const val A_DUMMY_DESCRIPTION = "A dummy description"
private val A_CHART_ENTITY = ChartEntity(A_MARKET_PRICE_CHART_NAME, A_UNIT_USD, A_PERIOD_DAY, A_DUMMY_DESCRIPTION, A_TODAY_TIMESTAMP)

private const val A_DUMMY_Y_VALUE = 123.toDouble()
private val A_DUMMY_CHART_VALUE = ChartValueEntity(A_TODAY_UNIX_TIMESTAMP, A_DUMMY_Y_VALUE,
    A_MARKET_PRICE_CHART_NAME)
private val A_ONE_ELEMENT_LIST_OF_CHART_VALUES = listOf(A_DUMMY_CHART_VALUE)

private val A_CHART_WITH_VALUES_ENTITY = ChartWithValuesEntity(A_CHART_ENTITY, A_ONE_ELEMENT_LIST_OF_CHART_VALUES)

private const val A_STATUS = "ok"
private val A_CHART_VALUE_DTO = ChartValueDto(A_TODAY_UNIX_TIMESTAMP.toLong(), A_DUMMY_Y_VALUE)
private val A_ONE_ELEMENT_CHART_VALUE_DTO_LIST = listOf(A_CHART_VALUE_DTO)
private val A_CHART_MARKET_PRICE_DTO = ChartMarketPriceDto(A_STATUS, A_MARKET_PRICE_CHART_NAME, A_UNIT_USD, A_PERIOD_DAY,
    A_DUMMY_DESCRIPTION, A_ONE_ELEMENT_CHART_VALUE_DTO_LIST)

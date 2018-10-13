package com.smassive.bitcointicker.charts.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.usecase.GetMarketPriceChartUseCase
import com.smassive.bitcointicker.charts.presentation.model.MarketPriceChartViewData
import com.smassive.bitcointicker.charts.presentation.model.mapper.ChartLineDataMapper
import com.smassive.bitcointicker.charts.presentation.model.mapper.MarketPriceChartViewDataMapper
import com.smassive.bitcointicker.charts.presentation.model.mapper.UnitViewDataMapper
import com.smassive.bitcointicker.core.R
import com.smassive.bitcointicker.core.domain.model.Period
import com.smassive.bitcointicker.core.domain.model.Unit
import com.smassive.bitcointicker.core.presentation.model.Status
import com.smassive.bitcointicker.core.presentation.provider.DimenProvider
import com.smassive.bitcointicker.core.presentation.provider.StringProvider
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MarketPriceChartViewModelShould {

  @get:Rule
  val rule = InstantTaskExecutorRule()

  private val getMarketPriceChartUseCase: GetMarketPriceChartUseCase = mock()
  private val stringProvider: StringProvider = mock()
  private val dimenProvider: DimenProvider = mock()

  private val chartLineDataMapper = ChartLineDataMapper(dimenProvider)
  private val unitViewDataMapper = UnitViewDataMapper(stringProvider)
  private val marketPriceChartViewDataMapper = MarketPriceChartViewDataMapper(chartLineDataMapper, unitViewDataMapper, stringProvider)

  private val marketPriceChartViewModel by lazy {
    MarketPriceChartViewModel(getMarketPriceChartUseCase, marketPriceChartViewDataMapper, Schedulers.trampoline(), Schedulers.trampoline())
  }

  @Before
  fun setUp() {
    reset(getMarketPriceChartUseCase)
    given { dimenProvider.provideDimen(R.dimen.chart_line_width) }.willReturn(A_ONE_AND_A_HALF_LINE_LENGTH)
    given { stringProvider.provideString(R.string.unit_usd) }.willReturn(A_UNIT_USD)
    given { stringProvider.provideString(R.string.market_price_title, A_UNIT_USD) }.willReturn(A_MARKET_PRICE_USD_TITLE)
  }

  @Test
  fun loadMarketPriceChart_atInit() {
    given { getMarketPriceChartUseCase.getMarketPriceChart() }.willReturn(Flowable.just(A_DUMMY_MARKET_PRICE_CHART))

    marketPriceChartViewModel.getMarketPriceChart().value

    verify(getMarketPriceChartUseCase).getMarketPriceChart()
  }

  @Test
  fun postMarketPriceChart_whenUseCaseSucceeded() {
    given { getMarketPriceChartUseCase.getMarketPriceChart() }.willReturn(Flowable.just(A_DUMMY_MARKET_PRICE_CHART))

    val marketPriceChartResult = marketPriceChartViewModel.getMarketPriceChart().value

    assertThat(marketPriceChartResult?.data).isEqualToComparingFieldByFieldRecursively(A_DUMMY_MARKET_PRICE_CHART_VIEW_DATA)
    assertThat(marketPriceChartResult?.status).isEqualTo(Status.SUCCESS)
  }

  @Test
  fun postError_whenUseCaseErrored() {
    given { getMarketPriceChartUseCase.getMarketPriceChart() }.willReturn(Flowable.error(Exception(A_DUMMY_ERROR_MESSAGE)))

    val marketPriceChartResult = marketPriceChartViewModel.getMarketPriceChart().value

    assertThat(marketPriceChartResult?.status).isEqualTo(Status.ERROR)
    assertThat(marketPriceChartResult?.message).isEqualTo(A_DUMMY_ERROR_MESSAGE)
  }
}

private const val A_ONE_AND_A_HALF_LINE_LENGTH = 1.5F
private const val A_UNIT_USD = "USD"
private const val A_MARKET_PRICE_USD_TITLE = "Market Price ($A_UNIT_USD)"
private const val A_DUMMY_DESCRIPTION = "Dummy description"
private val A_CONFIGURED_DATA_SET = LineDataSet(emptyList(), A_UNIT_USD).apply {
  setDrawCircles(false)
  setDrawValues(false)
  lineWidth = A_ONE_AND_A_HALF_LINE_LENGTH
  color = R.color.colorPrimary
}
private val A_DUMMY_MARKET_PRICE_CHART = MarketPriceChart(Unit.Usd, Period.Day, A_DUMMY_DESCRIPTION, emptyList())
private val A_DUMMY_MARKET_PRICE_CHART_VIEW_DATA =
    MarketPriceChartViewData(A_MARKET_PRICE_USD_TITLE, A_DUMMY_DESCRIPTION, LineData(A_CONFIGURED_DATA_SET))
private const val A_DUMMY_ERROR_MESSAGE = "A_DUMMY_ERROR_MESSAGE"

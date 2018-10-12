package com.smassive.bitcointicker.charts.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.usecase.GetMarketPriceChartUseCase
import com.smassive.bitcointicker.core.domain.model.Period
import com.smassive.bitcointicker.core.domain.model.Unit
import com.smassive.bitcointicker.core.presentation.model.Status
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class MarketPriceChartViewModelShould {

  @get:Rule
  val rule = InstantTaskExecutorRule()

  private val getMarketPriceChartUseCase: GetMarketPriceChartUseCase = mock()

  private val marketPriceChartViewModel by lazy {
    MarketPriceChartViewModel(getMarketPriceChartUseCase, Schedulers.trampoline(), Schedulers.trampoline())
  }

  @Before
  fun setUp() {
    reset(getMarketPriceChartUseCase)
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

    assertThat(marketPriceChartResult?.data).isEqualToComparingFieldByField(A_DUMMY_MARKET_PRICE_CHART)
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

private val A_DUMMY_MARKET_PRICE_CHART = MarketPriceChart(Unit.Usd, Period.Day, "Dummy description", emptyList())
private const val A_DUMMY_ERROR_MESSAGE = "A_DUMMY_ERROR_MESSAGE"
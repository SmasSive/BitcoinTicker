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
  fun postMarketPriceChart_inLiveData() {
    given { getMarketPriceChartUseCase.getMarketPriceChart() }.willReturn(Flowable.just(A_DUMMY_MARKET_PRICE_CHART))

    val marketPriceChart = marketPriceChartViewModel.getMarketPriceChart().value

    assertThat(marketPriceChart).isEqualToComparingFieldByField(A_DUMMY_MARKET_PRICE_CHART)
  }
}

private val A_DUMMY_MARKET_PRICE_CHART = MarketPriceChart(Unit.Usd, Period.Day, "Dummy description", emptyList())
package com.smassive.bitcointicker.charts.presentation.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.rule.BaristaRule
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.model.TimePriceChartValue
import com.smassive.bitcointicker.charts.domain.repository.ChartRepository
import com.smassive.bitcointicker.config.app
import com.smassive.bitcointicker.config.getChartDaggerRule
import com.smassive.bitcointicker.core.R
import com.smassive.bitcointicker.core.data.exception.NoDataException
import com.smassive.bitcointicker.core.domain.model.Period
import com.smassive.bitcointicker.core.domain.model.Unit
import com.smassive.bitcointicker.infrastructure.injector.component.ApplicationComponent
import com.smassive.bitcointicker.presentation.view.MainActivity
import io.reactivex.Flowable
import it.cosenonjaviste.daggermock.DaggerMockRule
import org.junit.Rule
import org.junit.Test
import java.util.Date

class ChartFragmentShould {

  @get:Rule
  val activityRule = BaristaRule.create(MainActivity::class.java)

  @get:Rule
  val clearDatabaseRule = ClearDatabaseRule()

  @get:Rule
  val daggerRule: DaggerMockRule<ApplicationComponent> = getChartDaggerRule()

  @get:Rule
  val rule = InstantTaskExecutorRule()

  private val chartDataRepository: ChartRepository = mock()

  @Test
  fun show_aChart() {
    given { chartDataRepository.getMarketPriceChart() }.willReturn(Flowable.just(A_DUMMY_MARKET_PRICE_CHART))

    activityRule.launchActivity()

    assertDisplayed(A_DUMMY_DESCRIPTION)
  }

  @Test
  fun show_noDataError() {
    given { chartDataRepository.getMarketPriceChart() }.willReturn(Flowable.error(NoDataException()))

    activityRule.launchActivity()

    assertDisplayed(app.getString(R.string.error_generic_no_data))
  }
}

private const val A_DUMMY_DESCRIPTION = "Dummy description"
private val A_TIME_PRICE_CHART_VALUE = TimePriceChartValue(Date(), 123.0)
private val A_TIME_PRICE_CHART_VALUES = listOf(A_TIME_PRICE_CHART_VALUE)
private val A_DUMMY_MARKET_PRICE_CHART = MarketPriceChart(Unit.Usd, Period.Day, A_DUMMY_DESCRIPTION, A_TIME_PRICE_CHART_VALUES)

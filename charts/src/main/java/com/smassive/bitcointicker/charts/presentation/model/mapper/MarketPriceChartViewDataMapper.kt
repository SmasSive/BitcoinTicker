package com.smassive.bitcointicker.charts.presentation.model.mapper

import com.smassive.bitcointicker.charts.R
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.presentation.model.MarketPriceChartViewData
import com.smassive.bitcointicker.core.presentation.provider.StringProvider
import javax.inject.Inject

class MarketPriceChartViewDataMapper @Inject constructor(private val chartLineDataMapper: ChartLineDataMapper,
                                                         private val unitViewDataMapper: UnitViewDataMapper,
                                                         private val stringProvider: StringProvider) {

  fun map(marketPriceChart: MarketPriceChart): MarketPriceChartViewData {
    return MarketPriceChartViewData(
        stringProvider.provideString(R.string.market_price_title, unitViewDataMapper.map(marketPriceChart.unit)),
        marketPriceChart.description, chartLineDataMapper.map(marketPriceChart.values, unitViewDataMapper.map(marketPriceChart.unit)))
  }
}
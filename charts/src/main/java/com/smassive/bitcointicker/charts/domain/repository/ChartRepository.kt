package com.smassive.bitcointicker.charts.domain.repository

import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import io.reactivex.Flowable

interface ChartRepository {

  fun getMarketPriceChart(): Flowable<MarketPriceChart>
}
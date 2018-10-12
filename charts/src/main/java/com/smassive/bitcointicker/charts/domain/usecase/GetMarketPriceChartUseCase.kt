package com.smassive.bitcointicker.charts.domain.usecase

import com.smassive.bitcointicker.charts.data.ChartRepository
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import io.reactivex.Flowable
import javax.inject.Inject

class GetMarketPriceChartUseCase @Inject constructor(private val chartRepository: ChartRepository) {

  fun getMarketPriceChart(): Flowable<MarketPriceChart> = chartRepository.getMarketPriceChart()
}
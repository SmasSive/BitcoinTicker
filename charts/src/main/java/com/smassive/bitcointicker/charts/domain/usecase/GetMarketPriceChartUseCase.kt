package com.smassive.bitcointicker.charts.domain.usecase

import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.repository.ChartRepository
import com.smassive.bitcointicker.core.infrastructure.annotation.OpenClassOnDebug
import io.reactivex.Flowable
import javax.inject.Inject

@OpenClassOnDebug
class GetMarketPriceChartUseCase @Inject constructor(private val chartDataRepository: ChartRepository) {

  fun getMarketPriceChart(): Flowable<MarketPriceChart> = chartDataRepository.getMarketPriceChart()
}
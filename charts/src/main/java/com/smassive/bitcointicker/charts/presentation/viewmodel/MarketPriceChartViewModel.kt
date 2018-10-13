package com.smassive.bitcointicker.charts.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.usecase.GetMarketPriceChartUseCase
import com.smassive.bitcointicker.charts.presentation.model.MarketPriceChartViewData
import com.smassive.bitcointicker.charts.presentation.model.mapper.MarketPriceChartViewDataMapper
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.ObserveOn
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.SubscribeOn
import com.smassive.bitcointicker.core.presentation.model.Resource
import com.smassive.bitcointicker.core.presentation.viewmodel.BaseViewModel
import io.reactivex.Scheduler
import javax.inject.Inject

class MarketPriceChartViewModel @Inject constructor(private val getMarketPriceChartUseCase: GetMarketPriceChartUseCase,
                                                    private val marketPriceChartViewDataMapper: MarketPriceChartViewDataMapper,
                                                    @SubscribeOn subscribeOn: Scheduler,
                                                    @ObserveOn observeOn: Scheduler)
  : BaseViewModel(subscribeOn, observeOn) {

  private val marketPriceChart = MutableLiveData<Resource<MarketPriceChartViewData>>()

  init {
    loadMarketPriceChart()
  }

  fun getMarketPriceChart(): MutableLiveData<Resource<MarketPriceChartViewData>> = marketPriceChart

  private fun loadMarketPriceChart() {
    getMarketPriceChartUseCase.getMarketPriceChart().execute(onMarketPriceChartRetrieved(), onMarketPriceChartErrored())
  }

  private fun onMarketPriceChartRetrieved(): (MarketPriceChart) -> Unit = {
    marketPriceChart.postValue(Resource.success(marketPriceChartViewDataMapper.map(it)))
  }

  private fun onMarketPriceChartErrored(): (Throwable) -> Unit = {
    marketPriceChart.postValue(Resource.error(it.message ?: "Unknown error", null))
  }
}
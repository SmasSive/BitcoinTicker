package com.smassive.bitcointicker.charts.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.usecase.GetMarketPriceChartUseCase
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.ObserveOn
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.SubscribeOn
import com.smassive.bitcointicker.core.presentation.model.Resource
import com.smassive.bitcointicker.core.presentation.viewmodel.BaseViewModel
import io.reactivex.Scheduler
import javax.inject.Inject

class MarketPriceChartViewModel @Inject constructor(private val getMarketPriceChartUseCase: GetMarketPriceChartUseCase,
                                                    @SubscribeOn subscribeOn: Scheduler,
                                                    @ObserveOn observeOn: Scheduler)
  : BaseViewModel(subscribeOn, observeOn) {

  private val marketPriceChart = MutableLiveData<Resource<MarketPriceChart>>()

  init {
    loadMarketPriceChart()
  }

  fun getMarketPriceChart(): MutableLiveData<Resource<MarketPriceChart>> = marketPriceChart

  private fun loadMarketPriceChart() {
    getMarketPriceChartUseCase.getMarketPriceChart().execute(onMarketPriceChartRetrieved(), onMarketPriceChartErrored())
  }

  private fun onMarketPriceChartRetrieved(): (MarketPriceChart) -> Unit = {
    marketPriceChart.postValue(Resource.success(it))
  }

  private fun onMarketPriceChartErrored(): (Throwable) -> Unit = {
    marketPriceChart.postValue(Resource.error(it.message ?: "Unknown error", null))
  }
}
package com.smassive.bitcointicker.charts.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.usecase.GetMarketPriceChartUseCase
import com.smassive.bitcointicker.core.presentation.viewmodel.BaseViewModel
import javax.inject.Inject

class MarketPriceChartViewModel @Inject constructor(private val getMarketPriceChartUseCase: GetMarketPriceChartUseCase) : BaseViewModel() {

  private val marketPriceChart = MutableLiveData<MarketPriceChart>()

  init {
    loadMarketPriceChart()
  }

  fun getMarketPriceChart(): MutableLiveData<MarketPriceChart> = marketPriceChart

  private fun loadMarketPriceChart() {
    getMarketPriceChartUseCase.getMarketPriceChart().execute(onMarketPriceRetrieved())
  }

  private fun onMarketPriceRetrieved(): (MarketPriceChart) -> Unit = { marketPriceChart.postValue(it) }
}
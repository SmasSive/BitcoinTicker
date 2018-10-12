package com.smassive.bitcointicker.charts.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.usecase.GetMarketPriceChartUseCase
import com.smassive.bitcointicker.core.presentation.viewmodel.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MarketPriceChartViewModel @Inject constructor(private val getMarketPriceChartUseCase: GetMarketPriceChartUseCase,
                                                    subscribeOn: Scheduler = Schedulers.io(),
                                                    observeOn: Scheduler = AndroidSchedulers.mainThread())
  : BaseViewModel(subscribeOn, observeOn) {

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
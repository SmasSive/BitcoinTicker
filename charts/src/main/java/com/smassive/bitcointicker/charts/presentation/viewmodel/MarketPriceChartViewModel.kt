package com.smassive.bitcointicker.charts.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.smassive.bitcointicker.charts.domain.model.MarketPriceChart
import com.smassive.bitcointicker.charts.domain.usecase.GetMarketPriceChartUseCase
import com.smassive.bitcointicker.core.presentation.model.Resource
import com.smassive.bitcointicker.core.presentation.viewmodel.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class MarketPriceChartViewModel @Inject constructor(private val getMarketPriceChartUseCase: GetMarketPriceChartUseCase,
                                                    @Named("subscribeOn") subscribeOn: Scheduler = Schedulers.io(),
                                                    @Named("observeOn") observeOn: Scheduler = AndroidSchedulers.mainThread())
  : BaseViewModel(subscribeOn, observeOn) {

  private val marketPriceChart = MutableLiveData<Resource<MarketPriceChart>>()

  init {
    loadMarketPriceChart()
  }

  fun getMarketPriceChart(): MutableLiveData<Resource<MarketPriceChart>> = marketPriceChart

  private fun loadMarketPriceChart() {
    getMarketPriceChartUseCase.getMarketPriceChart().execute(onMarketPriceRetrieved(), onMarketPriceErrored())
  }

  private fun onMarketPriceRetrieved(): (MarketPriceChart) -> Unit = {
    marketPriceChart.postValue(Resource.success(it))
  }

  private fun onMarketPriceErrored(): (Throwable) -> Unit = {
    marketPriceChart.postValue(Resource.error(it.message ?: "Unknown error", null))
  }
}
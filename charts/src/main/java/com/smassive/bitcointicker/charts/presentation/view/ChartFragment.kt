package com.smassive.bitcointicker.charts.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.smassive.bitcointicker.charts.R
import com.smassive.bitcointicker.charts.infrastructure.injector.component.ChartComponent
import com.smassive.bitcointicker.charts.presentation.viewmodel.MarketPriceChartViewModel
import com.smassive.bitcointicker.core.presentation.model.Status
import com.smassive.bitcointicker.core.presentation.view.BaseActivity
import com.smassive.bitcointicker.core.presentation.view.BaseFragment
import com.smassive.bitcointicker.core.util.TAG
import com.smassive.bitcointicker.core.util.observeNonNull
import javax.inject.Inject

class ChartFragment : BaseFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val marketPriceChartViewModel = ViewModelProviders.of(this, viewModelFactory)[MarketPriceChartViewModel::class.java]
    marketPriceChartViewModel.getMarketPriceChart().observeNonNull(this) { result ->
      when (result.status) {
        Status.SUCCESS -> Log.d(TAG, "Chart description: ${result.data?.description}")
        Status.ERROR -> Log.e(TAG, "ERROR loading chart data")
        Status.LOADING -> Log.d(TAG, "LOADING chart data")
      }
    }
  }

  override fun getLayoutId(): Int {
    return R.layout.fragment_chart
  }

  override fun onInject() {
    ((activity as BaseActivity<*>).component as ChartComponent.ChartComponentCreator).createChartComponent().inject(this)
  }
}
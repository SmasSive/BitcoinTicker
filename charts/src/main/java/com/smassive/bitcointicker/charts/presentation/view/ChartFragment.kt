package com.smassive.bitcointicker.charts.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.XAxis
import com.smassive.bitcointicker.charts.R
import com.smassive.bitcointicker.charts.infrastructure.injector.component.ChartComponent
import com.smassive.bitcointicker.charts.presentation.model.MarketPriceChartViewData
import com.smassive.bitcointicker.charts.presentation.model.formatter.DateValueFormatter
import com.smassive.bitcointicker.charts.presentation.viewmodel.MarketPriceChartViewModel
import com.smassive.bitcointicker.core.presentation.model.Status
import com.smassive.bitcointicker.core.presentation.view.BaseActivity
import com.smassive.bitcointicker.core.presentation.view.BaseFragment
import com.smassive.bitcointicker.core.util.observeNonNull
import kotlinx.android.synthetic.main.fragment_chart.chart
import kotlinx.android.synthetic.main.fragment_chart.chartLayout
import kotlinx.android.synthetic.main.fragment_chart.chartLoading
import kotlinx.android.synthetic.main.fragment_chart.chartSubTitle
import kotlinx.android.synthetic.main.fragment_chart.chartTitle
import kotlinx.android.synthetic.main.fragment_chart.errorLayout
import javax.inject.Inject

class ChartFragment : BaseFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @Inject
  lateinit var dateValueFormatter: DateValueFormatter

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val marketPriceChartViewModel = ViewModelProviders.of(this, viewModelFactory)[MarketPriceChartViewModel::class.java]
    marketPriceChartViewModel.getMarketPriceChart().observeNonNull(this) { result ->
      when (result.status) {
        Status.SUCCESS -> result.data?.let { addChartData(it) } ?: showError()
        Status.ERROR -> result.message?.let { showError(result.message!!) } ?: showError()
        Status.LOADING -> showLoading()
      }
    }
  }

  private fun addChartData(marketPriceChart: MarketPriceChartViewData) {
    with(marketPriceChart) {
      chartTitle.text = title
      chartSubTitle.text = description
      chart.data = data
    }
    configureChart()
    showChart()
  }

  private fun configureChart() {
    with(chart) {
      description.isEnabled = false
      axisRight.isEnabled = false
      setPinchZoom(false)
      setScaleEnabled(false)
      isDoubleTapToZoomEnabled = false
      isHighlightPerDragEnabled = false
      isHighlightPerTapEnabled = false
      xAxis.valueFormatter = dateValueFormatter
      xAxis.position = XAxis.XAxisPosition.BOTTOM
    }
  }

  private fun showError(message: String = getString(com.smassive.bitcointicker.core.R.string.error_generic_no_data)) {
    chartLoading.visibility = View.GONE
    chartLayout.visibility = View.GONE
    errorLayout.visibility = View.VISIBLE
    errorLayout.findViewById<TextView>(R.id.errorMessage).text = message
  }

  private fun showLoading() {
    chartLoading.visibility = View.VISIBLE
    chartLayout.visibility = View.GONE
    errorLayout.visibility = View.GONE
  }

  private fun showChart() {
    chartLayout.visibility = View.VISIBLE
    chartLoading.visibility = View.GONE
    errorLayout.visibility = View.GONE
    chart.invalidate()
  }

  override fun getLayoutId(): Int {
    return R.layout.fragment_chart
  }

  override fun onInject() {
    ((activity as BaseActivity<*>).component as ChartComponent.ChartComponentCreator).createChartComponent().inject(this)
  }
}
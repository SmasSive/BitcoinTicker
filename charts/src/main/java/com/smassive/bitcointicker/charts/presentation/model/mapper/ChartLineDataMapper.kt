package com.smassive.bitcointicker.charts.presentation.model.mapper

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.smassive.bitcointicker.charts.domain.model.TimePriceChartValue
import com.smassive.bitcointicker.core.R
import com.smassive.bitcointicker.core.presentation.provider.DimenProvider
import javax.inject.Inject

class ChartLineDataMapper @Inject constructor(private val dimenProvider: DimenProvider) {

  fun map(timePriceChartValues: List<TimePriceChartValue>, label: String = ""): LineData {
    return LineData(timePriceChartValues.map {
      Entry(it.date.time.toFloat(), it.price.toFloat())
    }.let { entries ->
      LineDataSet(entries, label).apply {
        setDrawCircles(false)
        setDrawValues(false)
        lineWidth = dimenProvider.provideDimen(R.dimen.chart_line_width)
        color = R.color.colorAccent
      }
    })
  }
}
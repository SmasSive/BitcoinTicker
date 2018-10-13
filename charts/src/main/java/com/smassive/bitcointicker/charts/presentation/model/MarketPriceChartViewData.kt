package com.smassive.bitcointicker.charts.presentation.model

import com.github.mikephil.charting.data.LineData

data class MarketPriceChartViewData(val title: String,
                                    val description: String,
                                    val data: LineData)
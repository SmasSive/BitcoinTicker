package com.smassive.bitcointicker.charts.domain.model

import com.smassive.bitcointicker.core.domain.model.Unit
import com.smassive.bitcointicker.core.domain.model.Period

data class MarketPriceChart(val unit: Unit,
                            val period: Period,
                            val description: String,
                            val values: List<TimePriceChartValue>)
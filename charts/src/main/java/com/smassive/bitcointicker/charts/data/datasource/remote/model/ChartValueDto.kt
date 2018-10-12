package com.smassive.bitcointicker.charts.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class ChartValueDto(@SerializedName("x") val timestamp: Long, @SerializedName("y") val y: Double)
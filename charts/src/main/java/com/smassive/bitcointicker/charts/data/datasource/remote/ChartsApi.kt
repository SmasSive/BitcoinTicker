package com.smassive.bitcointicker.charts.data.datasource.remote

import com.smassive.bitcointicker.charts.data.datasource.remote.model.ChartMarketPriceDto
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

interface ChartsApi {

  @GET("charts/{chartName}")
  fun getChart(@Path("chartName") chartName: String): Maybe<ChartMarketPriceDto>
}
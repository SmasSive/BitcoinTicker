package com.smassive.bitcointicker.charts.infrastructure.injector.module

import com.smassive.bitcointicker.charts.data.datasource.remote.ChartsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ChartNetworkModule {

  @Provides
  @Singleton
  fun provideChartsApi(retrofit: Retrofit): ChartsApi = retrofit.create(ChartsApi::class.java)
}
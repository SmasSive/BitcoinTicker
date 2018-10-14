package com.smassive.bitcointicker.charts.infrastructure.injector.module

import android.content.Context
import androidx.room.Room
import com.smassive.bitcointicker.charts.data.ChartRepository
import com.smassive.bitcointicker.charts.data.datasource.local.ChartDao
import com.smassive.bitcointicker.charts.data.datasource.local.ChartLocalDatasource
import com.smassive.bitcointicker.charts.data.datasource.local.ChartValueDao
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartValueEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartWithValuesEntityMapper
import com.smassive.bitcointicker.charts.data.datasource.local.model.mapper.ChartWithValuesMapper
import com.smassive.bitcointicker.charts.data.datasource.remote.ChartsApiClient
import com.smassive.bitcointicker.charts.data.datasource.remote.model.mapper.ChartMarketPriceDtoMapper
import com.smassive.bitcointicker.charts.infrastructure.database.ChartsDatabase
import com.smassive.bitcointicker.charts.infrastructure.database.DB_NAME
import com.smassive.bitcointicker.core.infrastructure.annotation.OpenClassOnDebug
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@OpenClassOnDebug
class ChartDataModule {

  @Provides
  @Singleton
  fun provideChartRepository(chartsApiClient: ChartsApiClient,
                             chartLocalDatasource: ChartLocalDatasource,
                             chartMarketPriceDtoMapper: ChartMarketPriceDtoMapper,
                             chartWithValuesEntityMapper: ChartWithValuesEntityMapper,
                             chartEntityMapper: ChartEntityMapper,
                             chartValueEntityMapper: ChartValueEntityMapper,
                             chartWithValuesMapper: ChartWithValuesMapper): ChartRepository {
    return ChartRepository(chartsApiClient, chartLocalDatasource, chartMarketPriceDtoMapper, chartWithValuesEntityMapper, chartEntityMapper,
        chartValueEntityMapper, chartWithValuesMapper)
  }

  @Provides
  @Singleton
  fun provideDatabase(@ForApplication context: Context): ChartsDatabase {
    return Room.databaseBuilder(context, ChartsDatabase::class.java, DB_NAME).fallbackToDestructiveMigration().build()
  }

  @Provides
  @Singleton
  fun provideChartDao(chartsDatabase: ChartsDatabase): ChartDao {
    return chartsDatabase.chartDao()
  }

  @Provides
  @Singleton
  fun provideChartValueDao(chartsDatabase: ChartsDatabase): ChartValueDao {
    return chartsDatabase.chartValueDao()
  }
}
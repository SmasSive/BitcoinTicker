package com.smassive.bitcointicker.charts.infrastructure.injector.module

import android.content.Context
import androidx.room.Room
import com.smassive.bitcointicker.charts.data.datasource.local.ChartDao
import com.smassive.bitcointicker.charts.data.datasource.local.ChartValueDao
import com.smassive.bitcointicker.charts.infrastructure.database.ChartsDatabase
import com.smassive.bitcointicker.charts.infrastructure.database.DB_NAME
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ChartDataModule {

  @Provides
  @Singleton
  fun provideDatabase(@ForApplication context: Context): ChartsDatabase {
    return Room.databaseBuilder(context, ChartsDatabase::class.java, DB_NAME).build()
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
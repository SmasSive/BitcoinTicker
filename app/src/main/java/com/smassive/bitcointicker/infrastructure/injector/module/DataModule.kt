package com.smassive.bitcointicker.infrastructure.injector.module

import android.content.Context
import androidx.room.Room
import com.smassive.bitcointicker.charts.data.datasource.local.ChartDao
import com.smassive.bitcointicker.charts.data.datasource.local.ChartValueDao
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.ForApplication
import com.smassive.bitcointicker.infrastructure.database.BitcoinTickerDatabase
import com.smassive.bitcointicker.infrastructure.database.DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

  @Provides
  @Singleton
  fun provideDatabase(@ForApplication context: Context): BitcoinTickerDatabase {
    return Room.databaseBuilder(context, BitcoinTickerDatabase::class.java, DB_NAME).build()
  }

  @Provides
  @Singleton
  fun provideChartDao(bitcoinTickerDatabase: BitcoinTickerDatabase): ChartDao {
    return bitcoinTickerDatabase.chartDao()
  }

  @Provides
  @Singleton
  fun provideChartValueDao(bitcoinTickerDatabase: BitcoinTickerDatabase): ChartValueDao {
    return bitcoinTickerDatabase.chartValueDao()
  }
}
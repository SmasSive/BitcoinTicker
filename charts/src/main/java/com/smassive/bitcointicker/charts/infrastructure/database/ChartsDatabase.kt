package com.smassive.bitcointicker.charts.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smassive.bitcointicker.charts.data.datasource.local.ChartDao
import com.smassive.bitcointicker.charts.data.datasource.local.ChartValueDao
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartValueEntity

const val DB_NAME = "charts.db"

@Database(entities = [ChartEntity::class, ChartValueEntity::class], version = 1)
abstract class ChartsDatabase : RoomDatabase() {
  abstract fun chartDao(): ChartDao
  abstract fun chartValueDao(): ChartValueDao
}
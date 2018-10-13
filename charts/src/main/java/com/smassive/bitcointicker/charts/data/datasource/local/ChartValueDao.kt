package com.smassive.bitcointicker.charts.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartValueEntity
import io.reactivex.Flowable

@Dao
interface ChartValueDao {

  @Query("SELECT * FROM chart_value WHERE chart = :chartName")
  fun getChartValues(chartName: String): Flowable<List<ChartValueEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(chartValueEntities: List<ChartValueEntity>)
}
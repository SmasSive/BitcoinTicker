package com.smassive.bitcointicker.charts.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import io.reactivex.Flowable

@Dao
interface ChartDao {

  @Query("SELECT * FROM chart WHERE name = :chartName")
  fun getChart(chartName: String): Flowable<List<ChartEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(chartEntity: ChartEntity)
}
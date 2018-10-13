package com.smassive.bitcointicker.charts.data.datasource.local.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "chart_value",
    primaryKeys = ["x", "y"],
    foreignKeys = [ForeignKey(entity = ChartEntity::class, parentColumns = ["name"], childColumns = ["chart"])])
data class ChartValueEntity(val x: Double, val y: Double, val chart: String)
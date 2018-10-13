package com.smassive.bitcointicker.charts.data.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chart")
data class ChartEntity(@PrimaryKey val name: String,
                       val unit: String,
                       val period: String,
                       val description: String)
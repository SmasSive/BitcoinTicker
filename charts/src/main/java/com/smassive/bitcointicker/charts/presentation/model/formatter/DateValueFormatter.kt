package com.smassive.bitcointicker.charts.presentation.model.formatter

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

private const val SHORT_MONTH_FORMAT_PATTERN = "MMM yy"

class DateValueFormatter : IAxisValueFormatter {

  override fun getFormattedValue(value: Float, axis: AxisBase?): String {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(value.toLong()), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(SHORT_MONTH_FORMAT_PATTERN))
  }
}
package com.smassive.bitcointicker.core.data.model.mapper

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import java.util.Date
import javax.inject.Inject

class DateMapper @Inject constructor() {

  fun map(unixTimestamp: Long): Date {
    return unixTimestamp.toMillis().let { timestampInMillis -> DateTimeUtils.toDate(Instant.ofEpochMilli(timestampInMillis)) }
  }
}

private fun Long.toMillis() = 1000 * this
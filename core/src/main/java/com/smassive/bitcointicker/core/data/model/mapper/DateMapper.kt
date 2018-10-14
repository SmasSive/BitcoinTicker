package com.smassive.bitcointicker.core.data.model.mapper

import com.smassive.bitcointicker.core.util.toMillis
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import java.util.Date
import javax.inject.Inject

class DateMapper @Inject constructor() {

  fun map(unixTimestamp: Long): Date {
    return unixTimestamp.toMillis().let { timestampInMillis -> DateTimeUtils.toDate(Instant.ofEpochMilli(timestampInMillis)) }
  }
}
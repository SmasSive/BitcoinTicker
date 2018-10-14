package com.smassive.bitcointicker.core.data.provider

import com.smassive.bitcointicker.core.infrastructure.annotation.OpenClassOnDebug
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import javax.inject.Inject

@OpenClassOnDebug
class DateProvider @Inject constructor() {

  fun now(): Long {
    return DateTimeUtils.toDate(Instant.now()).time
  }
}
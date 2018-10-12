package com.smassive.bitcointicker.core.data.exception

class PeriodNotSupportedException(period: String) : RuntimeException("Period $period not supported")
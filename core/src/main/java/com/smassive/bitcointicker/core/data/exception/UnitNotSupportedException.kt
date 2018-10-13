package com.smassive.bitcointicker.core.data.exception

class UnitNotSupportedException(unit: String?) : RuntimeException("Unit $unit not supported")
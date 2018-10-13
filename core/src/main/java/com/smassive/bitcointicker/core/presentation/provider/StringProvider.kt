package com.smassive.bitcointicker.core.presentation.provider

import android.content.Context
import com.smassive.bitcointicker.core.infrastructure.annotation.OpenClassOnDebug
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.ForApplication
import javax.inject.Inject

@OpenClassOnDebug
class StringProvider @Inject constructor(@ForApplication private val context: Context) {

  fun provideString(stringId: Int, vararg formatArgs: String = emptyArray()): String {
    return context.getString(stringId).format(*formatArgs)
  }
}
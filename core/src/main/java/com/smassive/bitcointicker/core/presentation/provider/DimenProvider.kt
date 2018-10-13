package com.smassive.bitcointicker.core.presentation.provider

import android.content.Context
import androidx.annotation.DimenRes
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.ForApplication
import javax.inject.Inject

class DimenProvider @Inject constructor(@ForApplication private val context: Context) {

  fun provideDimen(@DimenRes dimenId: Int): Float {
    return context.resources.getDimension(dimenId)
  }
}
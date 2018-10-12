package com.smassive.bitcointicker.core.presentation.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelUtil @Inject constructor() {
  fun <T : ViewModel> createFor(@NonNull viewModel: T): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {

      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel.javaClass)) {
          return viewModel as T
        }
        throw IllegalArgumentException("unexpected viewModel class $modelClass")
      }
    }
  }
}
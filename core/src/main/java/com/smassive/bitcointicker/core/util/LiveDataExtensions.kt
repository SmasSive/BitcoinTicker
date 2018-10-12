package com.smassive.bitcointicker.core.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

fun <T, R> LiveData<T>.map(transformation: (T?) -> R): LiveData<R> {
  return Transformations.map(this, transformation)
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, f: (T) -> Unit) {
  this.observe(owner, Observer<T> { t -> t?.let(f) })
}

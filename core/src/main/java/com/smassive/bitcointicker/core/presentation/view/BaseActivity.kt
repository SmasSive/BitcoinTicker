package com.smassive.bitcointicker.core.presentation.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<C> : AppCompatActivity() {

  abstract val component: C

  override fun onCreate(savedInstanceState: Bundle?) {
    onInject(component)
    super.onCreate(savedInstanceState)
    setContentView(getLayoutId())
  }

  @LayoutRes
  protected abstract fun getLayoutId(): Int

  protected abstract fun onInject(component: C)
}
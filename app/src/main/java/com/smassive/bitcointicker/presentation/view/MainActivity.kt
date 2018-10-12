package com.smassive.bitcointicker.presentation.view

import androidx.navigation.findNavController
import com.smassive.bitcointicker.R
import com.smassive.bitcointicker.core.infrastructure.injector.module.ActivityModule
import com.smassive.bitcointicker.core.presentation.view.BaseActivity
import com.smassive.bitcointicker.infrastructure.asApp
import com.smassive.bitcointicker.infrastructure.injector.component.MainComponent

class MainActivity : BaseActivity<MainComponent>() {

  override val component by lazy { asApp().component.createMainComponent(ActivityModule(this)) }

  override fun onSupportNavigateUp(): Boolean {
    return findNavController(R.id.fragmentContainer).navigateUp()
  }

  override fun getLayoutId(): Int = R.layout.activity_main

  override fun onInject(component: MainComponent) {
    component.inject(this)
  }
}

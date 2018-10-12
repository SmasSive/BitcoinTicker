package com.smassive.bitcointicker.infrastructure.injector.component

import com.smassive.bitcointicker.charts.infrastructure.injector.component.ChartComponent
import com.smassive.bitcointicker.core.infrastructure.injector.module.ActivityModule
import com.smassive.bitcointicker.core.infrastructure.injector.scope.ActivityScope
import com.smassive.bitcointicker.presentation.view.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface MainComponent : ChartComponent.ChartComponentCreator {

  fun inject(mainActivity: MainActivity)
}
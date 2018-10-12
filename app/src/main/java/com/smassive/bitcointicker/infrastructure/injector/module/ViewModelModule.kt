package com.smassive.bitcointicker.infrastructure.injector.module

import com.smassive.bitcointicker.charts.infrastructure.injector.module.ChartViewModelModule
import dagger.Module

@Module(includes = [ChartViewModelModule::class])
class ViewModelModule
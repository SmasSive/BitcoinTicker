package com.smassive.bitcointicker.infrastructure.injector.module

import com.smassive.bitcointicker.charts.infrastructure.injector.module.ChartDataModule
import dagger.Module

@Module(includes = [ChartDataModule::class])
class DataModule
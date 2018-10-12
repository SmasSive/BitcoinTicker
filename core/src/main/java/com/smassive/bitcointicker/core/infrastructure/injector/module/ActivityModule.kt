package com.smassive.bitcointicker.core.infrastructure.injector.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.smassive.bitcointicker.core.infrastructure.injector.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
open class ActivityModule(protected val activity: AppCompatActivity) {

  @Provides
  @ActivityScope
  fun provideContext(): Context = activity
}
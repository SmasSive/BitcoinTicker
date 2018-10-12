package com.smassive.bitcointicker.infrastructure.injector.module

import android.content.Context
import com.smassive.bitcointicker.BuildConfig
import com.smassive.bitcointicker.charts.infrastructure.injector.module.ChartNetworkModule
import com.smassive.bitcointicker.core.infrastructure.injector.qualifier.ForApplication
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ChartNetworkModule::class])
class NetworkModule {

  @Provides
  @Singleton
  fun provideCache(@ForApplication context: Context) = Cache(context.cacheDir, 10 * 1024 * 1024.toLong())

  @Provides
  @Singleton
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
      level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
      } else {
        HttpLoggingInterceptor.Level.NONE
      }
    }
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(cache: Cache, interceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .cache(cache)
        .addInterceptor(interceptor)
        .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
  }
}

private const val API_BASE_URL = "https://api.blockchain.info/"
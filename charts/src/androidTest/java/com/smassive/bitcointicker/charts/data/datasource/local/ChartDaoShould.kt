package com.smassive.bitcointicker.charts.data.datasource.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.infrastructure.database.ChartsDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChartDaoShould {

  @get:Rule
  val rule = InstantTaskExecutorRule()

  private lateinit var chartsDatabase: ChartsDatabase

  @Before
  fun setUp() {
    chartsDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), ChartsDatabase::class.java).allowMainThreadQueries()
        .build()
  }

  @Test
  fun insert_and_replace_theSameChartEntity_when_insertedTwice() {
    chartsDatabase.chartDao().insert(A_DUMMY_CHART_ENTITY)
    chartsDatabase.chartDao().insert(A_DUMMY_CHART_ENTITY)

    val testSubscriber = chartsDatabase.chartDao().getChart(A_DUMMY_NAME).test()

    testSubscriber.assertValue { charts -> charts.size == 1 }
  }

  @Test
  fun insert_and_get_aChartEntity() {
    chartsDatabase.chartDao().insert(A_DUMMY_CHART_ENTITY)

    val testSubscriber = chartsDatabase.chartDao().getChart(A_DUMMY_NAME).test()

    testSubscriber.assertValue { charts -> charts.size == 1 }
    testSubscriber.assertValue { charts ->
      charts.first().name == A_DUMMY_NAME &&
          charts.first().unit == A_UNIT_USD &&
          charts.first().period == A_PERIOD_DAY &&
          charts.first().description == A_DUMMY_DESCRIPTION
    }
  }

  @Test
  fun insert_and_get_anEmptyList_for_aNonExistentChartEntity() {
    chartsDatabase.chartDao().insert(A_DUMMY_CHART_ENTITY)

    val testSubscriber = chartsDatabase.chartDao().getChart(A_NON_EXISTENT_NAME).test()

    testSubscriber.assertValue { charts -> charts.isEmpty() }
  }

  @After
  fun tearDown() {
    chartsDatabase.close()
  }
}

private const val A_NON_EXISTENT_NAME = "non_existent_name"
private const val A_DUMMY_NAME = "a_dummy_name"
private const val A_UNIT_USD = "USD"
private const val A_PERIOD_DAY = "day"
private const val A_DUMMY_DESCRIPTION = "A dummy description"
private val A_DUMMY_CHART_ENTITY = ChartEntity(A_DUMMY_NAME, A_UNIT_USD, A_PERIOD_DAY, A_DUMMY_DESCRIPTION)
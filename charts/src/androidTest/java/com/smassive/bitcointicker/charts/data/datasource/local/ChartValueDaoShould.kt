package com.smassive.bitcointicker.charts.data.datasource.local

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartEntity
import com.smassive.bitcointicker.charts.data.datasource.local.model.ChartValueEntity
import com.smassive.bitcointicker.charts.infrastructure.database.ChartsDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChartValueDaoShould {

  @get:Rule
  val rule = InstantTaskExecutorRule()

  private lateinit var chartsDatabase: ChartsDatabase

  @Before
  fun setUp() {
    chartsDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), ChartsDatabase::class.java).allowMainThreadQueries()
        .build()
  }

  @Test
  fun insert_and_replace_theSameChartValueEntity_when_insertedTwice() {
    chartsDatabase.chartDao().insert(A_DUMMY_CHART_ENTITY)

    chartsDatabase.chartValueDao().insertAll(A_ONE_ELEMENT_LIST_OF_CHART_VALUES)
    chartsDatabase.chartValueDao().insertAll(A_ONE_ELEMENT_LIST_OF_CHART_VALUES)

    val testSubscriber = chartsDatabase.chartValueDao().getChartValues(A_DUMMY_CHART_NAME).test()

    testSubscriber.assertValue { chartValues -> chartValues.size == 1 }
  }

  @Test(expected = SQLiteConstraintException::class)
  fun check_foreignKey_for_aNonExistentParent() {
    chartsDatabase.chartValueDao().insertAll(A_ONE_ELEMENT_LIST_OF_CHART_VALUES)
    chartsDatabase.chartValueDao().insertAll(A_ONE_ELEMENT_LIST_OF_CHART_VALUES)

    chartsDatabase.chartValueDao().getChartValues(A_DUMMY_CHART_NAME).test()
  }

  @Test
  fun insert_and_get_aChartValueEntity() {
    chartsDatabase.chartDao().insert(A_DUMMY_CHART_ENTITY)

    chartsDatabase.chartValueDao().insertAll(A_ONE_ELEMENT_LIST_OF_CHART_VALUES)

    val testSubscriber = chartsDatabase.chartValueDao().getChartValues(A_DUMMY_CHART_NAME).test()

    testSubscriber.assertValue { chartValues -> chartValues.size == 1 }
    testSubscriber.assertValue { chartValues ->
      chartValues.first().x == A_DUMMY_X_VALUE &&
          chartValues.first().y == A_DUMMY_Y_VALUE &&
          chartValues.first().chart == A_DUMMY_CHART_NAME
    }
  }

  @Test
  fun insert_and_get_aListOfTwoChartValueEntities_when_insertingTwoForTheSameChart() {
    chartsDatabase.chartDao().insert(A_DUMMY_CHART_ENTITY)

    chartsDatabase.chartValueDao().insertAll(A_TWO_ELEMENT_LIST_OF_CHART_VALUES)

    val testSubscriber = chartsDatabase.chartValueDao().getChartValues(A_DUMMY_CHART_NAME).test()

    testSubscriber.assertValue { chartValues -> chartValues.size == 2 }
    testSubscriber.assertValue { chartValues ->
      chartValues.first().x == A_DUMMY_X_VALUE &&
          chartValues.first().y == A_DUMMY_Y_VALUE &&
          chartValues.first().chart == A_DUMMY_CHART_NAME
    }
    testSubscriber.assertValue { chartValues ->
      chartValues[1].x == ANOTHER_DUMMY_X_VALUE &&
          chartValues[1].y == ANOTHER_DUMMY_Y_VALUE &&
          chartValues[1].chart == A_DUMMY_CHART_NAME
    }
  }

  @After
  fun tearDown() {
    chartsDatabase.close()
  }
}

private const val A_DUMMY_CHART_NAME = "a_dummy_chart_name"

private const val A_DUMMY_X_VALUE = 123.toDouble()
private const val A_DUMMY_Y_VALUE = 123.toDouble()
private val A_DUMMY_CHART_VALUE = ChartValueEntity(A_DUMMY_X_VALUE, A_DUMMY_Y_VALUE, A_DUMMY_CHART_NAME)
private const val ANOTHER_DUMMY_X_VALUE = 456.toDouble()
private const val ANOTHER_DUMMY_Y_VALUE = 456.toDouble()
private val ANOTHER_DUMMY_CHART_VALUE = ChartValueEntity(ANOTHER_DUMMY_X_VALUE, ANOTHER_DUMMY_Y_VALUE, A_DUMMY_CHART_NAME)
private val A_ONE_ELEMENT_LIST_OF_CHART_VALUES = listOf(A_DUMMY_CHART_VALUE)

private val A_TWO_ELEMENT_LIST_OF_CHART_VALUES = listOf(A_DUMMY_CHART_VALUE, ANOTHER_DUMMY_CHART_VALUE)
private const val A_UNIT_USD = "USD"

private const val A_PERIOD_DAY = "day"
private const val A_DUMMY_DESCRIPTION = "A dummy description"
private val A_DUMMY_CHART_ENTITY = ChartEntity(A_DUMMY_CHART_NAME, A_UNIT_USD, A_PERIOD_DAY, A_DUMMY_DESCRIPTION)

package com.example.cryptotrackerapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CoinDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: CoinItemDatabase
    private lateinit var dao: CoinDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CoinItemDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.coinDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val coinItem = CoinItem(1, "bitcoin", 20000.0)
        dao.insertCoinItem(coinItem)

        val allShoppingItems = dao.observeAllCoinItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(coinItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val coinItem = CoinItem(1, "bitcoin", 20000.0)
        dao.insertCoinItem(coinItem)
        dao.deleteCoinItem(coinItem)

        val allShoppingItems = dao.observeAllCoinItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(coinItem)
    }


}
package com.roomedia.dakku

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.roomedia.dakku.persistence.Diary
import com.roomedia.dakku.repository.DiaryRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.measureTimeMillis

@RunWith(AndroidJUnit4::class)
class DatabaseMeasureTimeTest {

    private val diaryRepository = DiaryRepository()
    private val diaryDao = diaryRepository.dao

    @Test
    fun databaseInsertSpeed_measure(): Unit = runBlocking {
        diaryRepository.deleteAll()
        val job1: Deferred<Unit>
        val time1 = measureTimeMillis {
            job1 = GlobalScope.async {
                diaryDao.insert(*(1L..1000L).map { Diary(it) }.toTypedArray())
            }
        }
        val time2 = measureTimeMillis { job1.await() }
        Log.d(TAG, "databaseInsertSpeed_measure: $time1 $time2")

        diaryRepository.deleteAll()
        val job2: Deferred<Unit>
        val time3 = measureTimeMillis {
            job2 = GlobalScope.async {
                (1L..1000L)
                    .map { Diary(it) }
                    .forEach {
                        diaryDao.insert(it)
                    }
            }
        }
        val time4 = measureTimeMillis { job2.await() }
        Log.d(TAG, "databaseInsertSpeed_measure: $time3 $time4")

        diaryRepository.deleteAll()
        val job3: List<Deferred<Unit>>
        val time5 = measureTimeMillis {
            job3 = (1L..1000L).map {
                GlobalScope.async {
                    diaryDao.insert(Diary(it))
                }
            }
        }
        val time6 = measureTimeMillis {
            job3.forEach { it.await() }
        }
        Log.d(TAG, "databaseInsertSpeed_measure: $time5 $time6")
    }

    companion object {
        private const val TAG = "measured"
    }
}

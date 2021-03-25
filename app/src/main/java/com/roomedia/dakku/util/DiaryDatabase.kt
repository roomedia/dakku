package com.roomedia.dakku.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.roomedia.dakku.data.Diary
import com.roomedia.dakku.model.DiaryDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Database(entities = [Diary::class,], version = 1, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile private var instance: DiaryDatabase? = null
        fun getInstance(applicationContext: Context): DiaryDatabase {
            if (instance == null) {
                synchronized(DiaryDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            applicationContext,
                            DiaryDatabase::class.java,
                            "contact-database"
                        )
                            .addCallback(setInitialRoomDatabaseCallback())
                            .build()
                    }
                }
            }
            return instance!!
        }

        // MARK: test input data
        private fun setInitialRoomDatabaseCallback() = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                GlobalScope.launch {
                    instance!!.diaryDao().apply {
                        deleteAll()
                        insert(
                            Diary(0, simpleDateFormat.parse("2021-02-10")!!.time, "210210"),
                            Diary(0, simpleDateFormat.parse("2021-02-10")!!.time, "210210-2ㅜㅜ", lock = true),
                            Diary(0, simpleDateFormat.parse("2021-02-13")!!.time, "210213 오늘의 일", bookmark = true),
                            Diary(0, simpleDateFormat.parse("2021-02-14")!!.time, ".", lock = true),
                            Diary(0, simpleDateFormat.parse("2021-02-15")!!.time, "배고프다", bookmark = true),
                            Diary(0, simpleDateFormat.parse("2021-02-18")!!.time, "210218"),
                            Diary(0, simpleDateFormat.parse("2021-02-20")!!.time, "210220"),
                            Diary(0, simpleDateFormat.parse("2021-02-21")!!.time, "210221"),
                            Diary(0, simpleDateFormat.parse("2021-02-21")!!.time, "210227"),
                            Diary(0, simpleDateFormat.parse("2021-02-27")!!.time, "뭐더라", lock = true),
                            Diary(0, simpleDateFormat.parse("2021-03-01")!!.time, "오늘 어이 없었던 거..,,", bookmark = true, lock = true),
                            Diary(0, simpleDateFormat.parse("2021-03-02")!!.time, "210302"),
                            Diary(0, simpleDateFormat.parse("2021-03-04")!!.time, "210304"),
                            Diary(0, simpleDateFormat.parse("2021-03-04")!!.time, "?????"),
                            Diary(0, simpleDateFormat.parse("2021-03-05")!!.time, "아진짜"),
                            Diary(0, simpleDateFormat.parse("2021-03-06")!!.time, "배고파.."),
                        )
                    }
                }
            }
        }
    }
}

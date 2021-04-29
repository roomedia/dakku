package com.roomedia.dakku.persistence

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.roomedia.dakku.DakkuApplication

@TypeConverters(DakkuTypeConverter::class)
@Database(entities = [Diary::class, Sticker::class], version = 1, exportSchema = false)
abstract class DakkuDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
    abstract fun stickerDao(): StickerDao

    companion object {
        val instance: DakkuDatabase = Room.databaseBuilder(
            DakkuApplication.instance,
            DakkuDatabase::class.java,
            "dakku-database"
        ).build()
    }
}

package com.roomedia.dakku.persistence

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface CommonDao<T> {
    @Insert
    fun insert(vararg entities: T)

    @Update
    fun update(vararg entities: T)

    @Delete
    fun delete(vararg entities: T)
}

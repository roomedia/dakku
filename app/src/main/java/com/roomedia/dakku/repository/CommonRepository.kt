package com.roomedia.dakku.repository

import com.roomedia.dakku.persistence.CommonDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class CommonRepository<T> {
    abstract val dao: CommonDao<T>

    fun insert(vararg entities: T) {
        GlobalScope.launch {
            dao.insert(*entities)
        }
    }

    fun update(vararg entities: T) {
        GlobalScope.launch {
            dao.update(*entities)
        }
    }

    fun delete(vararg entities: T) {
        GlobalScope.launch {
            dao.delete(*entities)
        }
    }
}

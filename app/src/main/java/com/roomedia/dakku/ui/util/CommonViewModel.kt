package com.roomedia.dakku.ui.util

import androidx.lifecycle.ViewModel
import com.roomedia.dakku.repository.CommonRepository

abstract class CommonViewModel<T> : ViewModel() {
    abstract val repository: CommonRepository<T>

    fun insert(vararg entities: T) {
        repository.insert(*entities)
    }

    fun update(vararg entities: T) {
        repository.update(*entities)
    }

    fun delete(vararg entities: T) {
        repository.delete(*entities)
    }
}

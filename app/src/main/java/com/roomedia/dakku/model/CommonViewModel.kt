package com.roomedia.dakku.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.roomedia.dakku.data.CommonRepository

abstract class CommonViewModel<T>(application: Application) : AndroidViewModel(application) {
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

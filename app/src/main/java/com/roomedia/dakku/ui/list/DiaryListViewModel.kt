package com.roomedia.dakku.ui.list

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.roomedia.dakku.persistence.Diary
import com.roomedia.dakku.repository.DiaryRepository
import com.roomedia.dakku.ui.util.CommonViewModel

class DiaryListViewModel : CommonViewModel<Diary>(), LifecycleOwner {

    override val repository = DiaryRepository()
    val diaries = repository.diaries
    private val registry by lazy { LifecycleRegistry(this) }

    override fun getLifecycle(): Lifecycle {
        return registry
    }

    fun updateAll() {
        registry.currentState = Lifecycle.State.RESUMED
        diaries.observe(this) {
            update(*it.toTypedArray())
            registry.currentState = Lifecycle.State.DESTROYED
        }
    }
}

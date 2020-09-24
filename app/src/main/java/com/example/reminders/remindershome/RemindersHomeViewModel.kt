package com.example.reminders.remindershome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reminders.database.AppDatabase

class RemindersHomeViewModel(database: AppDatabase) : ViewModel() {
    private val _addSuccess = MutableLiveData<Boolean>()
    val addSuccess: LiveData<Boolean>
        get() = _addSuccess

    fun addNewReminder() {
        _addSuccess.value = true
    }

    fun finishAdding() {
        _addSuccess.value = false
    }
}

class RemindersHomeViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(RemindersHomeViewModel::class.java)) {
            return RemindersHomeViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

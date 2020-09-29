package com.example.reminders.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReminderDao {
    @Insert
    fun add(reminder: Reminder): Long

    @Update
    fun update(reminder: Reminder)

    @Query("DELETE FROM reminders WHERE :reminderId = id")
    fun delete(reminderId: Int)

    @Query("DELETE FROM reminders")
    fun deleteAll()

    @Query("SELECT * FROM reminders")
    fun getAll(): LiveData<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE :reminderId = id")
    fun getReminder(reminderId: Int): Reminder
}

package com.example.reminders.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReminderDao {
    @Insert
    fun add(reminder: Reminder)

    @Update
    fun update(reminder: Reminder)

    @Delete
    fun delete(reminder: Reminder)

    @Query("DELETE FROM reminders")
    fun deleteAll()

    @Query("SELECT * FROM reminders")
    fun getAll(): LiveData<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE :reminderId = id")
    fun getReminder(reminderId: Int): Reminder
}

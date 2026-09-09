package com.ally.care.data.db

import androidx.room.*
import com.ally.care.data.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments ORDER BY dateTimeMillis ASC")
    fun observeAll(): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE id = :id")
    suspend fun getById(id: Long): Appointment?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(appointment: Appointment): Long

    @Delete
    suspend fun delete(appointment: Appointment)
}

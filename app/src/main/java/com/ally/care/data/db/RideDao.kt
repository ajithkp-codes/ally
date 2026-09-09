package com.ally.care.data.db

import androidx.room.*
import com.ally.care.data.RideRequest
import kotlinx.coroutines.flow.Flow

@Dao
interface RideDao {
    @Query("SELECT * FROM rides ORDER BY dateTimeMillis ASC")
    fun observeAll(): Flow<List<RideRequest>>

    @Query("SELECT * FROM rides WHERE id = :id")
    suspend fun getById(id: Long): RideRequest?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(ride: RideRequest): Long

    @Delete
    suspend fun delete(ride: RideRequest)
}

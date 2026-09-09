package com.ally.care.data

import com.ally.care.data.db.AllyDatabase
import kotlinx.coroutines.flow.Flow

class AllyRepository(db: AllyDatabase) {
    private val appointmentDao = db.appointmentDao()
    private val rideDao = db.rideDao()

    fun observeAppointments(): Flow<List<Appointment>> = appointmentDao.observeAll()
    fun observeRides(): Flow<List<RideRequest>> = rideDao.observeAll()

    suspend fun getAppointment(id: Long): Appointment? = appointmentDao.getById(id)
    suspend fun getRide(id: Long): RideRequest? = rideDao.getById(id)

    suspend fun saveAppointment(appointment: Appointment): Long = appointmentDao.upsert(appointment)
    suspend fun deleteAppointment(appointment: Appointment) = appointmentDao.delete(appointment)

    suspend fun saveRide(ride: RideRequest): Long = rideDao.upsert(ride)
    suspend fun deleteRide(ride: RideRequest) = rideDao.delete(ride)
}

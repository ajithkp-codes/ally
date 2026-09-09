package com.ally.care.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ally.care.data.AllyRepository
import com.ally.care.data.Appointment
import com.ally.care.data.AppointmentStatus
import com.ally.care.data.RideRequest
import com.ally.care.data.RideStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AllyViewModel(private val repository: AllyRepository) : ViewModel() {

    val appointments: StateFlow<List<Appointment>> = repository.observeAppointments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rides: StateFlow<List<RideRequest>> = repository.observeRides()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    suspend fun getAppointment(id: Long) = repository.getAppointment(id)
    suspend fun getRide(id: Long) = repository.getRide(id)

    fun saveAppointment(appointment: Appointment) {
        viewModelScope.launch { repository.saveAppointment(appointment) }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch { repository.deleteAppointment(appointment) }
    }

    fun setAppointmentStatus(appointment: Appointment, status: AppointmentStatus) {
        viewModelScope.launch { repository.saveAppointment(appointment.copy(status = status)) }
    }

    fun saveRide(ride: RideRequest) {
        viewModelScope.launch { repository.saveRide(ride) }
    }

    fun deleteRide(ride: RideRequest) {
        viewModelScope.launch { repository.deleteRide(ride) }
    }

    fun setRideStatus(ride: RideRequest, status: RideStatus) {
        viewModelScope.launch { repository.saveRide(ride.copy(status = status)) }
    }

    companion object {
        fun factory(repository: AllyRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AllyViewModel(repository) as T
                }
            }
    }
}

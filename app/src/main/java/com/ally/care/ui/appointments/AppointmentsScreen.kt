package com.ally.care.ui.appointments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ally.care.data.Appointment
import com.ally.care.data.AppointmentStatus
import com.ally.care.ui.AllyViewModel
import com.ally.care.ui.common.StatusLabel
import com.ally.care.ui.common.formatDateTime
import com.ally.care.ui.common.statusColor

@Composable
fun AppointmentsScreen(
    viewModel: AllyViewModel,
    onAdd: () -> Unit,
    onEdit: (Long) -> Unit
) {
    val appointments by viewModel.appointments.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Default.Add, contentDescription = "Add appointment")
            }
        }
    ) { padding ->
        if (appointments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No appointments yet. Tap + to schedule one.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(appointments, key = { it.id }) { appt ->
                    AppointmentCard(
                        appointment = appt,
                        onClick = { onEdit(appt.id) },
                        onComplete = { viewModel.setAppointmentStatus(appt, AppointmentStatus.COMPLETED) },
                        onCancel = { viewModel.setAppointmentStatus(appt, AppointmentStatus.CANCELLED) },
                        onDelete = { viewModel.deleteAppointment(appt) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppointmentCard(
    appointment: Appointment,
    onClick: () -> Unit,
    onComplete: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    OutlinedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(Modifier.weight(1f)) {
                    Text(appointment.title, style = MaterialTheme.typography.titleMedium)
                    Text(appointment.doctorOrProvider, style = MaterialTheme.typography.bodyMedium)
                }
                StatusLabel(appointment.status.name, statusColor(appointment.status))
            }
            Spacer(Modifier.height(8.dp))
            Text(formatDateTime(appointment.dateTimeMillis), style = MaterialTheme.typography.bodyMedium)
            if (appointment.location.isNotBlank()) {
                Text(appointment.location, style = MaterialTheme.typography.bodyMedium)
            }
            if (appointment.status == AppointmentStatus.UPCOMING) {
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = onComplete, label = { Text("Mark done") }, leadingIcon = {
                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                    })
                    AssistChip(onClick = onCancel, label = { Text("Cancel") }, leadingIcon = {
                        Icon(Icons.Default.Close, contentDescription = null)
                    })
                    AssistChip(onClick = onDelete, label = { Text("Delete") }, leadingIcon = {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    })
                }
            }
        }
    }
}

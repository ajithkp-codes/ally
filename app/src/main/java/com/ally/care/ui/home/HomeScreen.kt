package com.ally.care.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ally.care.R
import com.ally.care.data.Appointment
import com.ally.care.data.AppointmentStatus
import com.ally.care.data.RideRequest
import com.ally.care.data.RideStatus
import com.ally.care.ui.AllyViewModel
import com.ally.care.ui.common.formatDateTime

@Composable
fun HomeScreen(
    viewModel: AllyViewModel,
    onAddAppointment: () -> Unit,
    onAddRide: () -> Unit,
    onOpenAppointments: () -> Unit,
    onOpenTransportation: () -> Unit
) {
    val appointments by viewModel.appointments.collectAsStateWithLifecycle()
    val rides by viewModel.rides.collectAsStateWithLifecycle()

    val upcomingAppointments = appointments.filter { it.status == AppointmentStatus.UPCOMING }
    val upcomingRides = rides.filter { it.status == RideStatus.REQUESTED || it.status == RideStatus.CONFIRMED }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Hello 👋",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                stringResource(R.string.app_tagline),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                QuickActionCard(
                    icon = Icons.Default.LocalHospital,
                    label = "New Appointment",
                    modifier = Modifier.weight(1f),
                    onClick = onAddAppointment
                )
                QuickActionCard(
                    icon = Icons.Default.DirectionsCar,
                    label = "Book a Ride",
                    modifier = Modifier.weight(1f),
                    onClick = onAddRide
                )
            }
        }

        item {
            SectionHeader(
                title = "Upcoming appointments",
                count = upcomingAppointments.size,
                onSeeAll = onOpenAppointments
            )
        }
        if (upcomingAppointments.isEmpty()) {
            item { EmptyStateText("No appointments scheduled yet.") }
        } else {
            items(upcomingAppointments.take(3)) { appt ->
                AppointmentSummaryCard(appt)
            }
        }

        item {
            SectionHeader(
                title = "Upcoming rides",
                count = upcomingRides.size,
                onSeeAll = onOpenTransportation
            )
        }
        if (upcomingRides.isEmpty()) {
            item { EmptyStateText("No rides arranged yet.") }
        } else {
            items(upcomingRides.take(3)) { ride ->
                RideSummaryCard(ride)
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, count: Int, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$title ($count)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        TextButton(onClick = onSeeAll) { Text("See all") }
    }
}

@Composable
private fun EmptyStateText(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun QuickActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(onClick = onClick, modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            Text(label, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun AppointmentSummaryCard(appointment: Appointment) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(appointment.title, style = MaterialTheme.typography.titleMedium)
            Text(appointment.doctorOrProvider, style = MaterialTheme.typography.bodyMedium)
            Text(
                formatDateTime(appointment.dateTimeMillis),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun RideSummaryCard(ride: RideRequest) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("${ride.pickupLocation} → ${ride.dropoffLocation}", style = MaterialTheme.typography.titleMedium)
            if (ride.purpose.isNotBlank()) {
                Text(ride.purpose, style = MaterialTheme.typography.bodyMedium)
            }
            Text(
                formatDateTime(ride.dateTimeMillis),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

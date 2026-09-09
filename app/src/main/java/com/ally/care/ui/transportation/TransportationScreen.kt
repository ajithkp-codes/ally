package com.ally.care.ui.transportation

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
import com.ally.care.data.RideRequest
import com.ally.care.data.RideStatus
import com.ally.care.ui.AllyViewModel
import com.ally.care.ui.common.StatusLabel
import com.ally.care.ui.common.formatDateTime
import com.ally.care.ui.common.statusColor

@Composable
fun TransportationScreen(
    viewModel: AllyViewModel,
    onAdd: () -> Unit,
    onEdit: (Long) -> Unit
) {
    val rides by viewModel.rides.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Default.Add, contentDescription = "Add ride")
            }
        }
    ) { padding ->
        if (rides.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No rides arranged yet. Tap + to book one.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(rides, key = { it.id }) { ride ->
                    RideCard(
                        ride = ride,
                        onClick = { onEdit(ride.id) },
                        onConfirm = { viewModel.setRideStatus(ride, RideStatus.CONFIRMED) },
                        onComplete = { viewModel.setRideStatus(ride, RideStatus.COMPLETED) },
                        onCancel = { viewModel.setRideStatus(ride, RideStatus.CANCELLED) },
                        onDelete = { viewModel.deleteRide(ride) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RideCard(
    ride: RideRequest,
    onClick: () -> Unit,
    onConfirm: () -> Unit,
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
                    Text("${ride.pickupLocation} → ${ride.dropoffLocation}", style = MaterialTheme.typography.titleMedium)
                    if (ride.purpose.isNotBlank()) {
                        Text(ride.purpose, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                StatusLabel(ride.status.name, statusColor(ride.status))
            }
            Spacer(Modifier.height(8.dp))
            Text(formatDateTime(ride.dateTimeMillis), style = MaterialTheme.typography.bodyMedium)
            if (ride.driverOrServiceContact.isNotBlank()) {
                Text("Contact: ${ride.driverOrServiceContact}", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                when (ride.status) {
                    RideStatus.REQUESTED -> {
                        AssistChip(onClick = onConfirm, label = { Text("Confirm") }, leadingIcon = {
                            Icon(Icons.Default.CheckCircle, contentDescription = null)
                        })
                        AssistChip(onClick = onCancel, label = { Text("Cancel") }, leadingIcon = {
                            Icon(Icons.Default.Close, contentDescription = null)
                        })
                    }
                    RideStatus.CONFIRMED -> {
                        AssistChip(onClick = onComplete, label = { Text("Mark done") }, leadingIcon = {
                            Icon(Icons.Default.CheckCircle, contentDescription = null)
                        })
                        AssistChip(onClick = onCancel, label = { Text("Cancel") }, leadingIcon = {
                            Icon(Icons.Default.Close, contentDescription = null)
                        })
                    }
                    else -> {}
                }
                AssistChip(onClick = onDelete, label = { Text("Delete") }, leadingIcon = {
                    Icon(Icons.Default.Delete, contentDescription = null)
                })
            }
        }
    }
}

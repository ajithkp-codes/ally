package com.ally.care.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ally.care.nav.AllyDestination
import com.ally.care.ui.appointments.AppointmentEditScreen
import com.ally.care.ui.appointments.AppointmentsScreen
import com.ally.care.ui.home.HomeScreen
import com.ally.care.ui.transportation.RideEditScreen
import com.ally.care.ui.transportation.TransportationScreen

private data class TopLevelTab(val destination: AllyDestination, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

private val topLevelTabs = listOf(
    TopLevelTab(AllyDestination.Home, "Home", Icons.Default.Home),
    TopLevelTab(AllyDestination.Appointments, "Appointments", Icons.Default.CalendarMonth),
    TopLevelTab(AllyDestination.Transportation, "Rides", Icons.Default.DirectionsCar)
)

@Composable
fun AllyApp(viewModel: AllyViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            val showBottomBar = topLevelTabs.any { currentRoute?.hierarchy?.any { h -> h.route == it.destination.route } == true }
            if (showBottomBar) {
                NavigationBar {
                    topLevelTabs.forEach { tab ->
                        NavigationBarItem(
                            selected = currentRoute?.hierarchy?.any { it.route == tab.destination.route } == true,
                            onClick = {
                                navController.navigate(tab.destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = AllyDestination.Home.route,
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {
            composable(AllyDestination.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onAddAppointment = { navController.navigate(AllyDestination.AppointmentEdit.buildRoute(null)) },
                    onAddRide = { navController.navigate(AllyDestination.RideEdit.buildRoute(null)) },
                    onOpenAppointments = { navController.navigate(AllyDestination.Appointments.route) },
                    onOpenTransportation = { navController.navigate(AllyDestination.Transportation.route) }
                )
            }
            composable(AllyDestination.Appointments.route) {
                AppointmentsScreen(
                    viewModel = viewModel,
                    onAdd = { navController.navigate(AllyDestination.AppointmentEdit.buildRoute(null)) },
                    onEdit = { id -> navController.navigate(AllyDestination.AppointmentEdit.buildRoute(id)) }
                )
            }
            composable(AllyDestination.Transportation.route) {
                TransportationScreen(
                    viewModel = viewModel,
                    onAdd = { navController.navigate(AllyDestination.RideEdit.buildRoute(null)) },
                    onEdit = { id -> navController.navigate(AllyDestination.RideEdit.buildRoute(id)) }
                )
            }
            composable(
                AllyDestination.AppointmentEdit.route,
                arguments = listOf(navArgument(AllyDestination.AppointmentEdit.ARG_ID) {
                    type = NavType.LongType
                    defaultValue = -1L
                })
            ) { entry ->
                val id = entry.arguments?.getLong(AllyDestination.AppointmentEdit.ARG_ID)?.takeIf { it >= 0 }
                AppointmentEditScreen(
                    viewModel = viewModel,
                    appointmentId = id,
                    onDone = { navController.popBackStack() }
                )
            }
            composable(
                AllyDestination.RideEdit.route,
                arguments = listOf(navArgument(AllyDestination.RideEdit.ARG_ID) {
                    type = NavType.LongType
                    defaultValue = -1L
                })
            ) { entry ->
                val id = entry.arguments?.getLong(AllyDestination.RideEdit.ARG_ID)?.takeIf { it >= 0 }
                RideEditScreen(
                    viewModel = viewModel,
                    rideId = id,
                    onDone = { navController.popBackStack() }
                )
            }
        }
    }
}

package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.viewmodel.UserSchedule

@Composable
fun AvailabilityBox(
    name: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(105.dp)
            .height(65.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            fontSize = 11.sp,
            lineHeight = 13.sp,
            maxLines = 2
        )
    }
}

@Composable
fun Screen5UserAvailabilities(
    schedules: List<UserSchedule> = emptyList(),
    onNewAvailabilityClicked: () -> Unit = {},
    onDashboardClicked: () -> Unit = {}
) {
    var selectedScheduleForDialog by remember { mutableStateOf<UserSchedule?>(null) }

    // Detail Pop-up Dialog dynamically rendering actual slots
    selectedScheduleForDialog?.let { schedule ->
        AlertDialog(
            onDismissRequest = { selectedScheduleForDialog = null },
            title = { Text(text = schedule.title, style = MaterialTheme.typography.titleMedium) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Status: Active / Submitted", fontSize = 12.sp, color = Color.Gray)
                    HorizontalDivider()
                    Text(text = "Configured Time Slots:", fontSize = 14.sp, style = MaterialTheme.typography.bodyMedium)

                    if (schedule.slots.isEmpty()) {
                        Text(text = "• No slots added.", fontSize = 13.sp)
                    } else {
                        schedule.slots.forEach { slot ->
                            Text(text = "• ${slot.date}: ${slot.timeRange}", fontSize = 13.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { selectedScheduleForDialog = null }) {
                    Text("BACK")
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "GATHER", fontSize = 25.sp)
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
            Text(text = "YOUR TIME TO GATHER", fontSize = 15.sp)
            Spacer(modifier = Modifier.height(30.dp))

            Text(text = "MY AVAILABILITIES", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(10.dp))

            schedules.chunked(3).forEach { rowSchedules ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    rowSchedules.forEach { schedule ->
                        AvailabilityBox(name = schedule.title) {
                            selectedScheduleForDialog = schedule
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            Button(onClick = onNewAvailabilityClicked) {
                Text("+ NEW AVAILABILITY")
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(text = "WHEN I'M GATHERING", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AvailabilityBox("RSVPED MEETING 1") {
                    selectedScheduleForDialog = UserSchedule("RSVPED MEETING 1", listOf(AvailabilitySlot("OCTOBER 28", "09:00 - 12:00")))
                }
                AvailabilityBox("RSVPED MEETING 2") {
                    selectedScheduleForDialog = UserSchedule("RSVPED MEETING 2", listOf(AvailabilitySlot("OCTOBER 29", "14:00 - 16:00")))
                }
                AvailabilityBox("RSVPED MEETING 3") {
                    selectedScheduleForDialog = UserSchedule("RSVPED MEETING 3", listOf(AvailabilitySlot("OCTOBER 30", "11:00 - 15:00")))
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(onClick = onDashboardClicked) {
                Text("BACK TO DASHBOARD")
            }
        }
    }
}
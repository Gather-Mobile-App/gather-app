package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AvailabilitySlot(val date: String, val timeRange: String)

@Composable
fun Screen6NewAvailability(
    modifier: Modifier = Modifier,
    onSaveClicked: (title: String, slots: List<AvailabilitySlot>) -> Unit = { _, _ -> },
    onBackClicked: () -> Unit = {},
    onImportClicked: () -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf("24") } // Dynamic selected calendar day
    var fromTime by remember { mutableStateOf("00:00") }
    var toTime by remember { mutableStateOf("11:00") }

    // Dynamic list of availability slots added by the user
    val availabilityList = remember { mutableStateListOf<AvailabilitySlot>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("GATHER", fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        }

        Text("NEW AVAILABILITY / RSVP AVAILABILITY", fontSize = 12.sp, color = Color.Gray)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text("ENTER TITLE FOR SCHEDULE") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("MY AVAILABILITIES", style = MaterialTheme.typography.bodyMedium)
            OutlinedButton(onClick = onImportClicked) {
                Text("IMPORT .ics")
            }
        }

        // Calendar (Tapping any day updates selectedDay)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                Text(
                    "<     October 2026     >",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(8.dp))

                val calendarWeeks = listOf(
                    listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"),
                    listOf("", "", "", "1", "2", "3", "4"),
                    listOf("5", "6", "7", "8", "9", "10", "11"),
                    listOf("12", "13", "14", "15", "16", "17", "18"),
                    listOf("19", "20", "21", "22", "23", "24", "25"),
                    listOf("26", "27", "28", "29", "30", "31", "")
                )
                calendarWeeks.forEach { week ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        week.forEach { day ->
                            val isSelected = day == selectedDay && day.isNotEmpty()
                            Text(
                                text = day,
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp,
                                color = if (isSelected) Color.White else Color.Black,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(1.dp)
                                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                                    .clickable(enabled = day.isNotEmpty() && day.toIntOrNull() != null) {
                                        selectedDay = day
                                    }
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // From / To inputs + ADD button
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = fromTime,
                onValueChange = { fromTime = it },
                modifier = Modifier.weight(1f),
                label = { Text("From") }
            )
            OutlinedTextField(
                value = toTime,
                onValueChange = { toTime = it },
                modifier = Modifier.weight(1f),
                label = { Text("To") }
            )
            OutlinedButton(
                onClick = {
                    if (fromTime.isNotBlank() && toTime.isNotBlank()) {
                        availabilityList.add(
                            AvailabilitySlot(
                                date = "OCTOBER $selectedDay",
                                timeRange = "$fromTime - $toTime"
                            )
                        )
                    }
                }
            ) {
                Text("ADD")
            }
        }

        // Dynamically preview added slots on Screen 6
        availabilityList.groupBy { it.date }.forEach { (date, slots) ->
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Screen6Box(date, Color(0xFFF3F3F3))
                slots.forEach { slot ->
                    Screen6Box(slot.timeRange)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBackClicked) {
                Text("BACK")
            }
            Button(
                onClick = {
                    // If list is empty, automatically create a slot from current selected date & time inputs
                    val finalSlots = if (availabilityList.isEmpty()) {
                        listOf(
                            AvailabilitySlot(
                                date = "OCTOBER $selectedDay",
                                timeRange = "$fromTime - $toTime"
                            )
                        )
                    } else {
                        availabilityList.toList()
                    }

                    onSaveClicked(title, finalSlots)
                }
            ) {
                Text("SAVE / SUBMIT")
            }
        }
    }
}

@Composable
fun Screen6Box(text: String, background: Color = Color.White) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(background)
            .border(1.dp, Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}
package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---- Sample data for the prototype ----
private val FitGreen = Color(0xFFD5E8D4)
private val FitGreenBorder = Color(0xFF82B366)

private data class Slot(val time: String, val fits: Boolean)
private data class Participant(val name: String, val slots: List<Slot>)
private data class DateOption(val label: String, val stats: String, val participants: List<Participant>)

private val sampleOptions = listOf(
    DateOption(
        "Sat, Oct 24 · 18:00 - 22:00",
        "RSVPs: 3   ·   Fit this option: 3\nBest time: 18:00 - 20:00 (3 of 3 free)",
        listOf(
            Participant("PARTICIPANT 1", listOf(Slot("15:00 - 22:00", true))),
            Participant("PARTICIPANT 2", listOf(Slot("08:00 - 10:00", false), Slot("14:00 - 23:59", true))),
            Participant("PARTICIPANT 3", listOf(Slot("12:00 - 12:30", false), Slot("15:00 - 20:00", true)))
        )
    ),
    DateOption(
        "Sun, Oct 25 · 13:00 - 17:00",
        "RSVPs: 3   ·   Fit this option: 2\nBest time: 14:00 - 16:00 (2 of 3 free)",
        listOf(
            Participant("PARTICIPANT 1", listOf(Slot("14:00 - 16:00", true))),
            Participant("PARTICIPANT 2", listOf(Slot("No free time on this date", false))),
            Participant("PARTICIPANT 3", listOf(Slot("13:00 - 17:00", true)))
        )
    ),
    DateOption(
        "Mon, Oct 26 · 09:00 - 12:00",
        "RSVPs: 3   ·   Fit this option: 1\nBest time: 09:00 - 12:00 (1 of 3 free)",
        listOf(
            Participant("PARTICIPANT 1", listOf(Slot("No free time on this date", false))),
            Participant("PARTICIPANT 2", listOf(Slot("09:00 - 12:00", true))),
            Participant("PARTICIPANT 3", listOf(Slot("18:00 - 21:00", false)))
        )
    )
)

// Screen 7 - VIEW EVENT USER AVAILABILITIES (UI prototype, sample data only)
@Composable
fun Screen7EventAvailabilities(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {}
) {
    var selected by remember { mutableIntStateOf(0) }
    val option = sampleOptions[selected]

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        item {
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
        }
        item {
            Text(
                "EVENT USER AVAILABILITY SCREEN",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item { Text("EVENT TITLE", fontSize = 22.sp, fontWeight = FontWeight.Bold) }
        item { Text("Share your event!\nThis event's RSVP code is:", textAlign = TextAlign.Center) }
        item {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("A1A1A1", fontSize = 28.sp, style = MaterialTheme.typography.headlineMedium)
            }
        }

        // DATE OPTION 1 / 2 / 3
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                sampleOptions.indices.forEach { i ->
                    val isSelected = i == selected
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(if (isSelected) FitGreen else Color.White, RoundedCornerShape(10.dp))
                            .border(1.dp, if (isSelected) FitGreenBorder else Color.Black, RoundedCornerShape(10.dp))
                            .clickable { selected = i }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("DATE\nOPTION ${i + 1}", textAlign = TextAlign.Center, fontSize = 13.sp)
                    }
                }
            }
        }

        item { Text(option.label, fontWeight = FontWeight.Bold) }

        // Stats readout
        item {
            Text(
                option.stats,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
        }

        // One card per participant. Green rows = fit the selected date option.
        items(option.participants) { participant ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Screen7Row(participant.name, background = Color(0xFFF3F3F3), borderColor = Color.Gray)
                participant.slots.forEach { slot ->
                    Screen7Row(
                        slot.time,
                        background = if (slot.fits) FitGreen else Color.White,
                        borderColor = if (slot.fits) FitGreenBorder else Color.Gray
                    )
                }
            }
        }

        item {
            Button(onClick = onBackClicked, modifier = Modifier.fillMaxWidth()) {
                Text("BACK TO DASHBOARD")
            }
        }
    }
}

@Composable
private fun Screen7Row(text: String, background: Color, borderColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(background)
            .border(1.dp, borderColor),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}

package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.data.GatherRepository
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.logic.OverlapCalculator
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.logic.TimeUtils

private val FitGreen = Color(0xFFD5E8D4)
private val FitGreenBorder = Color(0xFF82B366)

// Screen 7 - VIEW EVENT USER AVAILABILITIES (FR-02, FR-04)
// Shows the selected event's RSVPs, the best time for each date option,
// and whether the minimum number of people (quorum) has been reached.
@Composable
fun Screen7EventAvailabilities(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {}
) {
    val event = GatherRepository.selectedEvent()
    var selected by remember { mutableIntStateOf(0) }

    // Work everything out before drawing the list
    val rsvps = if (event == null) emptyList() else GatherRepository.rsvpsFor(event.id)
    val results = event?.dateOptions?.map { OverlapCalculator.calculate(it, rsvps) } ?: emptyList()
    val bestOptionIndex = results.indices.maxByOrNull { results[it].bestCount } ?: 0
    val sel = if (results.isEmpty()) 0 else selected.coerceIn(0, results.lastIndex)

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

        if (event == null || event.dateOptions.isEmpty()) {
            item { Text("No event selected. Open an event from the Dashboard.") }
            item {
                Button(onClick = onBackClicked, modifier = Modifier.fillMaxWidth()) {
                    Text("BACK TO DASHBOARD")
                }
            }
            return@LazyColumn
        }

        val option = event.dateOptions[sel]
        val result = results[sel]
        val windowStart = TimeUtils.toMinutes(option.startTime) ?: 0
        val windowEnd = TimeUtils.toMinutes(option.endTime) ?: 0

        item { Text(event.title.uppercase(), fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) }
        item { Text("Share your event!\nThis event's RSVP code is:", textAlign = TextAlign.Center) }
        item {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(event.rsvpCode, fontSize = 28.sp, style = MaterialTheme.typography.headlineMedium)
            }
        }

        // DATE OPTION buttons (★ = option with the most people free)
        item {
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                event.dateOptions.indices.forEach { i ->
                    val isSelected = i == sel
                    val star = if (i == bestOptionIndex && results[i].bestCount > 0) " ★" else ""
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .background(if (isSelected) FitGreen else Color.White, RoundedCornerShape(10.dp))
                            .border(1.dp, if (isSelected) FitGreenBorder else Color.Black, RoundedCornerShape(10.dp))
                            .clickable { selected = i }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("DATE\nOPTION ${i + 1}$star", textAlign = TextAlign.Center, fontSize = 13.sp)
                    }
                }
            }
        }

        item {
            Text(
                "${TimeUtils.dateLabel(option.date)} · ${option.startTime} - ${option.endTime}",
                fontWeight = FontWeight.Bold
            )
        }

        // Stats readout (FR-02) + attendance rules (FR-04)
        item {
            val bestText = if (result.bestCount == 0) {
                "nobody is free in this window yet"
            } else {
                result.bestWindows.joinToString(", ") {
                    "${TimeUtils.fromMinutes(it.first)} - ${TimeUtils.fromMinutes(it.second)}"
                } + " (${result.bestCount} of ${result.totalRsvps} free)"
            }
            val needed = event.minAttendees - result.bestCount
            val quorumText = if (needed <= 0) {
                "Quorum met ✓ (needs ${event.minAttendees})"
            } else {
                "Needs $needed more to reach the minimum of ${event.minAttendees}"
            }
            val capText = when {
                event.maxAttendees == 0 -> "No attendee limit"
                rsvps.size >= event.maxAttendees -> "Full (${rsvps.size} of ${event.maxAttendees})"
                else -> "Spots left: ${event.maxAttendees - rsvps.size} of ${event.maxAttendees}"
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("RSVPs: ${result.totalRsvps}")
                Text("Free for the whole time: ${result.fullyFree}  ·  Part of it: ${result.partlyFree}")
                Text("Best time: $bestText", fontWeight = FontWeight.Bold)
                Text(quorumText, color = if (needed <= 0) FitGreenBorder else Color.Red, fontWeight = FontWeight.Bold)
                Text(capText)
            }
        }

        if (rsvps.isEmpty()) {
            item { Text("No RSVPs yet. Share the code so people can respond.", color = Color.Gray) }
        }

        // One card per participant. Green rows = free during the selected date option.
        items(rsvps) { rsvp ->
            val blocks = rsvp.blocks.filter { it.date == option.date }.sortedBy { it.startTime }
            Column(modifier = Modifier.fillMaxWidth()) {
                Screen7Row(rsvp.name.uppercase(), background = Color(0xFFF3F3F3), borderColor = Color.Gray)
                if (blocks.isEmpty()) {
                    Screen7Row("No free time on this date", background = Color.White, borderColor = Color.Gray)
                }
                blocks.forEach { block ->
                    val s = TimeUtils.toMinutes(block.startTime) ?: 0
                    val e = TimeUtils.toMinutes(block.endTime) ?: 0
                    val fits = s < windowEnd && windowStart < e
                    Screen7Row(
                        "${block.startTime} - ${block.endTime}",
                        background = if (fits) FitGreen else Color.White,
                        borderColor = if (fits) FitGreenBorder else Color.Gray
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
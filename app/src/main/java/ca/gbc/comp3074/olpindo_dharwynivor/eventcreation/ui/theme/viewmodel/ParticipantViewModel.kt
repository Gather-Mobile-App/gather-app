package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.AvailabilitySlot

// Data model holding schedule details
data class UserSchedule(
    val title: String,
    val slots: List<AvailabilitySlot>
)

class ParticipantViewModel : ViewModel() {
    var currentEventCode by mutableStateOf("")
        private set

    // Default schedules list with dynamic models
    var savedSchedules = mutableStateOf(
        listOf(
            UserSchedule("MAIN AVAILABILITY", listOf(AvailabilitySlot("OCTOBER 23", "15:00 - 22:00"))),
            UserSchedule("SCHEDULE 2", listOf(AvailabilitySlot("OCTOBER 24", "08:00 - 11:30"))),
            UserSchedule("SCHEDULE 3", listOf(AvailabilitySlot("OCTOBER 24", "13:45 - 18:00")))
        )
    )
        private set

    var latestNotification by mutableStateOf<String?>(null)

    fun updateEventCode(code: String) {
        currentEventCode = code
    }

    // Save dynamic title and custom added slots
    fun submitAvailability(title: String, slots: List<AvailabilitySlot>) {
        val finalTitle = title.ifBlank { "UNTITLED SCHEDULE" }
        val newSchedule = UserSchedule(title = finalTitle, slots = slots)
        savedSchedules.value = savedSchedules.value + newSchedule
        triggerNotification("Availability '$finalTitle' submitted successfully!")
    }

    fun importIcsCalendar() {
        val importedSchedule = UserSchedule(
            title = "ICS_Imported_Schedule",
            listOf(AvailabilitySlot("OCTOBER 27", "10:00 - 17:00"))
        )
        savedSchedules.value = savedSchedules.value + importedSchedule
        triggerNotification("Successfully imported calendar (.ics) file!")
    }

    fun triggerNotification(message: String) {
        latestNotification = message
    }

    fun clearNotification() {
        latestNotification = null
    }
}
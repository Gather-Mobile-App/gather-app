package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class ParticipantViewModel : ViewModel() {
    var currentEventCode by mutableStateOf("")
        private set
    var savedSchedules = mutableStateOf(listOf("MAIN AVAILABILITY", "SCHEDULE 2", "SCHEDULE 3"))
    var latestNotification by mutableStateOf<String?>(null)
    fun updateEventCode(code: String) {
        currentEventCode = code
    }
    fun addSchedule(schedule: String) {
        if (schedule.isNotBlank() && !savedSchedules.value.contains(schedule)) {
            savedSchedules.value = savedSchedules.value + schedule
            triggerNotification("New schedule '$schedule'successfully saved!")
        }
    }
    fun importIcsCalendar() {
        val importedName ="ICS_Imported_Schedule"
        if (!savedSchedules.value.contains(importedName)) {
            savedSchedules.value = savedSchedules.value + importedName
        }
        triggerNotification("Successfully imported calendar (.ics) file!")
    }
    fun submitAvailability(title: String) {
        if (title.isNotBlank()) {
            addSchedule(title)
        }
        triggerNotification("Availability submitted successfully! Notification rules active.")
    }
    fun triggerNotification(message: String) {
        latestNotification = message
    }
    fun clearNotification() {
        latestNotification = null
    }
}
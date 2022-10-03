package com.amqo.notesappkmm.domain.time

import kotlinx.datetime.*

object DateTimeUtil {

    fun now(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun toEpochMillis(dateTime: LocalDateTime): Long {
        return dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    fun formatNoteDate(dateTime: LocalDateTime): String {
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val day = formatDateFieldWithZero(dateTime.dayOfMonth)
        val year = dateTime.year
        val hour = formatDateFieldWithZero(dateTime.hour)
        val minutes = formatDateFieldWithZero(dateTime.minute)
        return "$month $day $year, $hour:$minutes"
    }

    private fun formatDateFieldWithZero(dateField: Int): String {
        return if (dateField < 10) "0${dateField}" else dateField.toString()
    }
}
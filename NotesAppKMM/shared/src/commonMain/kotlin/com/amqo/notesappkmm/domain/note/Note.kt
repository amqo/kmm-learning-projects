package com.amqo.notesappkmm.domain.note

import com.amqo.notesappkmm.presentation.*
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        const val pendingId = -1L

        private val colors = listOf(
            RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex
        )
        fun generateRandomColor() = colors.random()
    }
}

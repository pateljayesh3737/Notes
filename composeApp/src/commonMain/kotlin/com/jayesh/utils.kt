package com.jayesh

import kotlinx.datetime.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun generateRandomUuid() : String {
    return Uuid.random().toString()
}

fun getCurrnetLocalDateTime() : LocalDateTime {
    val now: Instant = Clock.System.now()
    val today: LocalDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

    return today
}
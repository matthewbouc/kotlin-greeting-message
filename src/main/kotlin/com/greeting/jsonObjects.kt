package com.greeting

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Company (
    val id: Int,
    val company: String,
    val city: String,
    val timezone: String,
)


@Serdeable
data class Template (
    val id: Int,
    val message: String,
)

@Serdeable
data class Guest (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val reservation: Reservation,
)

data class Reservation (
    val roomNumber: Int,
    val startTimestamp: Int,
    val endTimestamp: Int,
)

@Serdeable
data class MessageVariables (
    val salutation: String,
    val company: Company,
    val guest: Guest,
)
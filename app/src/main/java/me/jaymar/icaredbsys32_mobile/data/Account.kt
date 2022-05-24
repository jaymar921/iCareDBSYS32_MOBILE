package me.jaymar.icaredbsys32_mobile.data

import java.util.*

data class Account(
    val acc_id: String,
    val lastname: String,
    val firstname: String,
    val birthdate: Date,
    val house_no: String,
    val street: String,
    val barangay: String,
    val city: String,
    val zip: String,
    val registry_date: Date) {
}
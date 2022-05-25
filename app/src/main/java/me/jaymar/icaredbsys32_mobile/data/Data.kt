package me.jaymar.icaredbsys32_mobile.data

import me.jaymar.icaredbsys32_mobile.annotation.GamitNiSya

@GamitNiSya
data class Data(
    val account: Account,
    val loginCredentials: LoginCredentials) {
}
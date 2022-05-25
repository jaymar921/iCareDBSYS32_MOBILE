package me.jaymar.icaredbsys32_mobile.data

import me.jaymar.icaredbsys32_mobile.annotation.GamitNiSya

@GamitNiSya
data class LoginCredentials(
    var login_id : String,
    var acc_id : String,
    var username : String,
    var password : String,
    var email: String,
    var contact_no: String
    ) {
}
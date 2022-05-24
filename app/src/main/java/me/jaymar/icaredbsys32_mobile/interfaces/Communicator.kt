package me.jaymar.icaredbsys32_mobile.interfaces

import me.jaymar.icaredbsys32_mobile.data.ServiceData

interface Communicator {
    fun passData(data: ServiceData)
}
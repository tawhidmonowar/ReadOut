package org.tawhid.readout

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
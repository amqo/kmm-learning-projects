package com.amqo.demoappkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
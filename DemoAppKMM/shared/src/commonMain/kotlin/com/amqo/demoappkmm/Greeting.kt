package com.amqo.demoappkmm

class Greeting {
    private val platform: Platform = getPlatform()

    fun greeting(): String {
        return "Running from ${platform.name}!"
    }
}
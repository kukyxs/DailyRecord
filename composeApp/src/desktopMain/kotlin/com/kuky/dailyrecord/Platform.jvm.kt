package com.kuky.dailyrecord

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getPlatformName(): String {
    return System.getProperty("os.name")
}
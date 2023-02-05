package com.loudbook.dev

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class Redisson() {
    var redisson: RedissonClient? = null
    private var uri: String? = null
    fun connect() {
        try {
            FileInputStream("./plugins/PiEvents/redisson.properties").use { input ->
                val prop = Properties()
                prop.load(input)
                this.uri = prop.getProperty("uri")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        val config = Config()
        config.useSingleServer()
            .address = uri
        this.redisson = Redisson.create(config)
    }

    fun sendMessage(channel: String, message: String) {
        val topic = this.redisson!!.getTopic(channel)
        topic.publish(message)
    }
}
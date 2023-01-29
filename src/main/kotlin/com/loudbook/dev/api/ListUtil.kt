package com.loudbook.dev.api

import com.loudbook.dev.Effect
import java.util.*

object ListUtil {
    fun <T> randomListElement(list: List<T>): T {
        val rand = Random()
        return list[rand.nextInt(list.size)]
    }
}
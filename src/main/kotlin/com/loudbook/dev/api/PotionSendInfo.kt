package com.loudbook.dev.api

import com.loudbook.dev.Effect
import com.loudbook.dev.PiPlayer
import org.bukkit.entity.Player

data class PotionSendInfo(val sender: PiPlayer, val receiver: PiPlayer, val effect: Effect)
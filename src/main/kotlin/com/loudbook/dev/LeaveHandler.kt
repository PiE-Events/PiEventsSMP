package com.loudbook.dev

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class LeaveHandler(private val playerManager: PlayerManager) : Listener {
    @EventHandler
    fun onLeave(e: PlayerQuitEvent) {
        playerManager.removePlayer(e.player.uniqueId)
    }
}
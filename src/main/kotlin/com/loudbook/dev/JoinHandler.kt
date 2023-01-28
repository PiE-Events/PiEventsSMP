package com.loudbook.dev

import com.loudbook.dev.api.Firestore
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinHandler(private val playerManager: PlayerManager, private val firestore: Firestore) : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = playerManager.addPlayer(e.player)
        this.firestore.getEffects(player)
        this.firestore.getVault(player)

    }
}
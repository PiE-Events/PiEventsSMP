package com.loudbook.dev

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class RedisHandler(private val redisson: Redisson) : Listener {
    @EventHandler
    fun onMessage(e: AsyncPlayerChatEvent) {
        this.redisson.sendMessage("mcmessage", "MESSAGE:${e.player.name}: ${e.message}")
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        this.redisson.sendMessage("mcmessage", "JOIN:${e.player.name}")
    }

    @EventHandler
    fun onLeave(e: PlayerQuitEvent) {
        this.redisson.sendMessage("mcmessage", "LEAVE:${e.player.name}")
    }

    @EventHandler
    fun onDeath(e: EntityDamageByEntityEvent) {
        if (e.entity !is Player) return
        val player = e.entity as Player
        if (player.health - e.finalDamage > 0) return
        if (e.damager !is Player) return
        this.redisson.sendMessage("mcmessage", "DEATH:${player.killer?.name}:${player.name}")
    }
}
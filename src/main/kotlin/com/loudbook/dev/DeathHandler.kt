package com.loudbook.dev

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.Plugin

class DeathHandler(private val playerManager: PlayerManager, private val plugin: Plugin) : Listener {
    @EventHandler
    fun onDamage(e: EntityDamageEvent) {
        if (e.entity !is Player) return
        val player = e.entity as Player
        if (player.health - e.finalDamage > 0) return

        val piPlayerEntity = this.playerManager.getPlayer(e.entity as Player)
        if (piPlayerEntity.lastDamaged != null) {
            val killer = piPlayerEntity.lastDamaged
            println(piPlayerEntity.effects)
            val toBeRemoved = ArrayList<Effect>()
            for (effect in piPlayerEntity.effects) {
                if (killer!!.hasEffect(effect)) {
                    if (killer.vault.isFull()) {
                        killer.player.sendMessage(ChatColor.RED.toString() + "Your vault is full! The effect ${effect.formattedName} was not added.")
                        piPlayerEntity.player.sendMessage(ChatColor.GREEN.toString() + "The effect ${effect.formattedName} was not added to ${killer.player.name}'s vault because it is full.")
                        continue
                    }
                    killer.addToVault(effect)
                    killer.player.sendMessage(ChatColor.GREEN.toString() + "You have gained ${effect.formattedName}! Because its a duplicate, it has been added to your vault.")
                    toBeRemoved.add(effect)
                } else {
                    killer.addEffect(effect)
                    killer.player.sendMessage(ChatColor.GREEN.toString() + "You have gained ${effect.formattedName}!")
                    toBeRemoved.add(effect)
                }
            }
            for (effect in toBeRemoved) {
                piPlayerEntity.removeEffect(effect)
            }
        }
    }

    @EventHandler
    fun onPlayerDamage(e: EntityDamageByEntityEvent) {
        if (e.entity is Player && e.damager is Player) {
            val piPlayerEntity = this.playerManager.getPlayer(e.entity as Player)
            val piPlayerDamager = this.playerManager.getPlayer(e.damager as Player)

            piPlayerEntity.lastDamaged = piPlayerDamager

            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, Runnable {
                 if (piPlayerEntity.lastDamaged != null) {
                     piPlayerEntity.lastDamaged = null
                 }
            }, 200L)
        }
    }

    @EventHandler
    fun onResapwn(e: PlayerRespawnEvent) {
        val piPlayer = this.playerManager.getPlayer(e.player)
        val toBeRemoved = ArrayList<Effect>()
        for (effect in piPlayer.vault.effects) {
            if (!piPlayer.hasEffect(effect)) {
                piPlayer.addEffect(effect)
                toBeRemoved.add(effect)
            }
        }
        for (effect in toBeRemoved) {
            piPlayer.vault.remove(effect)
        }
    }
}
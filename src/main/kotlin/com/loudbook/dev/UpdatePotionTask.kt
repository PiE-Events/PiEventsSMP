package com.loudbook.dev

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class UpdatePotionTask(playerManager: PlayerManager, plugin: Plugin) {
    init {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            for (piPlayer in playerManager.piPlayers) {
                for (effect in piPlayer.effects) {
                    if (effect == Effect.INVISIBILITY && !piPlayer.wantsInvis) {
                        piPlayer.player.removePotionEffect(effect.effect.type)
                        continue
                    }
                    piPlayer.player.addPotionEffect(effect.effect)
                }
            }
        }, 0, 20)
    }
}
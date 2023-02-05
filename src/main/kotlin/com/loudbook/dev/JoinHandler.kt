package com.loudbook.dev

import com.loudbook.dev.api.Firestore
import com.loudbook.dev.api.ListUtil
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinHandler(private val playerManager: PlayerManager, private val firestore: Firestore) : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = playerManager.addPlayer(e.player)
        this.firestore.getEffects(player)
        this.firestore.getVault(player)
        if (!e.player.hasPlayedBefore()) {
            val randomPotion: Effect = getArrayList().random()
            e.player.sendTitle(
                randomPotion.formattedName,
                ChatColor.GOLD.toString() + "Your random potion has been selected!",
                20,
                60,
                20
            )
            player.addEffect(randomPotion)
        }
    }
    private fun getArrayList(): ArrayList<Effect> {
        val list: ArrayList<Effect> = ArrayList()
        for (value in Effect.values()) {
            list.add(value)
        }
        return list
    }
}
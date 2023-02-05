package com.loudbook.dev

import com.loudbook.dev.api.Firestore
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class VaultHandler(private val playerManager: PlayerManager, private val firestore: Firestore) : Listener {
    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        if (e.view.title == "Effect Vault" && e.currentItem != null) {

            val piPlayer = this.playerManager.getPlayer(e.whoClicked as Player)

            e.isCancelled = true
            if (e.currentItem!!.itemMeta == null) return

            val effect = Effect.getFromTab(ChatColor.stripColor(e.currentItem!!.itemMeta!!.displayName)!!)
            if (effect == null) {
                piPlayer.player.sendMessage(ChatColor.RED.toString() + "An error occurred while trying to equip this effect!")
                return
            }

            if (piPlayer.hasEffect(effect)) {
                piPlayer.player.sendMessage(ChatColor.RED.toString() + "You already have this effect equipped!")
                return
            }
            piPlayer.vault.remove(effect)
            this.firestore.pushVault(piPlayer)
            piPlayer.addEffect(effect)

            piPlayer.player.sendMessage(ChatColor.GREEN.toString() + "You have equipped the " + effect.name + " effect!")
            piPlayer.vault.updateItems()
        }
    }
}
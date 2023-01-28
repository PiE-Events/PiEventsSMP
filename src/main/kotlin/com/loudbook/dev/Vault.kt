package com.loudbook.dev

import com.loudbook.dev.api.Firestore
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.Listener
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag

class Vault(private val piPlayer: PiPlayer, private val firestore: Firestore) : Listener {
    private val inventory: Inventory = Bukkit.createInventory(null, 54, "Effect Vault")

    val effects: MutableList<Effect> = ArrayList()

    fun openInventory() {
        this.piPlayer.player.sendMessage(ChatColor.GREEN.toString() + "Opening your vault...")
        updateItems()
        this.piPlayer.player.openInventory(inventory)
    }

    fun remove(effect: Effect) {
        this.effects.remove(effect)
        this.firestore.pushVault(this.piPlayer)
    }

    fun add(effect: Effect) {
        this.effects.add(effect)
        this.firestore.pushVault(this.piPlayer)
    }

    fun isFull(): Boolean {
        return this.effects.size >= 54
    }

    fun updateItems() {
        this.inventory.contents = arrayOfNulls(54)
        for ((index, effect) in firestore.getVault(this.piPlayer).withIndex()) {
            val itemStack = Effect.getItemStack(effect)
            if (this.piPlayer.hasEffect(effect)) {
                val meta = itemStack.itemMeta
                val lore = meta!!.lore
                lore!!.add(" ")
                lore.add(ChatColor.RED.toString() + "You already have this effect equipped!")
                meta.lore = lore
                itemStack.itemMeta = meta
            } else {
                val meta = itemStack.itemMeta
                val lore = meta!!.lore
                lore!!.add(" ")
                lore.add(ChatColor.GREEN.toString() + "Click to equip!")
                meta.addEnchant(Enchantment.DURABILITY, 1, true)
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                meta.lore = lore
                itemStack.itemMeta = meta
            }
            this.inventory.setItem(index, itemStack)
        }
    }
}
package com.loudbook.dev

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

enum class Effect(val effect: PotionEffect, val formattedName: String, val description: String, val material: Material, val color: ChatColor) {
    SPEED(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 2, true, true), "Speed",
        "Increases your speed!", Material.SUGAR, ChatColor.AQUA),
    JUMP(PotionEffect(PotionEffectType.JUMP, Int.MAX_VALUE, 2, true, true), "Jump",
        "Increases your jump height!", Material.FEATHER, ChatColor.AQUA);

    companion object {
        fun getFromTab(tabComplete: String?): Effect? {
            for (value in Effect.values()) {
                if (value.formattedName.equals(tabComplete, true)) {
                    return value
                }
            }
            return null
        }

        fun getItemStack(effect: Effect): ItemStack {
            val itemStack = ItemStack(effect.material)
            var itemMeta = itemStack.itemMeta
            if (itemMeta == null) {
                itemMeta = Bukkit.getItemFactory().getItemMeta(effect.material)
            }

            if (itemMeta != null) {
                itemMeta.setDisplayName(effect.color.toString() + ChatColor.BOLD.toString() + effect.formattedName)
                itemMeta.lore = listOf(ChatColor.GRAY.toString() + effect.description)
                itemStack.itemMeta = itemMeta
            }
            return itemStack
        }
    }
}
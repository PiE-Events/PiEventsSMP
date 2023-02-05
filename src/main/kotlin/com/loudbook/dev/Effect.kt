package com.loudbook.dev

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

enum class Effect(val effect: PotionEffect, val formattedName: String, val description: String, val material: Material, val color: ChatColor) {
    SPEED(PotionEffect(PotionEffectType.SPEED, 30, 0, true, true),"Speed",
        "Increases your speed!", Material.SUGAR, ChatColor.AQUA),
    JUMP(PotionEffect(PotionEffectType.JUMP, 30, 2, true, true), "Jump",
        "Increases your jump height!", Material.FEATHER, ChatColor.AQUA),
    STRENGTH(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30, 2, true, true), "Strength",
        "Increases your damage!", Material.IRON_SWORD, ChatColor.RED),
    REGENERATION(PotionEffect(PotionEffectType.REGENERATION, 30, 1, true, true), "Regeneration",
        "Regenerates your health!", Material.GOLDEN_APPLE, ChatColor.GREEN),
    NIGHT_VISION(PotionEffect(PotionEffectType.NIGHT_VISION, 30, 0, true, true), "Night Vision",
        "Allows you to see in the dark!", Material.GLOWSTONE_DUST, ChatColor.DARK_PURPLE),
    INVISIBILITY(PotionEffect(PotionEffectType.INVISIBILITY, 30, 0, true, true), "Invisibility",
        "Makes you invisible!", Material.INK_SAC, ChatColor.DARK_PURPLE),
    WATER_BREATHING(PotionEffect(PotionEffectType.WATER_BREATHING, 30, 0, true, true), "Water Breathing",
        "Allows you to breathe underwater!", Material.POTION, ChatColor.DARK_PURPLE),
    FIRE_RESISTANCE(PotionEffect(PotionEffectType.FIRE_RESISTANCE, 30, 0, true, true), "Fire Resistance",
        "Makes you immune to fire!", Material.BLAZE_POWDER, ChatColor.DARK_PURPLE),
    CONDUIT_POWER(PotionEffect(PotionEffectType.CONDUIT_POWER, 30, 0, true, true), "Conduit Power",
        "Gives you conduit power!", Material.PRISMARINE_CRYSTALS, ChatColor.DARK_PURPLE),
    HEALTH_BOOST(PotionEffect(PotionEffectType.HEALTH_BOOST, 30, 1, true, true), "Health Boost",
        "Increases your max health!", Material.GOLDEN_APPLE, ChatColor.GREEN),
    HASTE(PotionEffect(PotionEffectType.FAST_DIGGING, 30, 1, true, true), "Haste",
        "Increases your mining speed!", Material.DIAMOND_PICKAXE, ChatColor.GOLD);

    companion object {
        fun getFromTab(tabComplete: String): Effect? {
            for (value in Effect.values()) {
                if (value.formattedName.equals(tabComplete.replace("_", " "), true)) {
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
package com.loudbook.dev.commands

import com.loudbook.dev.Effect
import com.loudbook.dev.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.*

class GiveEffectTabComplete(private val playerManager: PlayerManager) : TabCompleter{
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        if (args.size == 1) {
            val player = playerManager.getPlayer(sender as Player)
            val potions: MutableList<Effect> = player.effects
            potions.addAll(player.vault.effects)

            val potionNames: MutableList<String> = ArrayList()
            for (potion in potions) {
                if (potionNames.contains(potion.formattedName)) continue
                potionNames.add(potion.formattedName)
            }
            return potionNames
        }
        val onlinePlayers: List<Player> = ArrayList(Bukkit.getOnlinePlayers())
        val playerName: MutableList<String> = ArrayList()
        for (onlinePlayer in onlinePlayers) {
            playerName.add(onlinePlayer.name)
        }
        return playerName
    }
}
package com.loudbook.dev.commands

import com.loudbook.dev.Effect
import com.loudbook.dev.PiPlayer
import com.loudbook.dev.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class RemoveEffectTabComplete(private val playerManager: PlayerManager) : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (args.size == 2) {

            if (Bukkit.getPlayer(args[0]) == null) {
                return null
            }

            val player: PiPlayer = playerManager.getPlayer(Bukkit.getPlayer(args[0])!!)
            val effects: List<Effect> = player.effects
            val potionNames: MutableList<String> = ArrayList()
            for (effect in effects) {
                potionNames.add(effect.formattedName)
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
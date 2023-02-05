package com.loudbook.dev.commands

import com.loudbook.dev.Effect
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class AddEffectTabComplete : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        if (args.size == 2) {
            val effectNames: MutableList<String> = ArrayList()
            for (value in Effect.values()) {
                effectNames.add(value.formattedName.replace(" ", "_"))
            }
            return effectNames
        }
        val onlinePlayers: List<Player> = ArrayList(Bukkit.getOnlinePlayers())
        val playerName: MutableList<String> = ArrayList()
        for (onlinePlayer in onlinePlayers) {
            playerName.add(onlinePlayer.name)
        }
        return playerName
    }
}
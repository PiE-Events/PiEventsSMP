package com.loudbook.dev.commands

import com.loudbook.dev.Effect
import com.loudbook.dev.PlayerManager
import com.loudbook.dev.api.Firestore
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class RemoveEffectCommand(private val playerManager: PlayerManager, private val firestore: Firestore) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("${ChatColor.RED}Player not found")
            return true
        }

        val player = playerManager.getPlayer(Bukkit.getPlayer(args[0])!!)

        if (args[1].isEmpty() || Effect.getFromTab(args[1]) == null) {
            sender.sendMessage("${ChatColor.RED}Potion not found")
            return true
        }

        if (player.hasEffect(Effect.getFromTab(args[1])!!)) {
            player.removeEffect(Effect.getFromTab(args[1])!!)
            this.firestore.updateEffects(player)
            sender.sendMessage("${ChatColor.GREEN}Effect removed.")
        } else {
            sender.sendMessage("${ChatColor.RED}Player does not have this potion.")
        }

        return true
    }
}
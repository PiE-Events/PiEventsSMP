package com.loudbook.dev.commands

import com.loudbook.dev.Effect
import com.loudbook.dev.PlayerManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Invisible(private val playerManager: PlayerManager) : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name == "toggleinvis") {
            val player = playerManager.getPlayer(sender as Player)
            if (player.hasEffect(Effect.INVISIBILITY)) {
                return if (player.wantsInvis) {
                    player.wantsInvis = false
                    sender.sendMessage(ChatColor.GREEN.toString() + "You are now visible!")
                    true
                } else {
                    player.wantsInvis = true
                    sender.sendMessage(ChatColor.GREEN.toString() + "You are now invisible!")
                    true
                }
            } else {
                player.player.sendMessage(ChatColor.RED.toString() + "You do not have the invisibility effect!")

            }
            return true
        }
        return true
    }
}
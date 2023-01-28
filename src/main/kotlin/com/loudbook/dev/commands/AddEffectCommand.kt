package com.loudbook.dev.commands

import com.loudbook.dev.Effect
import com.loudbook.dev.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class AddEffectCommand(private val playerManager: PlayerManager) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("${ChatColor.RED}Player not found")
            return true
        }

        val player = playerManager.getPlayer(Bukkit.getPlayer(args[0])!!)



        if (Effect.getFromTab(args[1]) == null) {
            sender.sendMessage("${ChatColor.RED}Effect not found")
            return true
        }

        val effect = Effect.getFromTab(args[1])!!

        if (player.effects.contains(effect)) {
            if (player.vault.isFull()) {
                sender.sendMessage("${ChatColor.RED}Player's vault is full!")
                return true
            }
            player.vault.add(effect)
            sender.sendMessage(ChatColor.GREEN.toString() + "Effect added to vault.")
            return true
        }

        player.addEffect(effect)
        sender.sendMessage(ChatColor.GREEN.toString() + "Effect added.")

        return true
    }
}
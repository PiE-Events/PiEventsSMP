package com.loudbook.dev.commands

import com.loudbook.dev.PlayerManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VaultCommand(private val playerManager: PlayerManager) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name != "vault") return true
        val player = playerManager.getPlayer(sender as Player)
        player.vault.openInventory()
        return true
    }
}
package com.loudbook.dev.commands

import com.loudbook.dev.PlayerManager
import com.loudbook.dev.api.PotionSendInfo
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class AcceptCommand(private val playerManager: PlayerManager) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if ((command.name == "accept")) {
            if (this.playerManager.currentSendings.containsKey(UUID.fromString(args[0]))) {
                val potionSendInfo: PotionSendInfo? = playerManager.currentSendings[UUID.fromString(args[0])]
                val potionSender = potionSendInfo!!.sender
                if (!potionSender.effects.contains(potionSendInfo.effect) && !potionSender.vault.effects.contains(potionSendInfo.effect)) {
                    sender.sendMessage(ChatColor.RED.toString() + "This is no longer valid!")
                    return true
                }

                potionSender.player.sendMessage(
                    ChatColor.GREEN.toString() + sender.name + " has accepted your " + potionSendInfo.effect
                        .formattedName + ChatColor.RESET + ChatColor.GREEN + "!"
                )
                val player = playerManager.getPlayer(sender as Player)

                if (player.hasEffect(potionSendInfo.effect)) {
                    player.vault.add(potionSendInfo.effect)
                    this.playerManager.currentSendings.remove(UUID.fromString(args[0]))
                    player.player.sendMessage(
                        ChatColor.GREEN.toString() + "You have accepted " + potionSendInfo.sender.player.name
                                + "'s " + potionSendInfo.effect.formattedName + ChatColor.RESET + ChatColor.GREEN + "! It has been added to your vault."
                    )
                } else {
                    player.addEffect(potionSendInfo.effect)
                    this.playerManager.currentSendings.remove(UUID.fromString(args[0]))
                    player.player.sendMessage(
                        ChatColor.GREEN.toString() + "You have accepted " + potionSendInfo.sender.player.name
                                + "'s " + potionSendInfo.effect.formattedName + ChatColor.RESET + ChatColor.GREEN + "!"
                    )
                }
                if (potionSender.vault.effects.contains(potionSendInfo.effect)) {
                    potionSender.vault.remove(potionSendInfo.effect)
                } else {
                    potionSender.removeEffect(potionSendInfo.effect)
                }

            } else {
                sender.sendMessage(ChatColor.RED.toString() + "This is no longer valid!")
            }
        }
        return true
    }

}

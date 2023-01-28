package com.loudbook.dev.commands

import com.loudbook.dev.Effect
import com.loudbook.dev.PiPlayer
import com.loudbook.dev.PlayerManager
import com.loudbook.dev.api.PotionSendInfo
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.*

class GiveEffectCommand(private val playerManager: PlayerManager, private val plugin: Plugin) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name != "giveeffect") return true
        if (Effect.getFromTab(args[0]) != null) {
            val onlinePlayers: List<Player> = ArrayList(Bukkit.getOnlinePlayers())
            val names: MutableList<String?> = ArrayList()
            for (onlinePlayer in onlinePlayers) {
                names.add(onlinePlayer.name)
            }
            if (names.contains(args[1])) {
                val receiver = Bukkit.getPlayer(args[1])
                val player = sender as Player

                if (receiver == null) {
                    sender.sendMessage(ChatColor.RED.toString() + "Player not found")
                    return true
                }

                val senderPlayer: PiPlayer = playerManager.getPlayer(sender)
                val receiverPlayer: PiPlayer = playerManager.getPlayer(receiver)

                if (!sender.world.getNearbyEntities(player.location, 15.0, 15.0, 15.0).contains(receiver)) {
                    sender.sendMessage(ChatColor.RED.toString() + "You are too far away from " + receiver.name + " to send potions! (15 blocks)")
                    return true
                }

                val potion: Effect? = Effect.getFromTab(args[0])

                if (potion == null) {
                    sender.sendMessage(ChatColor.RED.toString() + "Potion not found!")
                    return true
                }

                if (!senderPlayer.effects.contains(Effect.getFromTab(args[0])) && !senderPlayer.vault.effects.contains(Effect.getFromTab(args[0]))) {
                    sender.sendMessage(ChatColor.RED.toString() + "You don't have this potion!")
                    return true
                }

                senderPlayer.player
                    .sendMessage(ChatColor.GREEN.toString() + "You have sent " + receiver.name + " " + potion.formattedName + ChatColor.RESET + ChatColor.GREEN + "!")

                val text = TextComponent()

                if (receiverPlayer.effects.contains(potion)) {
                    receiver.sendMessage(ChatColor.GOLD.toString() + sender.getName() + " is sending you " + potion.formattedName + ". (Accepting this will put this effect in your vault.)")
                } else {
                    receiver.sendMessage(ChatColor.GOLD.toString() + sender.getName() + " is sending you " + potion.formattedName + ".")
                }

                text.text = ChatColor.GREEN.toString() + "[Accept]"
                val id = UUID.randomUUID()
                text.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept $id")
                receiver.spigot().sendMessage(text)
                val info = PotionSendInfo(senderPlayer, receiverPlayer, Effect.getFromTab(args[0])!!)
                playerManager.currentSendings[id] = info

                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    if (playerManager.currentSendings.containsKey(id)) {
                        playerManager.currentSendings.remove(id)
                        receiver.sendMessage(
                            ChatColor.RED.toString() + "Your incoming send request from " + info.sender.player
                                .name + " has expired!"
                        )
                        info.sender.player.sendMessage(
                            ChatColor.RED.toString() + "Your outgoing send request to " + info.receiver.player
                                .name + " has expired!"
                        )
                    }
                }, (60 * 20).toLong())
            }
        } else {
            sender.sendMessage(ChatColor.RED.toString() + "Incorrect syntax!")
        }
        return true
    }
}
package com.loudbook.dev

import com.loudbook.dev.api.Firestore
import com.loudbook.dev.commands.*
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class PiEvents : JavaPlugin() {
    private val plugin: Plugin = this
    override fun onEnable() {
        val firestore = Firestore()
        firestore.connect()
        val playerManager = PlayerManager(firestore)
        UpdatePotionTask(playerManager, plugin)

        Bukkit.getPluginCommand("giveeffect")!!.setExecutor(GiveEffectCommand(playerManager, this))
        Bukkit.getPluginCommand("giveeffect")!!.tabCompleter = GiveEffectTabComplete(playerManager)
        Bukkit.getPluginCommand("removeeffect")!!.setExecutor(RemoveEffectCommand(playerManager, firestore))
        Bukkit.getPluginCommand("removeeffect")!!.tabCompleter = RemoveEffectTabComplete(playerManager)
        Bukkit.getPluginCommand("addeffect")!!.setExecutor(AddEffectCommand(playerManager))
        Bukkit.getPluginCommand("addeffect")!!.tabCompleter = AddEffectTabComplete()
        Bukkit.getPluginCommand("accept")!!.setExecutor(AcceptCommand(playerManager))
        Bukkit.getPluginCommand("vault")!!.setExecutor(VaultCommand(playerManager))

        Bukkit.getPluginManager().registerEvents(VaultHandler(playerManager, firestore), this)
        Bukkit.getPluginManager().registerEvents(DeathHandler(playerManager, this), this)
        Bukkit.getPluginManager().registerEvents(JoinHandler(playerManager, firestore), this)
        Bukkit.getPluginManager().registerEvents(LeaveHandler(playerManager), this)
    }
}
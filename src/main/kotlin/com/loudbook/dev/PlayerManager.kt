package com.loudbook.dev

import com.loudbook.dev.api.Firestore
import com.loudbook.dev.api.PotionSendInfo
import org.bukkit.entity.Player
import java.util.*

class PlayerManager(private val firestore: Firestore) {
    private val players: MutableMap<UUID, PiPlayer> = HashMap()
    val piPlayers: MutableList<PiPlayer> = ArrayList()
    val currentSendings: MutableMap<UUID, PotionSendInfo> = HashMap()

    fun addPlayer(player: Player): PiPlayer {
        val piPlayer = PiPlayer(player, firestore)
        this.players[player.uniqueId] = piPlayer
        this.piPlayers.add(piPlayer)
        return piPlayer
    }

    fun removePlayer(uuid: UUID) {
        this.piPlayers.remove(this.players[uuid])
        this.players.remove(uuid)
    }

    fun getPlayer(player: Player): PiPlayer {
        return this.players[player.uniqueId]!!
    }
}
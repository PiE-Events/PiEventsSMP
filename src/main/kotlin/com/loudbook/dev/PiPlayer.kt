package com.loudbook.dev

import com.loudbook.dev.api.Firestore
import org.bukkit.entity.Player
import java.util.*

@Suppress("unused")
class PiPlayer(val player: Player, private val firestore: Firestore) {

    var effects: MutableList<Effect> = ArrayList()
    val vault: Vault = Vault(this, firestore)

    var lastDamaged: PiPlayer? = null

    fun hasEffect(effect: Effect): Boolean {
        return effects.contains(effect)
    }

    fun addEffect(effect: Effect) {
        this.effects.add(effect)
        this.firestore.updateEffects(this)
    }

    fun removeEffect(effect: Effect) {
        this.effects.remove(effect)
        this.player.removePotionEffect(effect.effect.type)
        this.firestore.updateEffects(this)
    }

    fun removeFromVault(effect: Effect) {
        vault.remove(effect)
    }

    fun addToVault(effect: Effect) {
        vault.add(effect)
    }
}
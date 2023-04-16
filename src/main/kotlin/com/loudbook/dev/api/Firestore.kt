package com.loudbook.dev.api

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.loudbook.dev.Effect
import com.loudbook.dev.PiPlayer
import org.bukkit.ChatColor
import java.io.FileInputStream
import java.util.*
import kotlin.collections.ArrayList

class Firestore {
    private lateinit var db: Firestore

    fun connect() {
        val credentials = GoogleCredentials.fromStream(FileInputStream("./plugins/PiEvents/credentials.json"))
        val options = FirebaseOptions.builder()
            .setProjectId("pievents")
            .setCredentials(credentials)
            .build()
        FirebaseApp.initializeApp(options)
        this.db = FirestoreClient.getFirestore()
    }

    fun putEffects(id: UUID, list: ArrayList<Effect>){
        val newList = ArrayList<String>()
        for (effect in list){
            newList.add(ChatColor.stripColor(effect.formattedName)!!)
        }
        db.collection("userdata").document(id.toString()).update(mapOf("effects" to newList))
    }

    fun updateEffects(player: PiPlayer) {
        val list = ArrayList<String>()
        for (effect in player.effects) {
            list.add(ChatColor.stripColor(effect.formattedName)!!)
        }
        if (db.collection("userdata").document(player.player.uniqueId.toString()).get().get() != null) {
            db.collection("userdata").document(player.player.uniqueId.toString()).update(mapOf("effects" to list))
        } else {
            db.collection("userdata").document(player.player.uniqueId.toString()).set(mapOf("effects" to list))
        }
    }

    fun getEffects(player: PiPlayer): ArrayList<Effect> {
        val list = ArrayList<Effect>()
        val doc = db.collection("userdata").document(player.player.uniqueId.toString()).get().get()
        if (doc.get("effects") !is ArrayList<*>) return arrayListOf()
        val effects = doc.get("effects") as ArrayList<*>

        for (effect in effects) {
            list.add(Effect.valueOf((effect as String).uppercase().replace(" ", "_")))
        }
        player.effects = list

        return list
    }

    fun putVault(id: UUID, list: ArrayList<Effect>){
        val newList = ArrayList<String>()
        for (effect in list){
            newList.add(ChatColor.stripColor(effect.formattedName)!!)
        }
        db.collection("userdata").document(id.toString()).update(mapOf("effects" to newList))
    }

    fun pushVault(player: PiPlayer) {
        val list = ArrayList<String>()
        for (effect in player.vault.effects) {
            list.add(ChatColor.stripColor(effect.formattedName)!!)
        }
        db.collection("userdata").document(player.player.uniqueId.toString()).update(mapOf("vault" to list))
    }

    fun getVault(piPlayer: PiPlayer): ArrayList<Effect> {
        val id = piPlayer.player.uniqueId
        val list = ArrayList<Effect>()
        val doc = db.collection("userdata").document(id.toString()).get().get()
        if (doc.get("vault") == null) return arrayListOf()
        if (doc.get("vault") !is ArrayList<*>) return arrayListOf()
        val effects = doc.get("vault") as ArrayList<*>

        for (effect in effects) {
            list.add(Effect.valueOf((effect as String).uppercase().replace(" ", "_")))
            if (!piPlayer.vault.effects.contains(Effect.valueOf(effect.uppercase().replace(" ", "_")))) {
                piPlayer.vault.add(Effect.valueOf(effect.uppercase().replace(" ", "_")))
            }
        }

        return list
    }
}
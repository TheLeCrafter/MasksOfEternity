package dev.thelecrafter.plugins.masksofeternity.util

import org.bukkit.NamespacedKey
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

object ItemUtil {

    fun setUnplaceable(identifier: NamespacedKey, event: PlayerInteractEvent) {
        if (event.hasItem()) {
            if (event.item!!.hasItemMeta()) {
                if (event.item!!.itemMeta.persistentDataContainer.has(identifier, PersistentDataType.STRING)) {
                    event.isCancelled = true
                }
            }
        }
    }

}
package dev.thelecrafter.plugins.masksofeternity.util

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object UpdateUtils {
    fun updateItem(player: Player, identifier: NamespacedKey?, replacementMeta: ItemMeta?) {
        for (item in player.inventory.contents) {
            if (item != null) {
                if (item.hasItemMeta()) {
                    if (item.itemMeta.persistentDataContainer.has(identifier!!, PersistentDataType.STRING)) {
                        item.itemMeta = replacementMeta
                    }
                }
            }
        }
    }
}
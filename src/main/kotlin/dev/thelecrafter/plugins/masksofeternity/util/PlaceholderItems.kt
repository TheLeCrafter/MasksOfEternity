package dev.thelecrafter.plugins.masksofeternity.util

import dev.thelecrafter.plugins.masksofeternity.MasksOfEternityPlugin
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

object PlaceholderItems {
    val isPlaceholderKey = NamespacedKey(MasksOfEternityPlugin.getInstance, "isMasksOfEternityPlaceholderItem")

    fun getItem(material: Material): ItemStack {
        val item = ItemStack(material)
        val meta: ItemMeta = item.itemMeta
        meta.displayName(Component.empty())
        meta.persistentDataContainer.set(isPlaceholderKey, PersistentDataType.STRING, "yeah")
        item.itemMeta = meta
        return item
    }

}
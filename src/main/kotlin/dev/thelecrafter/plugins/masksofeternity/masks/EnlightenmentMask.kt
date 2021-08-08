package dev.thelecrafter.plugins.masksofeternity.masks

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import dev.thelecrafter.plugins.masksofeternity.MasksOfEternityPlugin
import dev.thelecrafter.plugins.masksofeternity.util.ComponentColors
import dev.thelecrafter.plugins.masksofeternity.util.ItemUtil
import dev.thelecrafter.plugins.masksofeternity.util.PlayerHeadUtil
import dev.thelecrafter.plugins.masksofeternity.util.UpdateUtils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

class EnlightenmentMask: Listener {

    companion object {
        private val isEnlightenmentMaskKey: NamespacedKey = NamespacedKey(MasksOfEternityPlugin.getInstance, "isEnlightenmentMask")

        fun asHelmetItem(): ItemStack {
            val item: ItemStack = PlayerHeadUtil.getCustomTextureHead(MaskSettings.EnlightenmentMask)
            val meta: ItemMeta = item.itemMeta
            meta.displayName(
                Component.text("Mask of Enlightenment").color(ComponentColors.BLUE.textColor).decoration(
                    TextDecoration.ITALIC, false))
            meta.lore(listOf(
                Component.empty(),
                Component.text("Gain the enlightenment to").color(ComponentColors.GRAY.textColor),
                Component.text("see every ore in one chunk").color(ComponentColors.GRAY.textColor),
            ))
            meta.persistentDataContainer.set(isEnlightenmentMaskKey, PersistentDataType.STRING, "yeah")
            item.itemMeta = meta
            return item
        }
    }

    @EventHandler
    fun showOres(event: PlayerMoveEvent) {
        if (event.player.inventory.helmet != null) {
            if (event.player.inventory.helmet!!.hasItemMeta()) {
                if (event.player.inventory.helmet!!.itemMeta.persistentDataContainer.has(isEnlightenmentMaskKey, PersistentDataType.STRING)) {
                    if (event.from.chunk != event.to.chunk) {
                        for (x in 0..15) {
                            for (y in event.player.world.minHeight..event.player.world.maxHeight) {
                                for (z in 0..15) {
                                    val oldBlock: Block = event.from.chunk.getBlock(x, y, z)
                                    event.player.sendBlockChange(oldBlock.location, oldBlock.blockData)
                                    val newBlock: Block = event.to.chunk.getBlock(x, y, z)
                                    if (!newBlock.blockData.material.toString().contains("ORE")) {
                                        if (!newBlock.isEmpty) {
                                            event.player.sendBlockChange(newBlock.location, Bukkit.createBlockData(
                                                Material.BARRIER))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    fun reloadOnArmorChange(event: PlayerArmorChangeEvent) {
        if (event.oldItem != null) {
            if (event.oldItem!!.hasItemMeta()) {
                if (event.oldItem!!.itemMeta.persistentDataContainer.has(isEnlightenmentMaskKey, PersistentDataType.STRING)) {
                    for (x in 0..15) {
                        for (y in event.player.world.minHeight..event.player.world.maxHeight) {
                            for (z in 0..15) {
                                val oldBlock: Block = event.player.location.chunk.getBlock(x, y, z)
                                event.player.sendBlockChange(oldBlock.location, oldBlock.blockData)
                            }
                        }
                    }
                }
            }
        }
        if (event.newItem != null) {
            if (event.newItem!!.hasItemMeta()) {
                if (event.newItem!!.itemMeta.persistentDataContainer.has(isEnlightenmentMaskKey, PersistentDataType.STRING)) {
                    for (x in 0..15) {
                        for (y in event.player.world.minHeight..event.player.world.maxHeight) {
                            for (z in 0..15) {
                                val newBlock: Block = event.player.location.chunk.getBlock(x, y, z)
                                if (!newBlock.blockData.material.toString().contains("ORE")) {
                                    if (!newBlock.isEmpty) {
                                        event.player.sendBlockChange(newBlock.location, Bukkit.createBlockData(Material.BARRIER))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    fun unplaceable(event: PlayerInteractEvent) {
        ItemUtil.setUnplaceable(isEnlightenmentMaskKey, event)
    }

    @EventHandler
    fun updateItem(event: PlayerJoinEvent) {
        UpdateUtils.updateItem(event.player, isEnlightenmentMaskKey, asHelmetItem().itemMeta)
    }

}
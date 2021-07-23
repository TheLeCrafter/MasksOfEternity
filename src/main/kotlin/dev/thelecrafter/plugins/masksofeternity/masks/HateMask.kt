package dev.thelecrafter.plugins.masksofeternity.masks

import dev.thelecrafter.plugins.masksofeternity.MasksOfEternityPlugin
import dev.thelecrafter.plugins.masksofeternity.util.ComponentColors
import dev.thelecrafter.plugins.masksofeternity.util.PlayerHeadUtil
import dev.thelecrafter.plugins.masksofeternity.util.UpdateUtils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

class HateMask: Listener {

    companion object {
        private val isHateMaskKey: NamespacedKey = NamespacedKey(MasksOfEternityPlugin.getInstance, "isHateMask")

        fun asHelmetItem(): ItemStack {
            val item: ItemStack = PlayerHeadUtil.getCustomTextureHead(MaskSettings.HateMask)
            val meta: ItemMeta = item.itemMeta
            meta.displayName(
                Component.text("Mask of Hate").color(ComponentColors.YELLOW.textColor).decoration(
                    TextDecoration.ITALIC, false))
            meta.lore(listOf(
                Component.empty(),
                Component.text("Gain the hate to").color(ComponentColors.GRAY.textColor).decoration(TextDecoration.ITALIC, false),
                Component.text("destroy your enemies").color(ComponentColors.GRAY.textColor).decoration(TextDecoration.ITALIC, false),
            ))
            meta.persistentDataContainer.set(isHateMaskKey, PersistentDataType.STRING, "yeah")
            item.itemMeta = meta
            return item
        }

        fun initEffects() {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(MasksOfEternityPlugin.getInstance,
                {
                    for (player in Bukkit.getOnlinePlayers()) {
                        if (player.inventory.helmet != null) {
                            if (player.inventory.helmet!!.hasItemMeta()) {
                                if (player.inventory.helmet!!.itemMeta.persistentDataContainer.has(isHateMaskKey, PersistentDataType.STRING)) {
                                    for (living in player.location.getNearbyLivingEntities(3.5, 1.0)) {
                                        if (living != player) {
                                            living.damage(2.0)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }, 0, 20)
        }
    }

    @EventHandler
    fun updateItem(event: PlayerJoinEvent) {
        UpdateUtils.updateItem(event.player, isHateMaskKey, asHelmetItem().itemMeta)
    }

}
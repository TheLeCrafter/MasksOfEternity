package dev.thelecrafter.plugins.masksofeternity.gauntlet

import dev.thelecrafter.plugins.masksofeternity.MasksOfEternityPlugin
import dev.thelecrafter.plugins.masksofeternity.util.ComponentColors
import dev.thelecrafter.plugins.masksofeternity.util.ItemUtil
import dev.thelecrafter.plugins.masksofeternity.util.PlayerHeadUtil
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

class MagicGauntlet: Listener {

    companion object {
        private val isGauntletKey: NamespacedKey = NamespacedKey(MasksOfEternityPlugin.getInstance, "isGauntletMask")

        fun asHandheldItem(): ItemStack {
            val item: ItemStack = PlayerHeadUtil.getCustomTextureHead(GauntletSettings.MAGIC_GAUNTLET)
            val meta: ItemMeta = item.itemMeta
            meta.displayName(
                Component.text("Magical Gauntlet").color(ComponentColors.GOLD.textColor).decoration(
                    TextDecoration.ITALIC, false))
            meta.lore(listOf(
                Component.empty(),
                Component.text("Harness the power of the four").color(ComponentColors.GOLD.textColor).decoration(TextDecoration.ITALIC, false),
                Component.text("almighty gemstones to gain").color(ComponentColors.GOLD.textColor).decoration(TextDecoration.ITALIC, false),
                Component.text("unlimited power").color(ComponentColors.GOLD.textColor).decoration(TextDecoration.ITALIC, false)
            ))
            meta.persistentDataContainer.set(isGauntletKey, PersistentDataType.STRING, "yeah")
            item.itemMeta = meta
            return item
        }
    }

    @EventHandler
    fun unplaceable(event: PlayerInteractEvent) {
        ItemUtil.setUnplaceable(isGauntletKey, event)
    }
}
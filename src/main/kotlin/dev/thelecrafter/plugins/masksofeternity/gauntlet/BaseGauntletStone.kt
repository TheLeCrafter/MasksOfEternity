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

class BaseGauntletStone: Listener {

    companion object {
        val isGauntletStone = NamespacedKey(MasksOfEternityPlugin.getInstance, "gauntlet_stone")

        fun asHandheldItem(type: GauntletStones): ItemStack {
            val item: ItemStack = when(type) {
                GauntletStones.RED_GEMSTONE -> PlayerHeadUtil.getCustomTextureHead(GauntletSettings.RED_STONE)
                GauntletStones.YELLOW_GEMSTONE -> PlayerHeadUtil.getCustomTextureHead(GauntletSettings.YELLOW_STONE)
                GauntletStones.GREEN_GEMSTONE -> PlayerHeadUtil.getCustomTextureHead(GauntletSettings.GREEN_STONE)
                GauntletStones.BLUE_GEMSTONE -> PlayerHeadUtil.getCustomTextureHead(GauntletSettings.BLUE_STONE)
            }
            val meta: ItemMeta = item.itemMeta
            when(type) {
                GauntletStones.RED_GEMSTONE -> {
                    meta.displayName(Component.text("Red gemstone of power").color(ComponentColors.RED.textColor).decoration(TextDecoration.ITALIC, false))
                    meta.lore(listOf(
                        Component.empty(),
                        Component.text("This stone is imbued with").color(ComponentColors.RED.textColor),
                        Component.text("the power of flame").color(ComponentColors.RED.textColor)
                    ))
                }
                GauntletStones.YELLOW_GEMSTONE -> {
                    meta.displayName(Component.text("Yellow gemstone of power").color(ComponentColors.YELLOW.textColor).decoration(TextDecoration.ITALIC, false))
                    meta.lore(listOf(
                        Component.empty(),
                        Component.text("This stone is imbued with").color(ComponentColors.YELLOW.textColor),
                        Component.text("the power of air").color(ComponentColors.YELLOW.textColor)
                    ))
                }
                GauntletStones.GREEN_GEMSTONE -> {
                    meta.displayName(Component.text("Green gemstone of power").color(ComponentColors.GREEN.textColor).decoration(TextDecoration.ITALIC, false))
                    meta.lore(listOf(
                        Component.empty(),
                        Component.text("This stone is imbued with").color(ComponentColors.GREEN.textColor),
                        Component.text("the power of earth").color(ComponentColors.GREEN.textColor)
                    ))
                }
                GauntletStones.BLUE_GEMSTONE -> {
                    meta.displayName(Component.text("Blue gemstone of power").color(ComponentColors.BLUE.textColor).decoration(TextDecoration.ITALIC, false))
                    meta.lore(listOf(
                        Component.empty(),
                        Component.text("This stone is imbued with").color(ComponentColors.BLUE.textColor),
                        Component.text("the power of water").color(ComponentColors.BLUE.textColor)
                    ))
                }
            }
            meta.persistentDataContainer.set(isGauntletStone, PersistentDataType.STRING, type.toString())
            item.itemMeta = meta
            return item
        }
    }

    @EventHandler
    fun unplaceable(event: PlayerInteractEvent) {
        ItemUtil.setUnplaceable(isGauntletStone, event)
    }
}
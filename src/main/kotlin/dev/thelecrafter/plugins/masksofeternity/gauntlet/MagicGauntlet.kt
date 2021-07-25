package dev.thelecrafter.plugins.masksofeternity.gauntlet

import dev.thelecrafter.plugins.masksofeternity.MasksOfEternityPlugin
import dev.thelecrafter.plugins.masksofeternity.util.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

class MagicGauntlet: Listener {

    companion object {
        private val isGauntletKey: NamespacedKey = NamespacedKey(MasksOfEternityPlugin.getInstance, "isGauntletItem")
        private val getGauntletStones: NamespacedKey = NamespacedKey(MasksOfEternityPlugin.getInstance, "placed_gauntlet_stones")

        fun asHandheldItem(placedGemStones: MutableList<GauntletStones>): ItemStack {
            val item: ItemStack = PlayerHeadUtil.getCustomTextureHead(GauntletSettings.MAGIC_GAUNTLET)
            val meta: ItemMeta = item.itemMeta
            meta.displayName(
                Component.text("Magical Gauntlet").color(ComponentColors.GOLD.textColor).decoration(TextDecoration.ITALIC, false))
            val lore: MutableList<Component> = mutableListOf(
                Component.empty(),
                Component.text("Harness the power of the").color(ComponentColors.GOLD.textColor),
                Component.text("four almighty gemstones").color(ComponentColors.GOLD.textColor),
                Component.text("to gain unlimited power").color(ComponentColors.GOLD.textColor),
                Component.empty(),
                Component.text("Left click").color(ComponentColors.YELLOW.textColor).append(Component.text(" to use a ").color(ComponentColors.GRAY.textColor).append(Component.text("gemstone ability").color(ComponentColors.GOLD.textColor))),
                Component.text("Right click").color(ComponentColors.YELLOW.textColor).append(Component.text(" to switch ").color(ComponentColors.GRAY.textColor).append(Component.text("the ").color(ComponentColors.GRAY.textColor).append(Component.text("gemstone ability").color(ComponentColors.GOLD.textColor)))),
                Component.text("Shift right click").color(ComponentColors.YELLOW.textColor).append(Component.text(" to insert ").color(ComponentColors.GRAY.textColor).append(Component.text("gemstones").color(ComponentColors.GOLD.textColor))),
                Component.empty(),
                Component.text("Placed gemstones").color(ComponentColors.GRAY.textColor).decoration(TextDecoration.ITALIC, false))
            if (placedGemStones.size == 0) lore.add(Component.text("None").color(ComponentColors.DARK_GRAY.textColor).decoration(TextDecoration.ITALIC, false))
            if (placedGemStones.contains(GauntletStones.RED_GEMSTONE)) lore.add(Component.text("Red Gemstone").color(ComponentColors.RED.textColor).decoration(TextDecoration.ITALIC, false))
            if (placedGemStones.contains(GauntletStones.YELLOW_GEMSTONE)) lore.add(Component.text("Yellow Gemstone").color(ComponentColors.YELLOW.textColor).decoration(TextDecoration.ITALIC, false))
            if (placedGemStones.contains(GauntletStones.GREEN_GEMSTONE)) lore.add(Component.text("Green Gemstone").color(ComponentColors.GREEN.textColor).decoration(TextDecoration.ITALIC, false))
            if (placedGemStones.contains(GauntletStones.BLUE_GEMSTONE)) lore.add(Component.text("Blue Gemstone").color(ComponentColors.BLUE.textColor).decoration(TextDecoration.ITALIC, false))
            meta.lore(lore)
            var placedGauntletStonesString = ""
            for (stone in placedGemStones) {
                placedGauntletStonesString += "$stone\n"
            }
            meta.persistentDataContainer.set(getGauntletStones, PersistentDataType.STRING, placedGauntletStonesString)
            meta.persistentDataContainer.set(isGauntletKey, PersistentDataType.STRING, "yeah")
            item.itemMeta = meta
            return item
        }

        fun gemstoneInventory(gemstones: List<GauntletStones>): Inventory {
            val inventory: Inventory = Bukkit.createInventory(null, 27, Component.text("Gemstone "))
            val placeholderItems: List<ItemStack> = listOf(PlaceholderItems.getItem(Material.RED_STAINED_GLASS_PANE), PlaceholderItems.getItem(Material.YELLOW_STAINED_GLASS_PANE), PlaceholderItems.getItem(Material.GREEN_STAINED_GLASS_PANE), PlaceholderItems.getItem(Material.BLUE_STAINED_GLASS_PANE))
            var index = 0
            for (slot in 0..26) {
                inventory.setItem(slot, placeholderItems[index])
                index++
                if (index > placeholderItems.size) index = 0
            }
            inventory.setItem(10, null)
            inventory.setItem(12, null)
            inventory.setItem(14, null)
            inventory.setItem(16, null)
            if (gemstones.contains(GauntletStones.RED_GEMSTONE)) {
                // Add red gemstone
            }
            if (gemstones.contains(GauntletStones.YELLOW_GEMSTONE)) {
                // Add yellow gemstone
            }
            if (gemstones.contains(GauntletStones.GREEN_GEMSTONE)) {
                // Add green gemstone
            }
            if (gemstones.contains(GauntletStones.BLUE_GEMSTONE)) {
                // Add blue gemstone
            }
            return inventory
        }
    }

    @EventHandler
    fun unplaceable(event: PlayerInteractEvent) {
        ItemUtil.setUnplaceable(isGauntletKey, event)
    }

    @EventHandler
    fun updateItem(event: PlayerJoinEvent) {
        for (item in event.player.inventory.contents) {
            if (item != null) {
                if (item.hasItemMeta()) {
                    if (item.itemMeta.persistentDataContainer.has(isGauntletKey, PersistentDataType.STRING)) {
                        val stones: MutableList<GauntletStones> = mutableListOf()
                        for (stone in item.itemMeta.persistentDataContainer.get(getGauntletStones, PersistentDataType.STRING)!!.split("\n")) {
                            if (stone != "") {
                                stones.add(GauntletStones.valueOf(stone))
                            }
                        }
                        item.itemMeta = asHandheldItem(stones).itemMeta
                    }
                }
            }
        }
    }
}
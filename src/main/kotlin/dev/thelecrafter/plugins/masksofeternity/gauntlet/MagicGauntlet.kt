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
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.PrepareSmithingEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.SmithingInventory
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
                Component.text("Gemstone abilities apply on hits").color(ComponentColors.YELLOW.textColor),
                Component.empty(),
                Component.text("Placed gemstones").color(ComponentColors.GRAY.textColor).decoration(TextDecoration.ITALIC, false))
            if (placedGemStones.size == 0) {
                lore.add(Component.text("None").color(ComponentColors.DARK_GRAY.textColor).decoration(TextDecoration.ITALIC, false))
                lore.add(Component.text("Apply gemstones in a smithing table").color(ComponentColors.DARK_GRAY.textColor))
            }
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

    @EventHandler
    fun addSmithingRecipes(event: PrepareSmithingEvent) {
        if (event.inventory.inputEquipment != null) {
            if (event.inventory.inputEquipment!!.hasItemMeta()) {
                if (event.inventory.inputEquipment!!.itemMeta.persistentDataContainer.has(isGauntletKey, PersistentDataType.STRING)) {
                    if (event.inventory.inputMineral != null) {
                        if (event.inventory.inputMineral!!.hasItemMeta()) {
                            if (event.inventory.inputMineral!!.itemMeta.persistentDataContainer.has(BaseGauntletStone.isGauntletStone, PersistentDataType.STRING)) {
                                val gauntletItem: ItemStack = event.inventory.inputEquipment!!
                                val gemstoneItem: ItemStack = event.inventory.inputMineral!!
                                val gemstone: GauntletStones = GauntletStones.valueOf(gemstoneItem.itemMeta.persistentDataContainer.get(BaseGauntletStone.isGauntletStone, PersistentDataType.STRING)!!)
                                val stones: MutableList<GauntletStones> = mutableListOf()
                                for (stone in gauntletItem.itemMeta.persistentDataContainer.get(getGauntletStones, PersistentDataType.STRING)!!.split("\n")) {
                                    if (stone != "") {
                                        stones.add(GauntletStones.valueOf(stone))
                                    }
                                }
                                if (!stones.contains(gemstone)) {
                                    stones.add(gemstone)
                                    event.result = asHandheldItem(stones)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    fun grantItem(event: InventoryClickEvent) {
        if (event.inventory.type == InventoryType.SMITHING) {
            val smithingInventory: SmithingInventory = event.inventory as SmithingInventory
            if (smithingInventory.result != null) {
                if (smithingInventory.result!!.hasItemMeta()) {
                    if (smithingInventory.result!!.itemMeta.persistentDataContainer.has(isGauntletKey, PersistentDataType.STRING)) {
                        if (event.currentItem != null) {
                            if (event.currentItem == smithingInventory.result) {
                                event.cursor = smithingInventory.result
                                smithingInventory.inputEquipment = null
                                smithingInventory.inputMineral = null
                                smithingInventory.result = null
                            }
                        }
                    }
                }
            }
        }
    }
}
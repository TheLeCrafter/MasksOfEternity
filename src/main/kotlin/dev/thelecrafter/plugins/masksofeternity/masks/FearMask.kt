package dev.thelecrafter.plugins.masksofeternity.masks

import dev.thelecrafter.plugins.masksofeternity.MasksOfEternityPlugin
import dev.thelecrafter.plugins.masksofeternity.util.ComponentColors
import dev.thelecrafter.plugins.masksofeternity.util.ItemUtil
import dev.thelecrafter.plugins.masksofeternity.util.PlayerHeadUtil
import dev.thelecrafter.plugins.masksofeternity.util.UpdateUtils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import kotlin.random.Random

class FearMask: Listener {

    companion object {
        private val isFearMaskKey: NamespacedKey = NamespacedKey(MasksOfEternityPlugin.getInstance, "isFearMask")

        fun asHelmetItem(): ItemStack {
            val item: ItemStack = PlayerHeadUtil.getCustomTextureHead(MaskSettings.FearMask)
            val meta: ItemMeta = item.itemMeta
            meta.displayName(
                Component.text("Mask of Fear").color(ComponentColors.WHITE.textColor).decoration(
                    TextDecoration.ITALIC, false))
            meta.lore(listOf(
                Component.empty(),
                Component.text("Gain the fear to").color(ComponentColors.GRAY.textColor).decoration(TextDecoration.ITALIC, false),
                Component.text("dodge every attack").color(ComponentColors.GRAY.textColor).decoration(TextDecoration.ITALIC, false),
            ))
            meta.persistentDataContainer.set(isFearMaskKey, PersistentDataType.STRING, "yeah")
            item.itemMeta = meta
            return item
        }
    }

    @EventHandler
    fun unplaceable(event: PlayerInteractEvent) {
        ItemUtil.setUnplaceable(isFearMaskKey, event)
    }

    @EventHandler
    fun teleportOnDamage(event: EntityDamageEvent) {
        if (event.entity is Player) {
            val player: Player = event.entity as Player
            if (player.inventory.helmet != null) {
                if (player.inventory.helmet!!.hasItemMeta()) {
                    if (player.inventory.helmet!!.itemMeta.persistentDataContainer.has(isFearMaskKey, PersistentDataType.STRING)) {
                        event.isCancelled = true
                        val x: Double = Random.nextDouble(-20.0, 20.0)
                        val z: Double = Random.nextDouble(-20.0, 20.0)
                        player.teleport(Location(player.world, player.location.x + x, player.world.getHighestBlockAt(player.location.blockX + x.toInt(), player.location.blockZ + z.toInt()).location.y + 1, player.location.z + z ))
                    }
                }
            }
        }
    }

    @EventHandler
    fun updateItem(event: PlayerJoinEvent) {
        UpdateUtils.updateItem(event.player, isFearMaskKey, asHelmetItem().itemMeta)
    }

}
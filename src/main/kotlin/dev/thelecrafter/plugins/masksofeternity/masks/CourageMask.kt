package dev.thelecrafter.plugins.masksofeternity.masks

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import dev.thelecrafter.plugins.masksofeternity.MasksOfEternityPlugin
import dev.thelecrafter.plugins.masksofeternity.util.ComponentColors
import dev.thelecrafter.plugins.masksofeternity.util.ItemUtil
import dev.thelecrafter.plugins.masksofeternity.util.PlayerHeadUtil
import dev.thelecrafter.plugins.masksofeternity.util.UpdateUtils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*

class CourageMask: Listener {
    
    companion object {
        private val isCourageMaskKey: NamespacedKey = NamespacedKey(MasksOfEternityPlugin.getInstance, "isCourageMask")

        fun asHelmetItem(): ItemStack {
            val item: ItemStack = PlayerHeadUtil.getCustomTextureHead(MaskSettings.CourageMask)
            val meta: ItemMeta = item.itemMeta
            meta.displayName(Component.text("Mask of Courage").color(ComponentColors.RED.textColor).decoration(TextDecoration.ITALIC, false))
            meta.lore(listOf(
                Component.empty(),
                Component.text("Gain the courage to").color(ComponentColors.GRAY.textColor).decoration(TextDecoration.ITALIC, false),
                Component.text("replace life with damage").color(ComponentColors.GRAY.textColor).decoration(TextDecoration.ITALIC, false),
            ))
            val modifiers: Multimap<Attribute, AttributeModifier> = ArrayListMultimap.create()
            modifiers.put(Attribute.GENERIC_MAX_HEALTH, AttributeModifier(UUID.randomUUID(), "health_decrease", -0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD))
            modifiers.put(Attribute.GENERIC_ATTACK_DAMAGE, AttributeModifier(UUID.randomUUID(), "damage_increase", 0.25, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD))
            meta.attributeModifiers = modifiers
            meta.persistentDataContainer.set(isCourageMaskKey, PersistentDataType.STRING, "yeah")
            item.itemMeta = meta
            return item
        }
    }

    @EventHandler
    fun unplaceable(event: PlayerInteractEvent) {
        ItemUtil.setUnplaceable(isCourageMaskKey, event)
    }

    @EventHandler
    fun updateItem(event: PlayerJoinEvent) {
        UpdateUtils.updateItem(event.player, isCourageMaskKey, asHelmetItem().itemMeta)
    }
}
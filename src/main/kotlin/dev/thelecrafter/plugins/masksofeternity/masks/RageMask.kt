package dev.thelecrafter.plugins.masksofeternity.masks

import dev.thelecrafter.plugins.masksofeternity.MasksOfEternityPlugin
import dev.thelecrafter.plugins.masksofeternity.util.ComponentColors
import dev.thelecrafter.plugins.masksofeternity.util.PlayerHeadUtil
import dev.thelecrafter.plugins.masksofeternity.util.Settings
import dev.thelecrafter.plugins.masksofeternity.util.UpdateUtils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class RageMask: Listener {

    companion object {
        private val isRageMaskKey: NamespacedKey = NamespacedKey(MasksOfEternityPlugin.getInstance, "isRageMask")

        private val cooldownedPlayers: MutableList<Player> = mutableListOf()

        fun asHelmetItem(): ItemStack {
            val item: ItemStack = PlayerHeadUtil.getCustomTextureHead(MaskSettings.RageMask)
            val meta: ItemMeta = item.itemMeta
            meta.displayName(Component.text("Mask of Rage").color(ComponentColors.GREEN.textColor).decoration(TextDecoration.ITALIC, false))
            meta.lore(listOf(
                Component.empty(),
                Component.text("Gain the raging power if").color(ComponentColors.GRAY.textColor).decoration(TextDecoration.ITALIC, false),
                Component.text("you are wounded").color(ComponentColors.GRAY.textColor).decoration(TextDecoration.ITALIC, false)
            ))
            meta.persistentDataContainer.set(isRageMaskKey, PersistentDataType.STRING, "yeah")
            item.itemMeta = meta
            return item
        }

        fun timeOutPlayer(player: Player) {
            cooldownedPlayers.add(player)
            object : BukkitRunnable(){
                override fun run() {
                    cooldownedPlayers.remove(player)
                }
            }.runTaskLaterAsynchronously(MasksOfEternityPlugin.getInstance, 400)
        }
    }

    @EventHandler
    fun onHealthChange(event: EntityDamageEvent) {
        if (event.entity is Player) {
            val player: Player = event.entity as Player
            if (player.inventory.helmet != null) {
                if (player.inventory.helmet!!.hasItemMeta()) {
                    if (player.inventory.helmet!!.itemMeta.persistentDataContainer.has(isRageMaskKey, PersistentDataType.STRING)) {
                        if (player.health - event.finalDamage < player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue) {
                            if (!cooldownedPlayers.contains(player)) {
                                timeOutPlayer(player)
                                player.sendMessage(Settings.PREFIX.append(Component.text(" Activated your rage ability.").color(ComponentColors.YELLOW.textColor)))
                                player.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 1, false, false, true))
                                player.world.spawnParticle(Particle.TOTEM, player.location.clone().add(0.0, player.height / 2, 0.0), 25)
                                player.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.MASTER, 20f, 1f)
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    fun updateItem(event: PlayerJoinEvent) {
        UpdateUtils.updateItem(event.player, isRageMaskKey, asHelmetItem().itemMeta)
    }

}
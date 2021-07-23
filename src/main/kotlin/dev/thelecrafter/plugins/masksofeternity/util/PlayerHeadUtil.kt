package dev.thelecrafter.plugins.masksofeternity.util

import org.bukkit.inventory.ItemStack
import org.bukkit.Material
import org.bukkit.inventory.meta.SkullMeta
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import java.util.UUID
import java.lang.IllegalArgumentException
import java.lang.IllegalAccessException
import java.lang.NoSuchFieldException
import java.lang.SecurityException
import java.lang.reflect.Field

object PlayerHeadUtil {
    fun getCustomTextureHead(value: String?): ItemStack {
        val head = ItemStack(Material.PLAYER_HEAD, 1)
        val meta = head.itemMeta as SkullMeta
        val profile = GameProfile(UUID.randomUUID(), "")
        profile.properties.put("textures", Property("textures", value))
        var profileField: Field? = null
        try {
            profileField = meta.javaClass.getDeclaredField("profile")
            profileField.isAccessible = true
            profileField[meta] = profile
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        head.itemMeta = meta
        return head
    }
}
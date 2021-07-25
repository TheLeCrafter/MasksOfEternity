package dev.thelecrafter.plugins.masksofeternity.util

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.*

open class DoorAnimation(locations: MutableList<Location>) {
    private val locations: MutableList<Location> = locations

    open fun openDoor() {
        for (location in locations) {
            location.block.blockData = Bukkit.createBlockData(Material.AIR)
            val builder = ParticleBuilder(Particle.BLOCK_DUST)
            builder.data(Bukkit.createBlockData(Material.COBBLED_DEEPSLATE))
            builder.location(location)
            builder.receivers(10)
            builder.count(20)
            builder.spawn()
        }
        for (player in locations.first().getNearbyPlayers(35.0)) {
            player.playSound(player.eyeLocation, Sound.BLOCK_LARGE_AMETHYST_BUD_BREAK, SoundCategory.MASTER,100f, 1f)
        }
    }

    open fun closeDoor() {
        for (location in locations) {
            location.block.blockData = Bukkit.createBlockData(Material.COBBLED_DEEPSLATE)
        }
        for (player in locations.first().getNearbyPlayers(35.0)) {
            player.playSound(locations.first(), Sound.BLOCK_ANVIL_LAND, SoundCategory.MASTER, 45f, 1f)
        }
    }

}
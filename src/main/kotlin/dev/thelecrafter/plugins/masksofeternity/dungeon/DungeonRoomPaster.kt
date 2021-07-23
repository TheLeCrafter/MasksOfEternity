package dev.thelecrafter.plugins.masksofeternity.dungeon

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import org.bukkit.World

open class DungeonRoomPaster(room: DungeonRoom, world: World, offset: BlockVector3) {

    private val room: DungeonRoom = room
    private val world: World = world
    private val offset: BlockVector3 = offset

    open fun paste() {
        room.getSchematic().paste(BukkitAdapter.adapt(world), offset)
    }

}
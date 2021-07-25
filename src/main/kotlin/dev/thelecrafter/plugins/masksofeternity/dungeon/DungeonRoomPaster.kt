package dev.thelecrafter.plugins.masksofeternity.dungeon

import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.function.operation.Operation
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import org.bukkit.World

open class DungeonRoomPaster(room: DungeonRoom, world: World, offset: BlockVector3) {

    private val room: DungeonRoom = room
    private val world: World = world
    private val offset: BlockVector3 = offset

    open fun paste() {
        val editSession: EditSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world))
        val operation: Operation = ClipboardHolder(room.getSchematic())
            .createPaste(editSession)
            .to(offset)
            .build()
        Operations.complete(operation)
    }

}
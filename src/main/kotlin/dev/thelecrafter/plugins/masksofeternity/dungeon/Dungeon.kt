package dev.thelecrafter.plugins.masksofeternity.dungeon

import com.sk89q.worldedit.math.BlockVector3
import dev.thelecrafter.plugins.masksofeternity.MasksOfEternityPlugin
import dev.thelecrafter.plugins.masksofeternity.util.FileUtil
import dev.thelecrafter.plugins.masksofeternity.util.Treasure
import dev.thelecrafter.plugins.masksofeternity.util.VoidGenerator
import org.bukkit.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.jetbrains.annotations.NotNull
import java.io.File
import java.util.*
import kotlin.random.Random

open class Dungeon: Listener {
    private val treasure: Treasure = randomTreasure()
    private val allUnPlayedRooms: MutableList<DungeonRoom> = MasksOfEternityPlugin.allRooms.toMutableList()
    private lateinit var world: World
    private val uuid: UUID = UUID.randomUUID()

    open fun generateWorld(): World {
        val worldCreator = WorldCreator("EternalTemple_$uuid")
        worldCreator.generator(VoidGenerator())
        worldCreator.environment(World.Environment.CUSTOM)
        val world: World = Bukkit.createWorld(worldCreator)!!
        this.world = world
        MasksOfEternityPlugin.allDungeonWorlds[world] = this
        return world
    }

    open fun nextRoom(): DungeonRoom {
        val room: DungeonRoom = allUnPlayedRooms[Random.nextInt(1, allUnPlayedRooms.size)]
        allUnPlayedRooms.remove(room)
        return room
    }

    open fun pasteNextRoom() {
        val task: BukkitTask = playClickingSoundToAllPlayers()
        val room: DungeonRoom = nextRoom()
        DungeonRoomPaster(room, world, BlockVector3.at(0.0, 20.0, 0.0)).paste()
        // open door
        task.cancel()
    }

    open fun playClickingSoundToAllPlayers(): BukkitTask {
        return object : BukkitRunnable() {
            override fun run() {
                for (player in world.players) {
                    player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, SoundCategory.MASTER, 1f, 1f)
                }
            }
        }.runTaskTimer(MasksOfEternityPlugin.getInstance, 0, 10)
    }

    companion object {
        fun randomTreasure(): Treasure {
            return when(Random.nextInt(1, 100)) {
                in 1..16 -> Treasure.COURAGE_MASK
                in 17..32 -> Treasure.ENLIGHTENMENT_MASK
                in 33..48 -> Treasure.FEAR_MASK
                in 49..64 -> Treasure.HATE_MASK
                in 65..80 -> Treasure.RAGE_MASK
                81 -> Treasure.MAGIC_GAUNTLET
                in 82..85 -> Treasure.GAUNTLET_STONE_RED
                in 86..90 -> Treasure.GAUNTLET_STONE_BLUE
                in 91..95 -> Treasure.GAUNTLET_STONE_GREEN
                else -> Treasure.GAUNTLET_STONE_YELLOW
            }
        }
    }

    @EventHandler
    fun unloadIfEmpty(event: PlayerMoveEvent) {
        if (event.from.world.name.contains("EternalTemple")) {
            if (event.to.world != event.from.world) {
                if (event.from.world.players.isEmpty()) {
                    val zipFile = File(Bukkit.getWorldContainer(), event.from.world.worldFolder.name + ".zip")
                    val worldFile = File(Bukkit.getWorldContainer(), event.from.world.worldFolder.name)
                    Bukkit.unloadWorld(event.from.world, true)
                    FileUtil.zipFiles(zipFile, worldFile)
                }
            }
        }
    }

}
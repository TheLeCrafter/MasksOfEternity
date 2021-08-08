package dev.thelecrafter.plugins.masksofeternity

import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import dev.thelecrafter.plugins.masksofeternity.commands.CloseDoorCommand
import dev.thelecrafter.plugins.masksofeternity.commands.EternalItemCommand
import dev.thelecrafter.plugins.masksofeternity.commands.OpenDoorCommand
import dev.thelecrafter.plugins.masksofeternity.dungeon.Dungeon
import dev.thelecrafter.plugins.masksofeternity.dungeon.DungeonRoom
import dev.thelecrafter.plugins.masksofeternity.events.EventCollector
import dev.thelecrafter.plugins.masksofeternity.masks.HateMask
import dev.thelecrafter.plugins.masksofeternity.util.DoorAnimation
import dev.thelecrafter.plugins.masksofeternity.util.DungeonRoomConfigsHandler
import org.apache.commons.io.FileUtils
import org.bukkit.World
import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class MasksOfEternityPlugin : JavaPlugin() {

    companion object {
        lateinit var getInstance: Plugin
        val allRooms: MutableList<DungeonRoom> = mutableListOf()
        val allDungeonWorlds: MutableMap<World, Dungeon> = mutableMapOf()
        lateinit var startRoomSchematic: Clipboard
        lateinit var treasureRoomDoor: DoorAnimation
        lateinit var adventureRoomDoor: DoorAnimation
    }

    override fun onEnable() {
        getInstance = this
        EventCollector.addAllEvents()
        registerAllCommands()
        initSchematics()
        HateMask.initEffects()
    }

    private fun registerAllCommands() {
        registerSingleCommand("eternalitem", EternalItemCommand(), EternalItemCommand())
        registerSingleCommand("opendoor", OpenDoorCommand(), OpenDoorCommand())
        registerSingleCommand("closedoor", CloseDoorCommand(), CloseDoorCommand())
    }

    private fun registerSingleCommand(command: String, commandExecutor: CommandExecutor, tabCompleter: TabCompleter) {
        getCommand(command)?.setExecutor(commandExecutor)
        getCommand(command)?.tabCompleter = tabCompleter
    }

    private fun initSchematics() {
        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }
        loadStartRoomSchematic()
        val schematics: List<String> = listOf()
        for (schematic in schematics) {
            loadRoomSchematic(schematic, getResource("schematics/$schematic.schem")!!, getResource("schematics/$schematic.yml")!!)
        }
    }

    private fun loadStartRoomSchematic() {
        val schematic = File(dataFolder, "start.schem")
        FileUtils.copyInputStreamToFile(getResource("schematics/start.schem"), schematic)
        val clipboardFormat: ClipboardFormat = ClipboardFormats.findByFile(schematic)!!
        startRoomSchematic = clipboardFormat.getReader(getResource("schematics/start.schem")).read()
        schematic.delete()
        // Add animations
    }

    private fun loadRoomSchematic(name: String, schematic: InputStream, config: InputStream) {
        val schematicFile = File(dataFolder, "$name.schem")
        val configFile = File(dataFolder, "$name.yml")
        FileUtils.copyInputStreamToFile(schematic, schematicFile)
        FileUtils.copyInputStreamToFile(config, configFile)
        val clipboardFormat: ClipboardFormat = ClipboardFormats.findByFile(schematicFile)!!
        val clipboard: Clipboard = clipboardFormat.getReader(schematic).read()
        allRooms.add(DungeonRoom(clipboard, DungeonRoomConfigsHandler(configFile).setup().getConfig()))
        schematicFile.delete()
        configFile.delete()
    }
}
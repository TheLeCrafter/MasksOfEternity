package dev.thelecrafter.plugins.masksofeternity

import dev.thelecrafter.plugins.masksofeternity.commands.EternalItemCommand
import dev.thelecrafter.plugins.masksofeternity.dungeon.Dungeon
import dev.thelecrafter.plugins.masksofeternity.dungeon.DungeonRoom
import dev.thelecrafter.plugins.masksofeternity.events.EventCollector
import dev.thelecrafter.plugins.masksofeternity.masks.HateMask
import org.apache.commons.io.FileUtils
import org.bukkit.World
import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class MasksOfEternityPlugin : JavaPlugin() {

    companion object {
        lateinit var getInstance: Plugin
        val allRooms: MutableList<DungeonRoom> = mutableListOf()
        val allDungeonWorlds: MutableMap<World, Dungeon> = mutableMapOf()
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
    }

    private fun registerSingleCommand(command: String, commandExecutor: CommandExecutor, tabCompleter: TabCompleter) {
        getCommand(command)?.setExecutor(commandExecutor)
        getCommand(command)?.tabCompleter = tabCompleter
    }

    private fun initSchematics() {
        // Coming soon
        /*for (file in schematicDirectory.listFiles()) {
            if (file.endsWith(".schem")) {
                logger.info("Loaded schematic ${file.name}")
                val configFile = File(file.parent, file.name.replace(".schem", ".yml"))
                if (configFile.exists()) {
                    val clipboardFormat: ClipboardFormat = ClipboardFormats.findByAlias("schematic")!!
                    val clipboard: Clipboard = clipboardFormat.load(file)
                    allRooms.add(DungeonRoom(clipboard, DungeonRoomConfigsHandler(configFile).setup().getConfig()))
                }
            }
        }*/
    }
}
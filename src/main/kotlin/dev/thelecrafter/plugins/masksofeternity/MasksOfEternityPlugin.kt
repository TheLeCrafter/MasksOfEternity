package dev.thelecrafter.plugins.masksofeternity

import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import org.bukkit.plugin.java.JavaPlugin
import dev.thelecrafter.plugins.masksofeternity.commands.EternalItemCommand
import dev.thelecrafter.plugins.masksofeternity.dungeon.Dungeon
import dev.thelecrafter.plugins.masksofeternity.dungeon.DungeonRoom
import dev.thelecrafter.plugins.masksofeternity.events.EventCollector
import dev.thelecrafter.plugins.masksofeternity.masks.HateMask
import dev.thelecrafter.plugins.masksofeternity.util.DungeonRoomConfigsHandler
import org.bukkit.World
import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.Plugin
import java.io.File

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
        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }
        if (dataFolder.listFiles() != null) {
            for (file in dataFolder.listFiles()) {
                if (file.endsWith(".schem")) {
                    val configFile = File(file.parent, file.name.replace(".schem", ".yml"))
                    if (configFile.exists()) {
                        val clipboardFormat: ClipboardFormat = ClipboardFormats.findByFile(file)!!
                        val clipboard: Clipboard = clipboardFormat.load(file)
                        allRooms.add(DungeonRoom(clipboard, DungeonRoomConfigsHandler(configFile).setup().getConfig()))
                    }
                }
            }
        }
    }
}
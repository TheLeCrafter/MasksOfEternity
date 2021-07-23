package dev.thelecrafter.plugins.masksofeternity.dungeon

import com.sk89q.worldedit.extent.clipboard.Clipboard
import org.bukkit.configuration.file.YamlConfiguration

open class DungeonRoom(schematic: Clipboard, configFile: YamlConfiguration) {

    private val schematic: Clipboard = schematic
    private val configFile: YamlConfiguration = configFile

    open fun getSchematic(): Clipboard {
        return schematic
    }

    open fun getConfigFile(): YamlConfiguration {
        return configFile
    }

}
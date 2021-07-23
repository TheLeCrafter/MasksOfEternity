package dev.thelecrafter.plugins.masksofeternity.util

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

open class DungeonRoomConfigsHandler(file: File) {
    private val file: File = file
    private lateinit var config: YamlConfiguration

    open fun setup(): DungeonRoomConfigsHandler {
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (err: IOException) {
                err.printStackTrace()
            }
        }

        config = YamlConfiguration.loadConfiguration(file)
        return this
    }

    open fun getConfig(): YamlConfiguration {
        return config
    }

    open fun saveToFile() {
        try {
            config.save(file)
        } catch (err: IOException) {
            err.printStackTrace()
        }
    }

    open fun reloadFile() {
        config = YamlConfiguration.loadConfiguration(file)
    }

}
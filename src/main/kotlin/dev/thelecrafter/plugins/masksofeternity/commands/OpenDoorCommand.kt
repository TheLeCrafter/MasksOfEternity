package dev.thelecrafter.plugins.masksofeternity.commands

import dev.thelecrafter.plugins.masksofeternity.util.DoorAnimation
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class OpenDoorCommand: CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val locations: MutableList<Location> = mutableListOf(
                Location(sender.world, -59.5, 117.5, -69.5),
                Location(sender.world, -59.5, 117.5, -68.5),
                Location(sender.world, -59.5, 117.5, -67.5),
                Location(sender.world, -59.5, 118.5, -69.5),
                Location(sender.world, -59.5, 118.5, -68.5),
                Location(sender.world, -59.5, 118.5, -67.5),
                Location(sender.world, -59.5, 119.5, -69.5),
                Location(sender.world, -59.5, 119.5, -68.5),
                Location(sender.world, -59.5, 119.5, -67.5),
                Location(sender.world, -59.5, 120.5, -69.5),
                Location(sender.world, -59.5, 120.5, -68.5),
                Location(sender.world, -59.5, 120.5, -67.5))
            val animation = DoorAnimation(locations)
            animation.openDoor()
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        return mutableListOf()
    }
}
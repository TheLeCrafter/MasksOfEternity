package dev.thelecrafter.plugins.masksofeternity.commands

import dev.thelecrafter.plugins.masksofeternity.masks.*
import dev.thelecrafter.plugins.masksofeternity.util.ComponentColors
import dev.thelecrafter.plugins.masksofeternity.util.Settings
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GetMaskCommand: CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (args.size == 1) {
                when(args[0]) {
                    "rage" -> sender.inventory.addItem(RageMask.asHelmetItem())
                    "hate" -> sender.inventory.addItem(HateMask.asHelmetItem())
                    "enlightenment" -> sender.inventory.addItem(EnlightenmentMask.asHelmetItem())
                    "fear" -> sender.inventory.addItem(FearMask.asHelmetItem())
                    "courage" -> sender.inventory.addItem(CourageMask.asHelmetItem())
                    else -> sender.sendMessage(Settings.PREFIX.append(Component.text(" This mask either doesn't exist or isn't currently implemented.").color(ComponentColors.RED.textColor)))
                }
            }
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        val tabComplete: MutableList<String> = mutableListOf()
        val firstTabCompletion: List<String> = listOf("rage", "hate", "enlightenment", "fear", "courage")

        if (args.size == 1) {
            for (completion in firstTabCompletion) {
                if (completion.contains(args[0])) tabComplete.add(completion)
            }
        }

        return tabComplete
    }

}
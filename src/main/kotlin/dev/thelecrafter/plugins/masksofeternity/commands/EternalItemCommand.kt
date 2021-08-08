package dev.thelecrafter.plugins.masksofeternity.commands

import dev.thelecrafter.plugins.masksofeternity.gauntlet.BaseGauntletStone
import dev.thelecrafter.plugins.masksofeternity.gauntlet.GauntletStones
import dev.thelecrafter.plugins.masksofeternity.gauntlet.MagicGauntlet
import dev.thelecrafter.plugins.masksofeternity.masks.*
import dev.thelecrafter.plugins.masksofeternity.util.ComponentColors
import dev.thelecrafter.plugins.masksofeternity.util.Settings
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class EternalItemCommand: CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (args.size == 2) {
                when(args[0]) {
                    "mask" -> when(args[1]) {
                        "rage" -> sender.inventory.addItem(RageMask.asHelmetItem())
                        "hate" -> sender.inventory.addItem(HateMask.asHelmetItem())
                        "enlightenment" -> sender.inventory.addItem(EnlightenmentMask.asHelmetItem())
                        "fear" -> sender.inventory.addItem(FearMask.asHelmetItem())
                        "courage" -> sender.inventory.addItem(CourageMask.asHelmetItem())
                        else -> sender.sendMessage(Settings.PREFIX.append(Component.text(" This mask either doesn't exist or isn't currently implemented.").color(ComponentColors.RED.textColor)))
                    }
                    "gauntlet" -> when(args[1]) {
                        "magic_gauntlet" -> sender.inventory.addItem(MagicGauntlet.asHandheldItem(mutableListOf()))
                        "red_stone" -> sender.inventory.addItem(BaseGauntletStone.asHandheldItem(GauntletStones.RED_GEMSTONE))
                        "yellow_stone" -> sender.inventory.addItem(BaseGauntletStone.asHandheldItem(GauntletStones.YELLOW_GEMSTONE))
                        "green_stone" -> sender.inventory.addItem(BaseGauntletStone.asHandheldItem(GauntletStones.GREEN_GEMSTONE))
                        "blue_stone" -> sender.inventory.addItem(BaseGauntletStone.asHandheldItem(GauntletStones.BLUE_GEMSTONE))
                        else -> sender.sendMessage(Settings.PREFIX.append(Component.text(" This gauntlet item either doesn't exist or isn't currently implemented.").color(ComponentColors.RED.textColor)))
                    }
                }
            }
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        val tabComplete: MutableList<String> = mutableListOf()
        val firstTabCompletion: List<String> = listOf("mask", "gauntlet")
        var secondTabCompletion: MutableList<String>

        if (args.size == 1) {
            for (completion in firstTabCompletion) {
                if (completion.contains(args[0])) tabComplete.add(completion)
            }
        }

        if (args.size == 2) {
            if (args[0] == "mask") {
                secondTabCompletion = mutableListOf("rage", "hate", "enlightenment", "fear", "courage")
                for (completion in secondTabCompletion) {
                    if (completion.contains(args[1])) tabComplete.add(completion)
                }
            } else if (args[0] == "gauntlet") {
                secondTabCompletion = mutableListOf("magic_gauntlet", "red_stone", "yellow_stone", "green_stone", "blue_stone")
                for (completion in secondTabCompletion) {
                    if (completion.contains(args[1])) tabComplete.add(completion)
                }
            }
        }

        return tabComplete
    }

}
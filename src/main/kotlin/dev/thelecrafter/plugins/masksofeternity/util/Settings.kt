package dev.thelecrafter.plugins.masksofeternity.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextDecoration

object Settings {

    val PREFIX: TextComponent = Component.text("[").color(ComponentColors.GRAY.textColor)
        .append(Component.text("MASKS").color(ComponentColors.RED.textColor).decorate(TextDecoration.BOLD))
        .append(Component.text(" OF").color(ComponentColors.GREEN.textColor).decorate(TextDecoration.BOLD))
        .append(Component.text(" ETERNITY").color(ComponentColors.YELLOW.textColor).decorate(TextDecoration.BOLD))
        .append(Component.text("]").color(ComponentColors.GRAY.textColor))

}
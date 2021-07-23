package dev.thelecrafter.plugins.masksofeternity.events

import dev.thelecrafter.plugins.masksofeternity.MasksOfEternityPlugin
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.reflections.Reflections

object EventCollector {
    fun addAllEvents() {
        addEventsWithReflections("dev.thelecrafter.plugins.masksofeternity")
    }

    private fun addEventsWithReflections(topLevelPackage: String) {
        val reflections = Reflections(topLevelPackage)
        val classes: Set<Class<out Listener>> = reflections.getSubTypesOf(Listener::class.java)
        for (clazz in classes) {
            Bukkit.getPluginManager().registerEvents(clazz.getDeclaredConstructor().newInstance(), MasksOfEternityPlugin.getInstance)
        }
    }
}
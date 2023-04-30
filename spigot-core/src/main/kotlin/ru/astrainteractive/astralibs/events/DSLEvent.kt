package ru.astrainteractive.astralibs.events

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.plugin.Plugin

/**
 * With this object you will no longer need to create interfaces for your bukkit event listeners
 */
object DSLEvent {
    inline operator fun <reified T : Event> invoke(
        eventListener: EventListener,
        plugin: Plugin,
        eventPriority: EventPriority = EventPriority.NORMAL,
        crossinline block: (T) -> Unit
    ) = DSLEvent(T::class.java, eventListener, plugin, eventPriority) {
        (it as? T)?.let(block)
    }

    operator fun <T : Event> invoke(
        clazz: Class<T>,
        eventController: EventListener,
        plugin: Plugin,
        eventPriority: EventPriority,
        block: (Event) -> Unit
    ) = plugin.server.pluginManager.registerEvent(
        clazz,
        eventController,
        eventPriority,
        { _, event -> block.invoke(event) },
        plugin
    )
}

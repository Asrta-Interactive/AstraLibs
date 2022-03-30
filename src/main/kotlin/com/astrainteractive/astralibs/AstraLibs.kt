package com.astrainteractive.astralibs

import com.astrainteractive.astralibs.async.AsyncHelper
import com.astrainteractive.astralibs.observer.LifecycleOwner
import com.astrainteractive.astralibs.observer.MutableLiveData
import kotlinx.coroutines.cancel
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

/**
 * Main instance of AstraLibs
 * You can see AstraTemplate for examples of use
 */
object AstraLibs : LifecycleOwner {
    private lateinit var plugin: JavaPlugin

    /**
     * Instance of current plugin
     * @return instance of plugin
     */
    val instance: JavaPlugin
        get() = plugin

    /**
     * Initializer for AstraLibs
     */
    fun create(plugin: JavaPlugin) {
        AstraLibs.plugin = plugin
    }


    private val activeTasksList = mutableMapOf<Long, BukkitTask>()

    /**
     * Add task to list
     */
    @Deprecated("Use coroutines instead")
    fun onBukkitTaskAdded(id: Long, taskRef: BukkitTask) {
        activeTasksList[id] = taskRef
    }

    /**
     * Disable task and remove from list
     */
    @Deprecated("Use coroutines instead")
    fun onBukkitTaskEnded(id: Long) {
        val task = activeTasksList[id]
        task?.cancel()
        activeTasksList.remove(id)
    }

    /**
     * Clear all background tasks
     */
    fun clearAllTasks() {
        activeTasksList.forEach { (_, task) ->
            catching {
                task.cancel()
            }
        }
        activeTasksList.clear()
        catching { AsyncHelper.cancel() }

    }
}
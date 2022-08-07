package com.astrainteractive.astralibs.utils

import com.astrainteractive.astralibs.events.DSLEvent
import com.astrainteractive.astralibs.events.EventManager
import com.astrainteractive.astralibs.events.GlobalEventManager
import com.astrainteractive.astralibs.utils.catching
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import net.minecraft.network.protocol.Packet
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerRespawnEvent
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.HashMap

/**
 * This packet reader class allows you to read minecraft packets without ProtocolLib
 */
abstract class AstraPacketReader<T : Packet<*>> {
    private val channels: MutableMap<UUID, Channel> = HashMap()

    /**
     * You can reassign eventManager to another event
     */
    open val eventManager: EventManager = GlobalEventManager

    /**
     * In plugin initialization you should enable it
     */
    fun onEnable() {
        onDisable()
        Bukkit.getOnlinePlayers().forEach { inject(it) }
        val joinEvent = DSLEvent.event(PlayerJoinEvent::class.java, eventManager) {
            inject(it.player)
        }
        val respawnEvent = DSLEvent.event(PlayerRespawnEvent::class.java, eventManager) {
            inject(it.player)
        }
        val quitEvent = DSLEvent.event(PlayerQuitEvent::class.java, eventManager) {
            deInject(it.player.uniqueId)
        }
        val deathEvent = DSLEvent.event(PlayerDeathEvent::class.java, eventManager) {
            deInject(it.player.uniqueId)
        }
    }

    /**
     * Need to disable so all channels will be cleared
     */
    fun onDisable() {
        channels.keys.forEach { deInject(it) }
        channels.clear()
    }

    /**
     * Here you should provide a channel for your current version
     * Example, for v1_19_R1 it's (this as CraftPlayer).handle.b.b.m
     */
    abstract val Player.provideChannel: Channel

    /**
     * Example: PacketPlayInUseEntity::class.java
     */
    abstract val clazz: Class<out T>

    /**
     * In your plugin you probably will need only this function
     * It will return played packet
     */
    abstract fun readPacket(player: Player, packet: T)

    fun decoder(player: Player) = object : MessageToMessageDecoder<T>(clazz) {
        override fun decode(ctx: ChannelHandlerContext?, packet: T?, arg: MutableList<Any>?) {
            packet ?: return
            arg ?: return
            ctx ?: return
            arg.add(packet as Any)
            readPacket(player, packet)
        }
    }

    fun inject(player: Player) {
        val channel = player.provideChannel
        channels[player.uniqueId] = channel
        if (channel.pipeline().get("PacketInjector") != null) return
        channel.pipeline().addAfter("decoder", "PacketInjector", decoder(player))
    }

    fun deInject(uuid: UUID) {
        val channel = channels[uuid] ?: return
        catching { channel.pipeline().remove("PacketInjector") }
        channels.remove(uuid)
    }

    @Deprecated("Use ReflectionUtil instead", ReplaceWith("ReflectionUtil"))
    fun getClassFieldValue(instance: Any, name: String): Any? = catching(false) {
        val field: Field = instance.javaClass.getDeclaredField(name)
        field.isAccessible = true
        val result = field.get(instance)
        field.isAccessible = false
        result
    }

}
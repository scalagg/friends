package gg.scala.friends.handler

import gg.scala.banana.annotate.Subscribe
import gg.scala.banana.message.Message
import gg.scala.banana.subscribe.marker.BananaHandler
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.Constants
import org.bukkit.Bukkit
import java.util.*

/**
 * @author GrowlyX
 * @since 10/17/2021
 */
object FriendRedisHandler : BananaHandler
{

    @Subscribe("network-update")
    fun onConnection(message: Message)
    {
        val targetId = UUID.fromString(message["target-uuid"])

        Bukkit.getOnlinePlayers()
            .mapNotNull { FriendPlayerHandler.find(it) }
            .filter {
                it.friends.containsKey(targetId)
            }.forEach {
                Bukkit.getPlayer(it.uniqueId).sendMessage(
                    "${CC.B_PRI}Friends ${CC.GRAY}${Constants.DOUBLE_ARROW_RIGHT} ${CC.GREEN}${message["target-name"]} ${CC.SEC}${message["type"]}."
                )
            }
    }

    fun buildMessage(
        packet: String, message: Map<String, String>
    ): Message
    {
        return Message(packet).also {
            message.forEach { (key, value) ->
                it[key] = value
            }
        }
    }
}

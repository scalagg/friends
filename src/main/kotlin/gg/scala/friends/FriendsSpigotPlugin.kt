package gg.scala.friends

import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.friends.handler.FriendPlayerHandler
import me.lucko.helper.Events
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @author GrowlyX
 * @since 10/17/2021
 */
class FriendsSpigotPlugin : ExtendedScalaPlugin()
{

    override fun enable()
    {
        loadResources()

        FriendPlayerHandler.initialLoad()
    }

    private fun loadResources()
    {
        Events.subscribe(AsyncPlayerPreLoginEvent::class.java).handler {
            FriendPlayerHandler.loadOrCreate(it.uniqueId, it.name)
        }

        Events.subscribe(PlayerJoinEvent::class.java).handler {
            FriendPlayerHandler.lateLoad(it.player.uniqueId)
        }
    }
}

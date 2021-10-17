package gg.scala.friends

import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.friends.handler.FriendPlayerHandler

/**
 * @author GrowlyX
 * @since 10/17/2021
 */
class FriendsSpigotPlugin : ExtendedScalaPlugin()
{

    override fun enable()
    {
        FriendPlayerHandler.initialLoad()
    }
}

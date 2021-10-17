package gg.scala.friends.player

import gg.scala.common.Savable
import gg.scala.friends.handler.FriendPlayerHandler
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * @author GrowlyX
 * @since 10/17/2021
 */
data class FriendPlayer(
    val uniqueId: UUID,
    var username: String
) : Savable
{
    val friends = mutableMapOf<UUID, String>()

    override fun save(): CompletableFuture<Void>
    {
        return FriendPlayerHandler.save(this)
    }
}

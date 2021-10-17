package gg.scala.friends.handler

import com.solexgames.datastore.commons.connection.impl.mongo.UriMongoConnection
import com.solexgames.datastore.commons.layer.impl.MongoStorageLayer
import com.solexgames.datastore.commons.storage.impl.MongoStorageBuilder
import gg.scala.friends.player.FriendPlayer
import gg.scala.lemon.Lemon
import gg.scala.lemon.player.LemonPlayer
import net.evilblock.cubed.acf.bukkit.contexts.OnlinePlayer
import net.evilblock.cubed.serializers.Serializers
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * @author GrowlyX
 * @since 10/17/2021
 */
object FriendPlayerHandler
{
    private val handle = mutableMapOf<UUID, FriendPlayer>()
    private lateinit var connection: MongoStorageLayer<FriendPlayer>

    fun initialLoad()
    {
        val mongoConnection = UriMongoConnection(Lemon.instance.mongoConfig.uri)
        val database = Lemon.instance.mongoConfig.database

       connection = MongoStorageBuilder<FriendPlayer>()
            .setDatabase(database).setCollection("friend_players")
            .setConnection(mongoConnection).setType(FriendPlayer::class.java).build()

       connection.supplyWithCustomGson(Serializers.gson)
    }

    fun save(friendPlayer: FriendPlayer): CompletableFuture<Void>
    {
        return connection.saveEntry(friendPlayer.uniqueId.toString(), friendPlayer)
    }

    fun forget(friendPlayer: FriendPlayer)
    {
        handle.remove(
            friendPlayer.uniqueId
        )?.save()
    }

    fun loadOrCreate(uuid: UUID, username: String): CompletableFuture<Void>
    {
        return connection.fetchEntryByKey(uuid.toString()).thenAccept {
            if (it != null) {
                handle[it.uniqueId] = it
                it.username = username
            } else {
                handle[uuid] = FriendPlayer(
                    uuid, username
                )
            }
        }
    }

    fun find(uuid: UUID): FriendPlayer?
    {
        return handle[uuid]
    }

    fun find(player: Player): FriendPlayer?
    {
        return handle[player.uniqueId]
    }

    fun find(onlinePlayer: OnlinePlayer): FriendPlayer?
    {
        return handle[onlinePlayer.player.uniqueId]
    }
}

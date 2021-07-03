package dev.gimme.gimmemc

import dev.gimme.command.channel.TextCommandChannel
import dev.gimme.command.manager.CommandManager
import dev.gimme.command.mc.ChatCommandChannel
import dev.gimme.command.parameter.ParameterTypes
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.advancement.Advancement
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

/**
 * A Spigot plugin with a simple setup.
 */
abstract class GimmePlugin : JavaPlugin() {

    /**
     * The default command manager where custom commands can be registered.
     */
    lateinit var commandManager: CommandManager<String?>
        private set

    /**
     * The command channel through which command input and output is handled.
     */
    lateinit var channel: TextCommandChannel
        private set

    init {
        registerBaseParameterTypes()
    }

    final override fun onEnable() {
        channel = ChatCommandChannel(this, includeConsoleListener = false)
        channel.enable()
        commandManager = channel.commandManager

        onStart()
    }

    final override fun onDisable() {
        channel.disable()

        onStop()
    }

    /**
     * Performs plugin startup logic.
     */
    protected abstract fun onStart()

    /**
     * Performs plugin shutdown logic.
     */
    protected abstract fun onStop()

    @Suppress("RemoveExplicitTypeArguments")
    private fun registerBaseParameterTypes() {
        ParameterTypes.register<Player>(values = { server.onlinePlayers.map { it.name }.toSet() }) {
            server.getPlayerExact(it)
        }

        ParameterTypes.register<World>(values = { server.worlds.map { it.name }.toSet() }) {
            server.getWorld(it)
        }

        ParameterTypes.register<Advancement>(values = {
            server.advancementIterator().asSequence().map { it.key.toString() }.toSet()
        }) {
            val pair = it.split(":", limit = 2)
            @Suppress("DEPRECATION")
            if (pair.size != 2) null else server.getAdvancement(NamespacedKey(pair[0], pair[1]))
        }
    }
}

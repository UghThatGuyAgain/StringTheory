package net.draycia.stringtheory.commands

import net.draycia.stringtheory.utils.translateColors
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.World.Environment.*

class StUsersCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("stringtheory.command.stusers")) {
            return true
        }

        val header = "&a&l&m---------[--&7 User List &a&l&m--]---------".translateColors()

        sender.sendMessage(header)

        if (!args.contains("-c")) {
            sender.sendMessage("")
        }

        for (world in Bukkit.getWorlds()) {
            // Don't show worlds with no players in them
            if (world.playerCount == 0) {
                continue
            }

            // Color the world name according to its environment
            val message = when (world.environment) {
                NORMAL -> { "${ChatColor.GREEN}${world.name}" }
                NETHER -> { "${ChatColor.RED}${world.name}" }
                else -> { "${ChatColor.YELLOW}${world.name}" }
            }

            // Format the player list
            val players = world.players.joinToString("&d, &f".translateColors()) { it.name }

            // And send the message to the player
            sender.sendMessage("$message ${ChatColor.AQUA}- ${ChatColor.WHITE}$players")
        }

        if (!args.contains("-c")) {
            sender.sendMessage("")
        }

        sender.sendMessage(header)

        return true
    }

}
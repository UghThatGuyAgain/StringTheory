package net.draycia.stringtheory.commands

import net.draycia.stringtheory.utils.translateColors
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.World.Environment.*

class StListCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("stringtheory.command.stlist")) {
            return true
        }

        val header = "&b&l&m---------[--&7 World List &b&l&m--]---------".translateColors()

        sender.sendMessage(header)

        if (!args.contains("-c")) {
            sender.sendMessage("")
        }

        var normalWorlds = "${ChatColor.GREEN}NORMAL ${ChatColor.AQUA}- ${ChatColor.WHITE}"
        var netherWorlds = "${ChatColor.RED}NETHER ${ChatColor.AQUA}- ${ChatColor.WHITE}"
        var endWorlds    = "${ChatColor.YELLOW}END      ${ChatColor.AQUA}- ${ChatColor.WHITE}"

        val worlds = Bukkit.getWorlds()

        // Get a comma separated list of all worlds for each environment
        normalWorlds += worlds.filter { it.environment == NORMAL  }.joinToString(", ") { it.name }
        netherWorlds += worlds.filter { it.environment == NETHER  }.joinToString(", ") { it.name }
        endWorlds    += worlds.filter { it.environment == THE_END }.joinToString(", ") { it.name }

        // Send those lists to the player
        sender.sendMessage(normalWorlds)
        sender.sendMessage(netherWorlds)
        sender.sendMessage(endWorlds   )

        if (!args.contains("-c")) {
            sender.sendMessage("")
        }

        sender.sendMessage(header)

        return true
    }
}

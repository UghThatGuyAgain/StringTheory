package net.draycia.stringtheory.commands

import net.draycia.stringtheory.utils.translateColors
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.NumberFormatException

class StTeleportCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            return true
        }

        if (!sender.hasPermission("stringtheory.command.createworld")) {
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("&cNo arguments supplied!".translateColors())
            return true
        }

        val world = Bukkit.getWorld(args[0])
        var location = world?.spawnLocation

        if (world != null) {
            if (args.size == 4) {
                try {
                    val x = args[1].toDouble()
                    val y = args[2].toDouble()
                    val z = args[3].toDouble()

                    location = Location(world, x, y, z)
                } catch (e: NumberFormatException) {
                    sender.sendMessage("&cInvalid coordinates!".translateColors())
                }
            }

            if (location != null) {
                sender.teleport(location)
            } else {
                sender.sendMessage("&cCannot teleport you! Invalid location.".translateColors())
            }
        } else {
            sender.sendMessage("&cThat world does not exist!".translateColors())
        }

        return true
    }

}
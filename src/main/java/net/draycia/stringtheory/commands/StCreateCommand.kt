package net.draycia.stringtheory.commands

import net.draycia.stringtheory.StringTheory
import net.draycia.stringtheory.utils.WorldUtils
import net.draycia.stringtheory.utils.equalsAny
import net.draycia.stringtheory.utils.startsWithAny
import net.draycia.stringtheory.utils.translateColors
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StCreateCommand(private val main: StringTheory) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("stringtheory.command.createworld")) {
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("&cNo arguments supplied!".translateColors())
            return true
        }

        // Ensure the world doesn't already exist
        if (Bukkit.getServer().getWorld(args[0]) != null) {
            sender.sendMessage("&cA world with that name already exists!".translateColors())
            return true
        }

        // Initialize WorldCreator with first arg for simplicity
        var worldCreator: WorldCreator? = null
        var worldName: String?

        for (arg in args) {
            if (arg.startsWithAny("name:", "n:")) {
                worldName = arg.split(":")[1]
                worldCreator = WorldCreator(worldName)
            }
        }

        // If no name was supplied, abort
        if (worldCreator == null) {
            sender.sendMessage("&cA name must be supplied in order to create a world!".translateColors())
            return true
        }

        // Set optional settings
        for (arg in args) {
            // Prepare arguments
            val arguments = arg.split(":")

            if (arguments.size != 2) {
                sender.sendMessage("&cIncorrect argument! [{arg}]".translateColors().replace("{arg}", arg))
                continue
            }

            // Process arguments
            when {
                // Set the seed
                arguments[0].equalsAny("seed", "s") -> {
                    worldCreator.seed(arguments[1].hashCode().toLong())
                }

                // Set the environment
                arguments[0].equalsAny("environment", "env", "e") -> {
                    for (environment in World.Environment.values()) {
                        if (environment.name.equals(arguments[1], true)) {
                            worldCreator.environment(environment)
                        }
                    }
                }

                // Enable/disable structures
                arguments[0].equalsAny("structures", "structure", "st") -> {
                    worldCreator.generateStructures(arguments[1].toBoolean())
                }

                // Set the world type
                arguments[0].equalsAny("worldtype", "type", "wt") -> {
                    val worldType = WorldType.getByName(arguments[1])

                    if (worldType != null) {
                        main.addWorldToList(worldCreator)
                    }
                }
            }
        }

        WorldUtils.loadWorld(main, worldCreator, sender.name)

        return true
    }
}
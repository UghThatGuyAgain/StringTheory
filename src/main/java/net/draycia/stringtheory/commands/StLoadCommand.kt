package net.draycia.stringtheory.commands

import net.draycia.stringtheory.StringTheory
import net.draycia.stringtheory.utils.WorldUtils
import net.draycia.stringtheory.utils.translateColors
import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StLoadCommand(private val main: StringTheory) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("stringtheory.command.stload")) {
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("&cNo arguments supplied!".translateColors())
            return true
        }

        val worldName = args[0]
        val world = Bukkit.getWorld(worldName)

        if (world != null) {
            sender.sendMessage("&cThat world is already loaded!".translateColors())
            return true
        }

        WorldUtils.loadWorld(main, WorldCreator(args[0]), sender.name)

        return true
    }

}
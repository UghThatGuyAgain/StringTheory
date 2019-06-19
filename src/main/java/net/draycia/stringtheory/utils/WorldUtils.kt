package net.draycia.stringtheory.utils

import net.draycia.stringtheory.StringTheory
import org.bukkit.Bukkit
import org.bukkit.WorldCreator

class WorldUtils {

    companion object {
        fun loadWorld(main: StringTheory, worldCreator: WorldCreator, sender: String?) {
            if (sender != null) {
                // Alert staff and players that world creation is about to take place
                for (player in Bukkit.getServer().onlinePlayers) {
                    if (player.hasPermission("stringtheory.alerts.createworld")) {
                        player.sendMessage("&6&l$sender &a&lhas started creation of the world &b&l${worldCreator.name()}&a&l.".translateColors())
                    }
                    else if (player.hasPermission("stringtheory.alerts.lag")) {
                        player.sendMessage("&c&lPotential lag incoming!".translateColors())
                    }
                }
            }

            // Create the world
            val world = worldCreator.createWorld()

            // Alert staff and players that the creation is done
            for (player in Bukkit.getServer().onlinePlayers) {
                if (player.hasPermission("stringtheory.alerts.createworld")) {
                    if (world != null) {
                        player.sendMessage("&a&lCreation of the world &b&l${worldCreator.name()} &a&lhas succeeded!".translateColors())
                    } else {
                        player.sendMessage("&c&lCreation of the world &b&l${worldCreator.name()} &c&lhas failed!".translateColors())
                    }
                }
                else if (player.hasPermission("stringtheory.alerts.lag")) {
                    player.sendMessage("&a&lPotential lag gone!".translateColors())
                }
            }

            if (world != null && sender != null) {
                main.addWorldToList(worldCreator)
            }
        }
    }
}
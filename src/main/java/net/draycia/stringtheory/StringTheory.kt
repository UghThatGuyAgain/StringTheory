package net.draycia.stringtheory

import net.draycia.stringtheory.commands.StCreateCommand
import net.draycia.stringtheory.commands.StListCommand
import net.draycia.stringtheory.commands.StLoadCommand
import net.draycia.stringtheory.commands.StTeleportCommand
import net.draycia.stringtheory.commands.StUsersCommand
import net.draycia.stringtheory.utils.WorldUtils
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class StringTheory : JavaPlugin() {
    private val worldList = ArrayList<String>()

    override fun onEnable() {
        saveDefaultConfig()

        loadWorldList()
        loadWorlds()
        registerCommands()
    }

    override fun onDisable() {
        saveWorldList()
    }

    private fun loadWorldList() {
        // Worlds file
        val worldFile = File(dataFolder, "worlds.txt")

        // Make sure plugin folder exists
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }

        // Check if worlds file exists
        // Read from it if it exists
        // Otherwise just create it and continue
        if (worldFile.exists()) {
            worldList.addAll(worldFile.readLines())
        } else {
            worldFile.createNewFile()
        }
    }

    private fun loadWorlds() {
        for (world in worldList) {
            val args = world.split(";")
            val worldCreator = WorldCreator(args[0])

            worldCreator.apply {
                environment(World.Environment.valueOf(args[1]))
                type(if (args[2] == "DEFAULT") WorldType.NORMAL else WorldType.valueOf(args[2]))
                seed(args[3].toLong())
                generateStructures(args[4].toBoolean())
            }

            WorldUtils.loadWorld(this, worldCreator, null)
        }
    }

    private fun saveWorldList() {
        val worldFile = File(dataFolder, "worlds.txt")

        // Make sure plugin folder exists
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }

        // Check if worlds file exists
        // Read from it if it exists
        // Otherwise just create it and continue
        if (!worldFile.exists()) {
            worldFile.createNewFile()
        } else {
            worldFile.delete()
            worldFile.createNewFile()
        }

        worldFile.printWriter().use { out ->
            worldList.forEach { world ->
                if (!worldFile.readLines().contains(world)) {
                    out.println(world)
                }
            }
        }
    }

    fun addWorldToList(worldCreator: WorldCreator) {
        this.worldList.add("${worldCreator.name()};${worldCreator.environment().name};" +
                            "${worldCreator.type().getName()};${worldCreator.seed()};" +
                            "${worldCreator.generateStructures()}")
    }

    private fun registerCommands() {
        getCommand("stcreate")!!.setExecutor(StCreateCommand(this))
        getCommand("stteleport")!!.setExecutor(StTeleportCommand())
        getCommand("stload")!!.setExecutor(StLoadCommand(this))
        getCommand("stlist")!!.setExecutor(StListCommand())
        getCommand("stusers")!!.setExecutor(StUsersCommand())
    }
}

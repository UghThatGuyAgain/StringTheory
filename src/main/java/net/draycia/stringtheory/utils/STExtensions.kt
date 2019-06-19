package net.draycia.stringtheory.utils

import org.bukkit.ChatColor

fun String.translateColors(): String {
    return ChatColor.translateAlternateColorCodes('&', this)
}

fun String.startsWithAny(vararg pattern: String): Boolean {
    for (str in pattern) {
        if (this.startsWith(str, true)) {
            return true
        }
    }

    return false
}

fun String.equalsAny(vararg pattern: String): Boolean {
    for (str in pattern) {
        if (this.equals(str, true)) {
            return true
        }
    }

    return false
}
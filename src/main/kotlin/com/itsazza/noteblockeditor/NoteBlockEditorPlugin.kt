package com.itsazza.noteblockeditor

import com.itsazza.noteblockeditor.command.NoteBlockMenuCommand
import com.itsazza.noteblockeditor.listener.NoteBlockEventListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class NoteBlockEditorPlugin : JavaPlugin() {
    companion object {
        var instance: NoteBlockEditorPlugin? = null
            private set
    }

    override fun onEnable() {
        instance = this
        getCommand("noteblock")?.setExecutor(NoteBlockMenuCommand)
        Bukkit.getPluginManager().registerEvents(NoteBlockEventListener, this)
    }
}
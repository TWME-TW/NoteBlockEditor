package com.itsazza.noteblockeditor

import com.itsazza.noteblockeditor.command.NoteBlockMenuCommand
import com.itsazza.noteblockeditor.listener.NoteBlockEventListener
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.Instrument
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import java.lang.IllegalArgumentException
import kotlin.collections.HashMap
import kotlin.properties.Delegates

class NoteBlockEditorPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: NoteBlockEditorPlugin
            private set
        var noteBlocksPlusEnabled by Delegates.notNull<Boolean>()
    }

    val instruments = HashMap<Instrument, Material>()

    override fun onEnable() {
        instance = this
        noteBlocksPlusEnabled = Bukkit.getPluginManager().isPluginEnabled("NoteBlocksPlus")

        getCommand("noteblock")?.setExecutor(NoteBlockMenuCommand)
        saveDefaultConfig()
        loadInstruments()
        Bukkit.getPluginManager().registerEvents(NoteBlockEventListener, this)
        Metrics(this, 11176)
    }

    fun getLangString(path: String) : String {
        return config.getString("messages.$path") ?: throw NullPointerException()
    }

    fun loadInstruments() {
        instruments.clear()

        val configInstruments = config.getConfigurationSection("instruments")?.getKeys(false) ?: return
        for (instrumentString in configInstruments) {
            lateinit var material: Material
            lateinit var instrument: Instrument
            val materialString = config.getString("instruments.$instrumentString") ?: continue

            try {
              instrument = Instrument.valueOf(instrumentString)
            } catch (e: IllegalArgumentException) {
                continue
            }

            try {
                material = Material.valueOf(materialString)
            } catch (e: IllegalArgumentException) {
                logger.severe("Could not find material $materialString for instrument $instrumentString")
                continue
            }

            this.instruments[instrument] = material
        }
    }
}
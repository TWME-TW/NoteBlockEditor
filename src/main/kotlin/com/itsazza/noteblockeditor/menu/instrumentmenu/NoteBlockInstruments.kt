package com.itsazza.noteblockeditor.menu.instrumentmenu

import org.bukkit.Instrument
import org.bukkit.Material
import java.lang.IllegalArgumentException

fun getInstrumentMaterial(instrument: Instrument) : Material {
    return when(instrument.name) {
        "PIANO" -> Material.DIRT
        "BASS_GUITAR" -> Material.OAK_PLANKS
        "SNARE_DRUM" -> Material.SAND
        "STICKS"-> Material.GLASS
        "BASS_DRUM" -> Material.STONE
        "BELL" -> Material.GOLD_BLOCK
        "FLUTE" -> Material.CLAY
        "CHIME" -> Material.PACKED_ICE
        "GUITAR" -> Material.WHITE_WOOL
        "XYLOPHONE" -> Material.BONE_BLOCK
        "IRON_XYLOPHONE" -> Material.IRON_BLOCK
        "COW_BELL" -> Material.SOUL_SAND
        "DIDGERIDOO" -> Material.PUMPKIN
        "BIT" -> Material.EMERALD_BLOCK
        "BANJO" -> Material.HAY_BLOCK
        "PLING" -> Material.GLOWSTONE
        else -> throw IllegalArgumentException()
    }
}
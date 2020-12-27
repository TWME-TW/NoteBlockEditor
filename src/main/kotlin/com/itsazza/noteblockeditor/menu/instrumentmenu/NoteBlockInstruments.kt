package com.itsazza.noteblockeditor.menu.instrumentmenu

import org.bukkit.Instrument
import org.bukkit.Material

class NoteBlockInstrument(val name: String, val instrument: Instrument, val material: Material)

val listOfInstruments = listOf (
    NoteBlockInstrument("Piano", Instrument.PIANO ,Material.COARSE_DIRT),
    NoteBlockInstrument("Bass", Instrument.BASS_GUITAR, Material.OAK_LOG),
    NoteBlockInstrument("Snare Drum", Instrument.SNARE_DRUM, Material.SAND),
    NoteBlockInstrument("Click", Instrument.STICKS, Material.GLASS),
    NoteBlockInstrument("Bass Drum", Instrument.BASS_DRUM, Material.STONE),
    NoteBlockInstrument("Bell", Instrument.BELL, Material.GOLD_BLOCK),
    NoteBlockInstrument("Flute", Instrument.FLUTE, Material.CLAY),
    NoteBlockInstrument("Chimes", Instrument.CHIME, Material.PACKED_ICE),
    NoteBlockInstrument("Guitar", Instrument.GUITAR, Material.WHITE_WOOL),
    NoteBlockInstrument("Xylophone", Instrument.XYLOPHONE, Material.BONE_BLOCK),
    NoteBlockInstrument("Iron Xylophone", Instrument.IRON_XYLOPHONE, Material.IRON_BLOCK),
    NoteBlockInstrument("Cow Bell", Instrument.COW_BELL, Material.SOUL_SAND),
    NoteBlockInstrument("Didgeridoo", Instrument.DIDGERIDOO, Material.PUMPKIN),
    NoteBlockInstrument("Square Wave", Instrument.BIT, Material.EMERALD_BLOCK),
    NoteBlockInstrument("Banjo", Instrument.BANJO, Material.HAY_BLOCK),
    NoteBlockInstrument("Electric Piano", Instrument.PLING, Material.GLOWSTONE)
)
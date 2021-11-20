package com.itsazza.noteblockeditor.listener

import com.itsazza.noteblockeditor.NoteBlockEditorPlugin
import org.bukkit.NamespacedKey
import org.bukkit.Note
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.persistence.PersistentDataType

object NoteBlockPlaceListener : Listener {
    @EventHandler
    fun onNoteBlockPlace(event: BlockPlaceEvent) {
        if (event.blockPlaced.blockData !is NoteBlock) return
        if (!event.itemInHand.hasItemMeta()) return
        event.itemInHand.itemMeta!!.persistentDataContainer
            .get(NamespacedKey(NoteBlockEditorPlugin.instance, "noteblock"), PersistentDataType.INTEGER)?.let {
                val noteBlock = event.blockPlaced.blockData as NoteBlock
                noteBlock.note = Note(it)
                event.block.blockData = noteBlock
            }
    }
}
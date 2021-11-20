package com.itsazza.noteblockeditor.util

import com.itsazza.noteblockeditor.NoteBlockEditorPlugin
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun giveNoteBlock(player: Player, value: Int) {
    val block = ItemStack(Material.NOTE_BLOCK, 64)
    val itemMeta = block.itemMeta!!
    itemMeta.persistentDataContainer.set(
        NamespacedKey(NoteBlockEditorPlugin.instance, "noteblock"),
        PersistentDataType.INTEGER,
        value - 1
    )
    itemMeta.setDisplayName("§fNote Block ($value clicks)")
    block.itemMeta = itemMeta
    player.inventory.addItem(block)
}
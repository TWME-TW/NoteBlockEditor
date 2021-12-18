package com.itsazza.noteblockeditor.util

import com.itsazza.noteblockeditor.NoteBlockEditorPlugin
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object Noteblock {
    private val instance = NoteBlockEditorPlugin.instance

    fun giveBlock(player: Player, value: Int) {
        val block = ItemStack(Material.NOTE_BLOCK, 64)
        val itemMeta = block.itemMeta!!
        itemMeta.persistentDataContainer.set(
            NamespacedKey(instance, "noteblock"),
            PersistentDataType.INTEGER,
            value - 1
        )
        itemMeta.setDisplayName(instance.getLangString("pre-set-note-block-name").format(value))
        block.itemMeta = itemMeta
        player.inventory.addItem(block)
    }

    fun setBlocksInHand(player: Player, value: Int) {
        val item = player.inventory.itemInMainHand
        val itemMeta = item.itemMeta!!
        itemMeta.persistentDataContainer.set(
            NamespacedKey(NoteBlockEditorPlugin.instance, "noteblock"),
            PersistentDataType.INTEGER,
            value - 1
        )
        itemMeta.setDisplayName(NoteBlockEditorPlugin.instance.getLangString("pre-set-note-block-name").format(value))
        item.itemMeta = itemMeta
        // player.updateInventory()
    }
}
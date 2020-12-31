package com.itsazza.noteblockeditor.menu.notemenu

import com.itsazza.noteblockeditor.NoteBlockEditorPlugin
import de.themoep.inventorygui.*
import com.itsazza.noteblockeditor.menu.buttons.closeButton
import com.itsazza.noteblockeditor.menu.instrumentmenu.NoteBlockInstrumentMenu
import com.itsazza.noteblockeditor.menu.noteBlockMenuTemplate
import org.bukkit.Instrument
import org.bukkit.Material
import org.bukkit.Note
import org.bukkit.block.Block
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object NoteBlockNoteMenu {
    fun openMenu(player: Player, block: Block, note: Note, instrument: Instrument) {
        createMenu(player, block, note, instrument).show(player)
    }

    private fun createMenu(player: Player, block: Block, note: Note, instrument: Instrument) : InventoryGui {
        val gui = InventoryGui(
            NoteBlockEditorPlugin.instance,
            null,
            "Note Block Editor",
            noteBlockMenuTemplate
        )

        val group = GuiElementGroup('n')

        for(item in listOfNotes) {
            group.addElement(createNoteBlockButton(block, player, item, instrument, note))
        }

        gui.addElement(group)
        if(player.hasPermission("noteblockeditor.instruments")) {
            gui.addElement(
                StaticGuiElement(
                    'i',
                    ItemStack(Material.BELL),
                    1,
                    {
                        NoteBlockInstrumentMenu.openMenu(player, block, note, instrument)
                        return@StaticGuiElement true
                    },
                    "§6§lChange instrument",
                    "§7Open a menu to change the",
                    "§7noteblock's instrument",
                    "§0 ",
                    "§eClick to open"
                )
            )
        }
        gui.closeAction = InventoryGui.CloseAction { false }
        gui.addElement(closeButton)
        return gui
    }

    private fun createNoteBlockButton(block: Block, player: Player, note: NoteBlockNote, instrument: Instrument, currentNote: Note) : StaticGuiElement {
        val item = ItemStack(Material.NOTE_BLOCK)
        val itemMeta = item.itemMeta
        if (Note(note.value - 1) == currentNote) {
            itemMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
            item.itemMeta = itemMeta
        }

        return StaticGuiElement('b',
            item,
            note.value,
            GuiElement.Action {
                val newNote = Note(note.value - 1)
                when {
                    it.event.isRightClick -> {
                        player.playNote(player.location, instrument, newNote)
                        return@Action true
                    }
                    it.event.isLeftClick -> {
                        player.playNote(player.location, instrument, newNote)
                        val data = block.blockData as NoteBlock
                        data.note = newNote
                        block.blockData = data
                        player.sendMessage("§eNote set to ${note.name}")
                        it.gui.destroy()
                        return@Action true
                    }
                    else -> {
                        return@Action true
                    }
                }
            },
            "§6§l${note.name}",
            "§7Equals to note ${note.name}",
            "§7or ${note.value - 1} clicks on",
            "§7the noteblock",
            "§0 ",
            "§e§lL-CLICK §7to set",
            "§e§lR-CLICK §7to preview"
            )
    }
}
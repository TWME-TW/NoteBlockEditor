package com.itsazza.noteblockeditor.menu.instrumentmenu

import com.itsazza.noteblockeditor.NoteBlockEditorPlugin
import com.itsazza.noteblockeditor.menu.buttons.closeButton
import com.itsazza.noteblockeditor.menu.noteBlockMenuTemplate
import com.itsazza.noteblockeditor.menu.notemenu.NoteBlockNoteMenu
import com.itsazza.noteblockeditor.util.canPlace
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Instrument
import org.bukkit.Material
import org.bukkit.Note
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object NoteBlockInstrumentMenu {
    fun openMenu(player: Player, block: Block, currentNote: Note, currentInstrument: Instrument) {
        createMenu(player, block, currentNote, currentInstrument).show(player)
    }

    private fun createMenu(player: Player, block: Block, currentNote: Note, currentInstrument: Instrument) : InventoryGui {
        val gui = InventoryGui(
            NoteBlockEditorPlugin.instance,
            null,
            "Note Block Editor",
            noteBlockMenuTemplate
        )

        val group = GuiElementGroup('n')

        for(item in listOfInstruments) {
            group.addElement(createInstrumentButton(block, currentNote, item, currentInstrument))
        }

        gui.addElement(group)
        gui.addElement(StaticGuiElement('r',
            ItemStack(Material.ARROW),
            1,
            {   NoteBlockNoteMenu.openMenu(player, block, currentNote, currentInstrument)
                return@StaticGuiElement true},
            "§eBack"
        ))
        gui.addElement(closeButton)
        gui.closeAction = InventoryGui.CloseAction { false }
        return gui
    }

    private fun createInstrumentButton(block: Block, note: Note, newInstrument: NoteBlockInstrument, currentInstrument: Instrument) : StaticGuiElement {
        val item = ItemStack(newInstrument.material)
        if(newInstrument.instrument == currentInstrument) {
            val itemMeta = item.itemMeta
            itemMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
            item.itemMeta = itemMeta
        }

        return StaticGuiElement('i',
            item,
            {
                val player = it.event.whoClicked as Player
                when {
                    it.event.isLeftClick -> {
                        val blockBelow = block.getRelative(BlockFace.DOWN)
                        if(!player.canPlace(blockBelow)) {
                            player.sendMessage("§cYou don't have permission to edit the block below the note block!")
                            it.gui.destroy()
                            return@StaticGuiElement true
                        }
                        blockBelow.setType(newInstrument.material, true)
                        it.gui.destroy()
                        return@StaticGuiElement true
                    }
                    it.event.isRightClick -> {
                        player.playNote(player.location, newInstrument.instrument, note)
                        return@StaticGuiElement  true
                    }
                    else -> {
                        return@StaticGuiElement true
                    }
                }
            },
            "§6§l${newInstrument.name}",
            "§7Sets the note block",
            "§7instrument to ${newInstrument.name.toLowerCase()}",
            "§0 ",
            "§e§lL-CLICK §7to set",
            "§e§lR-CLICK §7to preview"
        )
    }
}
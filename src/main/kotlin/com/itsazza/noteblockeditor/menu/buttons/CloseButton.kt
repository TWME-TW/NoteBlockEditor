package com.itsazza.noteblockeditor.menu.buttons

import com.itsazza.noteblockeditor.NoteBlockEditorPlugin
import de.themoep.inventorygui.GuiElement
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

val closeButton = StaticGuiElement(
    'c',
    ItemStack(Material.BARRIER),
    GuiElement.Action {
        it.gui.destroy(); return@Action true
                      },
    NoteBlockEditorPlugin.instance.getLangString("button-close")
)
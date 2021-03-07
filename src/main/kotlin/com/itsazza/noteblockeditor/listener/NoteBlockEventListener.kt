package com.itsazza.noteblockeditor.listener

import com.itsazza.noteblockeditor.menu.notemenu.NoteBlockNoteMenu
import com.itsazza.noteblockeditor.util.canPlace
import org.bukkit.Material
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object NoteBlockEventListener: Listener {
    @EventHandler
    fun onNoteBlockClick(event: PlayerInteractEvent) {
        val player = event.player

        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (!player.isSneaking) return
        if (player.inventory.itemInMainHand.type != Material.AIR) return

        val clickedBlock = event.clickedBlock ?: return
        if (clickedBlock.type != Material.NOTE_BLOCK) return

        if(!player.hasPermission("noteblockeditor.interact")) return
        val noteBlock = clickedBlock.blockData as? NoteBlock ?: return

        if(!player.canPlace(clickedBlock) && !player.hasPermission("noteblockeditor.bypass")) {
            player.sendMessage("§cYou're not allowed to build here!")
            return
        }

        NoteBlockNoteMenu.openMenu(player, clickedBlock, noteBlock.note, noteBlock.instrument)
        event.isCancelled = true
    }
}
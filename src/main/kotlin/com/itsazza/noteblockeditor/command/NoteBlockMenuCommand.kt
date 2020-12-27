package com.itsazza.noteblockeditor.command

import com.itsazza.noteblockeditor.menu.notemenu.NoteBlockNoteMenu
import com.itsazza.noteblockeditor.util.canPlace
import org.bukkit.Material
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object NoteBlockMenuCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cThis command must be executed as a player!")
            return true
        }

        if (!sender.hasPermission("noteblockeditor.command")) {
            sender.sendMessage("§cInsufficient permissions: noteblockeditor.command")
            return true
        }

        val block = sender.getTargetBlockExact(15) ?: return true

        if(!sender.canPlace(block) && sender.hasPermission("noteblockeditor.bypass")) {
            sender.sendMessage("§cYou're not allowed to build here!")
            return true
        }

        if(block.type != Material.NOTE_BLOCK) {
            sender.sendMessage("§cYou're not looking at a note block!")
            return true
        }

        val noteBlock = block.blockData as NoteBlock
        NoteBlockNoteMenu.openMenu(sender, block, noteBlock.note, noteBlock.instrument)
        return true
    }
}
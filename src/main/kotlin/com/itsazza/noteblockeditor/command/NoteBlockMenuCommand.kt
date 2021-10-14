package com.itsazza.noteblockeditor.command

import com.itsazza.noteblockeditor.NoteBlockEditorPlugin
import com.itsazza.noteblockeditor.menu.notemenu.NoteBlockNoteMenu
import com.itsazza.noteblockeditor.util.canPlace
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object NoteBlockMenuCommand : CommandExecutor {
    private val instance = NoteBlockEditorPlugin.instance

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(instance.getLangString("not-a-player"))
            return true
        }

        if (args.isNotEmpty() && args[0].toLowerCase() == "reload") {
            if (!sender.hasPermission("noteblockeditor.reload")) {
                sender.sendMessage(instance.getLangString("config-reload-no-permission"))
                return true
            }

            instance.reloadConfig()
            instance.loadInstruments()
            sender.sendMessage(instance.getLangString("config-reload"))
            return true
        }

        if (!sender.hasPermission("noteblockeditor.command")) {
            sender.sendMessage(instance.getLangString("command-no-permission"))
            return true
        }

        val block = sender.getTargetBlockExact(15)
        if (block == null || block.blockData !is NoteBlock) {
            sender.sendMessage(instance.getLangString("not-looking-at-noteblock"))
            return true
        }

        if(!sender.canPlace(block) && !sender.hasPermission("noteblockeditor.bypass")) {
            sender.sendMessage(instance.getLangString("no-build-permission"))
            return true
        }

        val noteBlock = block.blockData as NoteBlock
        NoteBlockNoteMenu.openMenu(sender, block, noteBlock.note, noteBlock.instrument)
        return true
    }
}
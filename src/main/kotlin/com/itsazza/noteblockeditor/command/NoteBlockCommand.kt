package com.itsazza.noteblockeditor.command

import com.itsazza.noteblockeditor.NoteBlockEditorPlugin
import com.itsazza.noteblockeditor.menu.blockmenu.BlockMenu
import com.itsazza.noteblockeditor.menu.notemenu.NoteBlockNoteMenu
import com.itsazza.noteblockeditor.util.canPlace
import com.itsazza.noteblockeditor.util.giveNoteBlock
import org.bukkit.Bukkit
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object NoteBlockCommand : CommandExecutor {
    private val instance = NoteBlockEditorPlugin.instance

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(instance.getLangString("not-a-player"))
            return true
        }

        if (args.isNotEmpty()) {
            when (args[0].lowercase()) {
                "reload" -> {
                    if (!sender.hasPermission("noteblockeditor.reload")) {
                        sender.sendMessage(instance.getLangString("config-reload-no-permission"))
                        return true
                    }
                    reload(sender)
                    return true
                }
                "give" -> {
                    if (!sender.hasPermission("noteblockeditor.give")) {
                        sender.sendMessage(instance.getLangString("general-no-permission"))
                        return true
                    }

                    when (args.size) {
                        1 -> {
                            BlockMenu.open(sender)
                            return true
                        }
                        2 -> {
                            val level = args[1].toIntOrNull() ?: return true
                            if (level !in 1..25) return true
                            giveNoteBlock(sender, level)
                            sender.sendMessage(instance.getLangString("received-note-block"))
                            return true
                        }
                        3 -> {
                            val level = args[1].toIntOrNull()
                            if (level == null || level !in 1..25) {
                                sender.sendMessage(instance.getLangString("invalid-level"))
                                return true
                            }
                            val player = Bukkit.getPlayer(args[2])
                            if (player == null) {
                                sender.sendMessage(instance.getLangString("player-not-found"))
                                return true
                            }
                            giveNoteBlock(player, level)
                            player.sendMessage(instance.getLangString("received-note-block"))
                            return true
                        }
                        else -> {
                            sender.sendMessage(instance.getLangString("command-give-usage"))
                            return true
                        }
                    }
                }
            }
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

        if (!sender.canPlace(block) && !sender.hasPermission("noteblockeditor.bypass")) {
            sender.sendMessage(instance.getLangString("no-build-permission"))
            return true
        }

        val noteBlock = block.blockData as NoteBlock
        NoteBlockNoteMenu.openMenu(sender, block, noteBlock.note, noteBlock.instrument)
        return true
    }

    private fun reload(player: Player) {
        instance.reloadConfig()
        instance.loadInstruments()
        player.sendMessage(instance.getLangString("config-reload"))
    }
}
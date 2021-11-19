package com.itsazza.noteblockeditor.command

import com.itsazza.noteblockeditor.NoteBlockEditorPlugin
import com.itsazza.noteblockeditor.menu.notemenu.NoteBlockNoteMenu
import com.itsazza.noteblockeditor.util.canPlace
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object NoteBlockCommand : CommandExecutor {
    private val instance = NoteBlockEditorPlugin.instance

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(instance.getLangString("not-a-player"))
            return true
        }

        if (args.isNotEmpty()) {
            when (args[0].toLowerCase()) {
                "reload" -> {
                    if (!sender.hasPermission("noteblockeditor.reload")) {
                        sender.sendMessage(instance.getLangString("config-reload-no-permission"))
                        return true
                    }
                    reload(sender)
                    return true
                }
                "get" -> {
                    if(!sender.hasPermission("noteblockeditor.give")) {
                        sender.sendMessage(instance.getLangString("general-no-permission"))
                        return true
                    }

                    if (args.size < 2) {
                        sender.sendMessage("Oops!")
                        return true
                    }

                    val level = args[1].toIntOrNull() ?: return true
                    if (level !in 0..24) return true

                    giveNoteBlock(sender, level)
                    return true
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

        if(!sender.canPlace(block) && !sender.hasPermission("noteblockeditor.bypass")) {
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

    private fun giveNoteBlock(player: Player, value: Int) {
        val block = ItemStack(Material.NOTE_BLOCK, 64)
        val itemMeta = block.itemMeta!!
        itemMeta.persistentDataContainer.set(NamespacedKey(instance, "noteblock"), PersistentDataType.INTEGER, value)
        itemMeta.lore = listOf("§7Value: §2$value", "§ePlace to set as this level!")
        block.itemMeta = itemMeta
        player.inventory.addItem(block)
        player.sendMessage("Gave pre-set noteblock!")
    }
}
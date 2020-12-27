package com.itsazza.noteblockeditor.util

import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.EquipmentSlot

fun Player.canPlace(block: Block): Boolean {
    val event = BlockPlaceEvent(block, block.state, block, inventory.itemInMainHand, this, true, EquipmentSlot.HAND)
    Bukkit.getPluginManager().callEvent(event)
    return !event.isCancelled
}
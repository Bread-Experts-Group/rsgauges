package org.bread_experts_group.rsgauges.util

import net.minecraft.world.level.block.state.BlockBehaviour

fun BlockBehaviour.Properties.isValidSpawn(value: Boolean): BlockBehaviour.Properties =
	this.isValidSpawn { _, _, _, _ -> value }
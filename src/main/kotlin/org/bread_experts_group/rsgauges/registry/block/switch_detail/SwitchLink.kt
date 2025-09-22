package org.bread_experts_group.rsgauges.registry.block.switch_detail

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

class SwitchLink(
	val targetPos: BlockPos = BlockPos.ZERO,
	val blockID: String = "",
	private val config: Long = 0,
	val valid: Boolean = false
) {
	constructor(
		pos: BlockPos,
		blockID: String,
		config: Long
	) : this(pos, blockID, config, !blockID.isEmpty() && pos != BlockPos.ZERO)

	var level: Level? = null
	val player: Player? = null
	var sourceAnalogPower: Int = 0
	var sourceDigitalPower: Int = 0
	var sourcePosition: BlockPos = BlockPos.ZERO
	override fun toString(): String = "SwitchLink{pos=$targetPos, block='$blockID', config=$config}"
}
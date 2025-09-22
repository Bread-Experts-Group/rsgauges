package org.bread_experts_group.rsgauges.registry.block.switch_detail

import com.google.common.collect.ImmutableList
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import java.util.*

interface ISwitchLinkable {
	fun switchLinkHasTargetSupport(level: Level, pos: BlockPos): Boolean
	fun switchLinkHasSourceSupport(level: Level, pos: BlockPos): Boolean
	fun switchLinkHasAnalogSupport(level: Level, pos: BlockPos): Boolean
	fun switchLinkUnlink(link: SwitchLink)
	fun switchLinkInit(link: SwitchLink)
	fun switchLinkGetSupportedTargetModes(): List<LinkMode> = listOf(
		LinkMode.AS_STATE,
		LinkMode.ACTIVATE,
		LinkMode.DEACTIVATE,
		LinkMode.TOGGLE,
		LinkMode.INV_STATE
	)

	fun switchLinkTrigger(link: SwitchLink): RequestResult
	fun switchLinkOutputPower(level: Level, pos: BlockPos): Optional<Int>
	fun switchLinkInputPower(level: Level, pos: BlockPos): Optional<Int>
	fun switchLinkComparatorInput(level: Level, pos: BlockPos): Optional<Int>
}
package org.bread_experts_group.rsgauges.registry.block

import net.minecraft.core.BlockPos
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import org.bread_experts_group.rsgauges.registry.block.switch_detail.ISwitchLinkable
import org.bread_experts_group.rsgauges.registry.block.switch_detail.LinkMode
import org.bread_experts_group.rsgauges.registry.block.switch_detail.RequestResult
import org.bread_experts_group.rsgauges.registry.block.switch_detail.SwitchLink
import org.bread_experts_group.rsgauges.util.RsConfigFlags
import java.util.EnumSet
import java.util.Optional

open class SwitchBlock(configFlags: EnumSet<RsConfigFlags>, properties: Properties) : RsDirectedBlock(configFlags, properties), ISwitchLinkable {
	companion object {
		val POWERED: BooleanProperty = BlockStateProperties.POWERED
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		super.createBlockStateDefinition(builder)
		builder.add(Companion.POWERED)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState =
		this.defaultBlockState().setValue(Companion.POWERED, false)

	override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? = null
	override fun switchLinkHasTargetSupport(level: Level, pos: BlockPos): Boolean =
		this.configFlags.contains(RsConfigFlags.SWITCH_CONFIG_LINK_TARGET_SUPPORT)

	override fun switchLinkHasSourceSupport(level: Level, pos: BlockPos): Boolean =
		this.configFlags.contains(RsConfigFlags.SWITCH_CONFIG_LINK_SOURCE_SUPPORT)

	override fun switchLinkHasAnalogSupport(level: Level, pos: BlockPos): Boolean = false
	override fun switchLinkGetSupportedTargetModes(): List<LinkMode> =
		if (this.configFlags.contains(RsConfigFlags.SWITCH_CONFIG_PULSE))
			listOf(LinkMode.ACTIVATE, LinkMode.DEACTIVATE, LinkMode.TOGGLE)
		else super.switchLinkGetSupportedTargetModes()

	override fun switchLinkOutputPower(level: Level, pos: BlockPos): Optional<Int> {
		TODO("Not yet implemented")
	}

	override fun switchLinkInputPower(level: Level, pos: BlockPos): Optional<Int> {
		TODO("Not yet implemented")
	}
	override fun switchLinkComparatorInput(level: Level, pos: BlockPos): Optional<Int> {
		TODO("Not yet implemented")
	}
	override fun switchLinkTrigger(link: SwitchLink): RequestResult {
		TODO("Not yet implemented")
	}

	override fun switchLinkInit(link: SwitchLink) {
		TODO("Not yet implemented")
	}
	override fun switchLinkUnlink(link: SwitchLink) {
	}
}
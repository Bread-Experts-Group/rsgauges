package org.bread_experts_group.rsgauges.registry.block

import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.DirectionProperty
import org.bread_experts_group.rsgauges.util.RsConfigFlags
import java.util.EnumSet

abstract class RsDirectedBlock(configFlags: EnumSet<RsConfigFlags>, properties: Properties) : RsBlock(configFlags, properties) {
	companion object {
		val FACING: DirectionProperty = DirectionalBlock.FACING
	}

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		super.createBlockStateDefinition(builder)
		builder.add(Companion.FACING)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState =
		this.defaultBlockState().setValue(Companion.FACING, Direction.SOUTH)
}
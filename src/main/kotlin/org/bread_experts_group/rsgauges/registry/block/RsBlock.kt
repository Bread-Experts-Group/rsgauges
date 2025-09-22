package org.bread_experts_group.rsgauges.registry.block

import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import org.bread_experts_group.rsgauges.util.RsConfigFlags
import java.util.EnumSet

abstract class RsBlock(val configFlags: EnumSet<RsConfigFlags>, properties: Properties) : Block(properties), EntityBlock {
	companion object {
		val WATERLOGGED: BooleanProperty = BlockStateProperties.WATERLOGGED
	}
	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(Companion.WATERLOGGED)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState =
		this.defaultBlockState().setValue(Companion.WATERLOGGED, false)
}
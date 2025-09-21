package org.bread_experts_group.rsgauges.registry.block

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.MapColor
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import org.bread_experts_group.rsgauges.registry.RegistryProvider
import org.bread_experts_group.rsgauges.registry.item.IRegisterSpecialCreativeTab
import org.bread_experts_group.rsgauges.registry.item.ModItems
import org.bread_experts_group.rsgauges.util.isValidSpawn
import java.util.function.Supplier

object ModBlocks : RegistryProvider(
	Registries.BLOCK,
	Registries.MENU,
	Registries.BLOCK_ENTITY_TYPE
) {
	private val registry: DeferredRegister<Block> = this.getRegistry(Registries.BLOCK)
	private val menuRegistry: DeferredRegister<MenuType<*>> = this.getRegistry(Registries.MENU)
	private val blockEntityRegistry: DeferredRegister<BlockEntityType<*>> = this.getRegistry(
		Registries.BLOCK_ENTITY_TYPE
	)

	fun blockIterator(): Iterator<DeferredBlock<Block>> = object : Iterator<DeferredBlock<Block>> {
		val registryIterator = this@ModBlocks.registry.entries.iterator()
		override fun hasNext(): Boolean = this.registryIterator.hasNext()
		override fun next(): DeferredBlock<Block> {
			val holder = this.registryIterator.next()
			return DeferredBlock.createBlock(holder.id)
		}
	}

	private val GAUGE_METALLIC_PROPERTIES: BlockBehaviour.Properties =
		BlockBehaviour.Properties.of()
			.strength(0.5f, 15f)
			.mapColor(MapColor.METAL)
			.sound(SoundType.METAL)
			.noCollission()
			.isValidSpawn(false)
	private val GAUGE_GLASS_PROPERTIES: BlockBehaviour.Properties =
		BlockBehaviour.Properties.of()
			.strength(0.5f, 15f)
			.mapColor(MapColor.METAL)
			.sound(SoundType.METAL)
			.noOcclusion()
			.isValidSpawn(false)
	private val INDICATOR_METALLIC_PROPERTIES: BlockBehaviour.Properties =
		BlockBehaviour.Properties.of()
			.strength(0.5f, 15f)
			.mapColor(MapColor.METAL)
			.sound(SoundType.METAL)
			.lightLevel { _ -> 3 }
			.noOcclusion()
			.isValidSpawn(false)
	private val INDICATOR_GLASS_PROPERTIES: BlockBehaviour.Properties =
		BlockBehaviour.Properties.of()
			.strength(0.5f, 15f)
			.mapColor(MapColor.METAL)
			.sound(SoundType.METAL)
			.lightLevel { state: BlockState? -> 3 }
			.noOcclusion()
			.isValidSpawn(false)
	private val ALARM_LAMP_PROPERTIES: BlockBehaviour.Properties =
		BlockBehaviour.Properties.of()
			.strength(0.5f, 15f)
			.mapColor(MapColor.METAL)
			.sound(SoundType.METAL)
			.noOcclusion()
			.lightLevel { state -> if (state.getValue(BlockStateProperties.POWERED)) 12 else 2 }
			.isValidSpawn(false)
	private val COLORED_SENSITIVE_GLASS_PROPERTIES: BlockBehaviour.Properties =
		BlockBehaviour.Properties.of()
			.strength(0.35f, 15f)
			.mapColor(MapColor.METAL)
			.sound(SoundType.GLASS)
			.noOcclusion()
			.isValidSpawn(false)
	private val LIGHT_EMITTING_SENSITIVE_GLASS_PROPERTIES: BlockBehaviour.Properties =
		BlockBehaviour.Properties.of()
			.strength(0.35f, 15f)
			.mapColor(MapColor.METAL)
			.sound(SoundType.GLASS)
			.noOcclusion().emissiveRendering { _, _ , _ -> true }
			.lightLevel { state: BlockState -> if (state.getValue(BlockStateProperties.POWERED)) 15 else 0 }
			.isValidSpawn(false)
	private val SWITCH_METALLIC_PROPERTIES: BlockBehaviour.Properties = this.GAUGE_METALLIC_PROPERTIES
	private val SWITCH_GLASS_PROPERTIES: BlockBehaviour.Properties = this.GAUGE_GLASS_PROPERTIES
	private val SWITCH_METALLIC_FAINT_LIGHT_PROPERTIES: BlockBehaviour.Properties =
		BlockBehaviour.Properties.of()
			.strength(0.5f, 15f)
			.mapColor(MapColor.METAL)
			.sound(SoundType.METAL)
			.lightLevel { _ -> 5 }
	// Contact lever switch
	val INDUSTRIAL_SMALL_LEVER: DeferredItem<BlockItem> = this.registerSimpleBlockItem("industrial_small_lever")
	fun getLocation(block: Block): ResourceLocation = BuiltInRegistries.BLOCK.getKey(block)
	fun <T : Block> registerBlock(
		id: String,
		block: () -> T
	): DeferredBlock<T> {
		val holder = this.registry.register(id, Supplier {
			val actual = block()
//            if (actual is BreadModBlock) {
//                if (actual.shouldCreateEntity()) {
//                    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
//                    actual.blockEntityType = this.blockEntityRegistry.register(id, Supplier {
//                        BlockEntityType.Builder.of(
//                            { p, s -> actual.newBlockEntity(p, s) },
//                            actual
//                        ).build(null)
//                    })
//                }
//                val menu = actual.ofMenu()
//                if (menu != null) {
//                    actual.menuType = this.menuRegistry.register(id) { id: ResourceLocation ->
//                        IMenuTypeExtension.create { id, inventory, byteBuf ->
//                            @Suppress("UNCHECKED_CAST")
//                            menu(
//                                actual.menuType!!.get(), id, inventory,
//                                inventory.player
//                                    .level()
//                                    .getBlockEntity(
//                                        byteBuf.readBlockPos(),
//                                        actual.blockEntityType!!.get() as BlockEntityType<BreadModBlockEntity>
//                                    )
//                                    .get()
//                            )
//                        }
//                    }
//                }
//            }
			actual
		})
		return DeferredBlock.createBlock(holder.id)
	}

	private fun registerBlockItem(
		id: String,
		block: () -> Block,
		properties: Properties = Properties()
	): DeferredItem<BlockItem> = this.registerBlockItem(id, block) { blockItem -> BlockItem(blockItem, properties) }

	private fun <T : Block> registerBlockItem(
		id: String,
		block: () -> T,
		item: (block: T) -> BlockItem
	): DeferredItem<BlockItem> {
		val supplier = this.registerBlock(id, block)
		return ModItems.registerItem(id) { item(supplier.get()) }
	}

	private fun registerSimpleBlockItem(id: String): DeferredItem<BlockItem> =
		this.registerBlockItem(id, { Block(BlockBehaviour.Properties.of()) })

	private fun itemWithCreativeTab(
		block: Block,
		properties: Properties,
		creativeTabs: List<Supplier<CreativeModeTab>>
	): BlockItem = object : BlockItem(block, properties), IRegisterSpecialCreativeTab {
		override val creativeModeTabs: List<Supplier<CreativeModeTab>> = creativeTabs
	}
}

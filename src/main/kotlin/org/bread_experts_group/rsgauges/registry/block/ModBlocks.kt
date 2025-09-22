package org.bread_experts_group.rsgauges.registry.block

import net.minecraft.client.Minecraft
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
import net.minecraft.world.level.material.MapColor
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import org.bread_experts_group.rsgauges.registry.RegistryProvider
import org.bread_experts_group.rsgauges.registry.item.IRegisterSpecialCreativeTab
import org.bread_experts_group.rsgauges.registry.item.ModItems
import org.bread_experts_group.rsgauges.util.RsConfigFlags
import org.bread_experts_group.rsgauges.util.isValidSpawn
import java.util.EnumSet
import java.util.function.Supplier

@Suppress("unused")
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
//			.lightLevel { state -> if (state.getValue(BlockStateProperties.POWERED)) 12 else 2 }
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
			.noOcclusion().emissiveRendering { _, _, _ -> true }
//			.lightLevel { state: BlockState -> if (state.getValue(BlockStateProperties.POWERED)) 15 else 0 }
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
	val INDUSTRIAL_SMALL_LEVER: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_small_lever", {
			BistableSwitchBlock(
				EnumSet.of(
					RsConfigFlags.RSBLOCK_CONFIG_CUTOUT, RsConfigFlags.SWITCH_CONFIG_BISTABLE,
					RsConfigFlags.SWITCH_CONFIG_WALLMOUNT, RsConfigFlags.SWITCH_CONFIG_WEAKABLE,
					RsConfigFlags.SWITCH_CONFIG_INVERTABLE,
					RsConfigFlags.SWITCH_CONFIG_LINK_TARGET_SUPPORT, RsConfigFlags.SWITCH_CONFIG_LINK_SOURCE_SUPPORT
				), this.SWITCH_METALLIC_PROPERTIES
			)
		})
	// Mechanical lever
	val INDUSTRIAL_LEVER: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_lever", {
			BistableSwitchBlock(
				EnumSet.of(
					RsConfigFlags.RSBLOCK_CONFIG_CUTOUT, RsConfigFlags.SWITCH_CONFIG_BISTABLE,
					RsConfigFlags.SWITCH_CONFIG_WALLMOUNT, RsConfigFlags.SWITCH_CONFIG_WEAKABLE,
					RsConfigFlags.SWITCH_CONFIG_INVERTABLE,
					RsConfigFlags.SWITCH_CONFIG_LINK_TARGET_SUPPORT, RsConfigFlags.SWITCH_CONFIG_LINK_SOURCE_SUPPORT
				), this.SWITCH_METALLIC_PROPERTIES
			)
		})
	// Mechanical rotary lever
	val INDUSTRIAL_ROTARY_LEVER: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_rotary_lever", {
			BistableSwitchBlock(
				EnumSet.of(
					RsConfigFlags.RSBLOCK_CONFIG_CUTOUT, RsConfigFlags.SWITCH_CONFIG_BISTABLE,
					RsConfigFlags.SWITCH_CONFIG_WALLMOUNT, RsConfigFlags.SWITCH_CONFIG_WEAKABLE,
					RsConfigFlags.SWITCH_CONFIG_INVERTABLE,
					RsConfigFlags.SWITCH_CONFIG_LINK_TARGET_SUPPORT, RsConfigFlags.SWITCH_CONFIG_LINK_SOURCE_SUPPORT
				), this.SWITCH_METALLIC_PROPERTIES
			)
		})
	// Rotary machine switch
	val INDUSTRIAL_ROTARY_MACHINE_SWITCH: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_rotary_machine_switch", {
			BistableSwitchBlock(
				EnumSet.of(
					RsConfigFlags.RSBLOCK_CONFIG_CUTOUT, RsConfigFlags.SWITCH_CONFIG_BISTABLE,
					RsConfigFlags.SWITCH_CONFIG_WALLMOUNT, RsConfigFlags.SWITCH_CONFIG_WEAKABLE,
					RsConfigFlags.SWITCH_CONFIG_INVERTABLE,
					RsConfigFlags.SWITCH_CONFIG_LINK_TARGET_SUPPORT, RsConfigFlags.SWITCH_CONFIG_LINK_SOURCE_SUPPORT
				), this.SWITCH_METALLIC_PROPERTIES
			)
		})
	// Two-button machine switch
	val INDUSTRIAL_MACHINE_SWITCH: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_machine_switch", {
			BistableSwitchBlock(
				EnumSet.of(
					RsConfigFlags.RSBLOCK_CONFIG_CUTOUT, RsConfigFlags.SWITCH_CONFIG_BISTABLE,
					RsConfigFlags.SWITCH_CONFIG_WALLMOUNT, RsConfigFlags.SWITCH_CONFIG_WEAKABLE,
					RsConfigFlags.SWITCH_CONFIG_INVERTABLE,
					RsConfigFlags.SWITCH_CONFIG_LINK_TARGET_SUPPORT, RsConfigFlags.SWITCH_CONFIG_LINK_SOURCE_SUPPORT
				), this.SWITCH_METALLIC_PROPERTIES
			)
		})
	// ESTOP button
	val INDUSTRIAL_ESTOP_SWITCH: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_estop_switch", {
			BistableSwitchBlock(
				EnumSet.of(
					RsConfigFlags.RSBLOCK_CONFIG_CUTOUT, RsConfigFlags.SWITCH_CONFIG_BISTABLE,
					RsConfigFlags.SWITCH_CONFIG_WALLMOUNT, RsConfigFlags.SWITCH_CONFIG_WEAKABLE,
					RsConfigFlags.SWITCH_CONFIG_INVERTABLE,
					RsConfigFlags.SWITCH_CONFIG_LINK_TARGET_SUPPORT, RsConfigFlags.SWITCH_CONFIG_LINK_SOURCE_SUPPORT,
					RsConfigFlags.SWITCH_CONFIG_PROJECTILE_SENSE_OFF
				), this.SWITCH_METALLIC_PROPERTIES
			)
		})
	// Hopper blocking switch
	val INDUSTRIAL_HOPPER_SWITCH: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_hopper_switch", {
			BistableSwitchBlock(
				EnumSet.of(
					RsConfigFlags.RSBLOCK_CONFIG_CUTOUT, RsConfigFlags.SWITCH_CONFIG_BISTABLE,
					RsConfigFlags.SWITCH_CONFIG_WALLMOUNT, RsConfigFlags.SWITCH_CONFIG_WEAKABLE,
					RsConfigFlags.SWITCH_CONFIG_INVERTABLE,
					RsConfigFlags.SWITCH_CONFIG_LINK_TARGET_SUPPORT, RsConfigFlags.SWITCH_CONFIG_LINK_SOURCE_SUPPORT,
					RsConfigFlags.SWITCH_DATA_WEAK
				), this.SWITCH_METALLIC_PROPERTIES
			)
		})
	// Square machine pulse switch
	val INDUSTRIAL_BUTTON: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_button", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Fenced round machine pulse switch
	val INDUSTRIAL_FENCED_BUTTON: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_fenced_button", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Retro double pole switch
	val INDUSTRIAL_DOUBLE_POLE_BUTTON: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_double_pole_button", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Mechanical spring reset push button
	val INDUSTRIAL_FOOT_BUTTON: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_foot_button", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Mechanical spring reset pull handle
	val INDUSTRIAL_PULL_HANDLE: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_pull_handle", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Manual dimmer
	val INDUSTRIAL_DIMMER: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_dimmer", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Door contact mat
	val INDUSTRIAL_DOOR_CONTACT_MAT: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_door_contact_mat", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Sensitive full size contact mat
	val INDUSTRIAL_CONTACT_MAT: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_contact_mat", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial shock sensor contact mat
	val INDUSTRIAL_SHOCK_SENSITIVE_CONTACT_MAT: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_shock_sensitive_contact_mat", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial trap door switch (shock vibration sensitive)
	val INDUSTRIAL_SHOCK_SENSITIVE_TRAPDOOR: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_shock_sensitive_trapdoor", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial trap door switch (high sensitive shock vibration sensitive)
	val INDUSTRIAL_HIGH_SENSITIVE_TRAPDOOR: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_high_sensitive_trapdoor", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial trap door switch (item trap door)
	val INDUSTRIAL_FALLTHROUGH_DETECTOR: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_fallthrough_detector", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Day time switch
	val INDUSTRIAL_DAY_TIMER: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_day_timer", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Interval signal timer
	val INDUSTRIAL_INTERVAL_TIMER: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_interval_timer", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Infrared motion sensor
	val INDUSTRIAL_ENTITY_DETECTOR: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_entity_detector", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Linear laser motion sensor
	val INDUSTRIAL_LINEAR_ENTITY_DETECTOR: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_linear_entity_detector", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Local light sensor
	val INDUSTRIAL_LIGHT_SENSOR: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_light_sensor", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Rain sensor switch
	val INDUSTRIAL_RAIN_SENSOR: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_rain_sensor", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Lightning sensor switch
	val INDUSTRIAL_LIGHTNING_SENSOR: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_lightning_sensor", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Comparator output level observing switch
	val INDUSTRIAL_COMPARATOR_SWITCH: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_comparator_switch", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Uni-directional block detector switch
	val INDUSTRIAL_BLOCK_DETECTOR: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_block_detector", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial bistable link receiver switch
	val INDUSTRIAL_SWITCHLINK_RECEIVER: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_switchlink_receiver", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial analog link receiver
	val INDUSTRIAL_SWITCHLINK_RECEIVER_ANALOG: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_switchlink_receiver_analog", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial full block bistable link receiver switch
	val INDUSTRIAL_SWITCHLINK_CASED_RECEIVER: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_switchlink_cased_receiver", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial pulse link receiver switch
	val INDUSTRIAL_SWITCHLINK_PULSE_RECEIEVER: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_switchlink_pulse_receiver", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial full block pulse link receiver switch
	val INDUSTRIAL_SWITCHLINK_CASED_PULSE_RECEIEVER: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_switchlink_cased_pulse_receiver", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial bistable link relay
	val INDUSTRIAL_SWITCHLINK_RELAY: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_switchlink_relay", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial analog link relay
	val INDUSTRIAL_SWITCHLINK_RELAY_ANALOG: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_switchlink_relay_analog", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Industrial pulse link relay
	val INDUSTRIAL_SWITCHLINK_PULSE_RELAY: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_switchlink_pulse_relay", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Bistable industrial knock surge detector
	val INDUSTRIAL_KNOCK_SWITCH: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_knock_switch", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	// Pulse industrial knock surge detector
	val INDUSTRIAL_KNOCK_BUTTON: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_knock_button", { Block(this.SWITCH_METALLIC_PROPERTIES) })
	val INDUSTRIAL_ANALOG_ANGULAR_GAUGE: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_analog_angular_gauge", { Block(this.GAUGE_METALLIC_PROPERTIES) })
	val INDUSTRIAL_ANALOG_HORIZONTAL_GAUGE: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_analog_horizontal_gauge", { Block(this.GAUGE_METALLIC_PROPERTIES) })
	val INDUSTRIAL_VERTICAL_BAR_GAUGE: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_vertical_bar_gauge", { Block(this.GAUGE_METALLIC_PROPERTIES) })
	val INDUSTRIAL_SMALL_DIGITAL_GAUGE: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_small_digital_gauge", { Block(this.GAUGE_METALLIC_PROPERTIES) })
	val INDUSTRIAL_TUBE_GAUGE: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_tube_gauge", { Block(this.GAUGE_METALLIC_PROPERTIES) })
	val INDUSTRIAL_ALARM_LAMP: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_alarm_lamp", { Block(this.ALARM_LAMP_PROPERTIES) })
	val INDUSTRIAL_ALARM_SIREN: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_alarm_siren", { Block(this.INDICATOR_METALLIC_PROPERTIES) })
	val INDUSTRIAL_GREEN_LED: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_green_led", { Block(this.INDICATOR_METALLIC_PROPERTIES) })
	val INDUSTRIAL_YELLOW_LED: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_yellow_led", { Block(this.INDICATOR_METALLIC_PROPERTIES) })
	val INDUSTRIAL_RED_LED: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_red_led", { Block(this.INDICATOR_METALLIC_PROPERTIES) })
	val INDUSTRIAL_WHITE_LED: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_white_led", { Block(this.INDICATOR_METALLIC_PROPERTIES) })
	val INDUSTRIAL_GREEN_BLINKING_LED: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_green_blinking_led", { Block(this.INDICATOR_METALLIC_PROPERTIES) })
	val INDUSTRIAL_YELLOW_BLINKING_LED: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_yellow_blinking_led", { Block(this.INDICATOR_METALLIC_PROPERTIES) })
	val INDUSTRIAL_RED_BLINKING_LED: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_red_blinking_led", { Block(this.INDICATOR_METALLIC_PROPERTIES) })
	val INDUSTRIAL_WHITE_BLINKING_LED: DeferredItem<BlockItem> =
		this.registerBlockItem("industrial_white_blinking_led", { Block(this.INDICATOR_METALLIC_PROPERTIES) })
	// ----------
	// -- Rustic
	// ----------
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

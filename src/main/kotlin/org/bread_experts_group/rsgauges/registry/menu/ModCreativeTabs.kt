package org.bread_experts_group.rsgauges.registry.menu

import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.item.component.DyedItemColor
import net.neoforged.neoforge.registries.DeferredRegister
import org.bread_experts_group.rsgauges.RsGauges.Companion.modTranslatable
import org.bread_experts_group.rsgauges.registry.RegistryProvider
import org.bread_experts_group.rsgauges.registry.block.ModBlocks
import org.bread_experts_group.rsgauges.registry.item.IRegisterSpecialCreativeTab
import org.bread_experts_group.rsgauges.registry.item.ModItems
import java.util.Optional
import java.util.function.Supplier
import kotlin.collections.set

object ModCreativeTabs : RegistryProvider(Registries.CREATIVE_MODE_TAB) {
	private val registry: DeferredRegister<CreativeModeTab> = this.getRegistry(Registries.CREATIVE_MODE_TAB)
	private fun constructTab(
		name: String,
		general: Boolean,
		constructor: CreativeModeTab.Builder.() -> Unit
	): Supplier<CreativeModeTab> {
		val builder = CreativeModeTab.builder()
		val registryObject = this.registry.register(name, builder::build)
		builder
			.title(modTranslatable("itemGroup", name))
			.displayItems { parameters, output ->
				for (deferredItem in ModItems.itemIterator()) {
					val item = deferredItem.get()
					if (item is IRegisterSpecialCreativeTab) {
						if (
							item.creativeModeTabs.contains(registryObject) &&
							item.displayInCreativeTab(parameters, output)
						) output.accept(item.defaultInstance)
						continue
					}
					output.accept(item.defaultInstance)
				}
			}
		constructor(builder)
		return registryObject
	}

	val MAIN_TAB: Supplier<CreativeModeTab> = this.constructTab("main", true) {
		this.icon { ModBlocks.INDUSTRIAL_SMALL_LEVER.get().defaultInstance }
	}
}
package org.bread_experts_group.rsgauges

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bread_experts_group.rsgauges.registry.Registry

/**
 * Main mod class.
 */
@Mod(RsGauges.ID)
class RsGauges(eventBus: IEventBus, container: ModContainer) {
	companion object {
		const val ID = "rsgauges"
		val LOGGER: Logger = LogManager.getLogger(ID)

		fun modLocation(vararg path: String, override: Boolean = false): ResourceLocation =
			path.toMutableList().let {
				ResourceLocation.fromNamespaceAndPath(
					if (override) it.removeFirst() else this.ID, it.joinToString("/")
				)
			}

		fun modTranslatable(
			type: String = "misc",
			vararg path: String,
			args: List<Any> = listOf()
		): MutableComponent = Component.translatable(
			"$type.${this.ID}.${path.joinToString(".")}",
			*args.toTypedArray()
		)
	}

	init {
		Companion.LOGGER.log(Level.INFO, "Hello world!")

		Registry.registerAll(eventBus)
	}
}

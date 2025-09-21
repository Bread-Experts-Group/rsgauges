package org.bread_experts_group.rsgauges.registry

import net.neoforged.bus.api.IEventBus
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bread_experts_group.rsgauges.registry.block.ModBlocks
import org.bread_experts_group.rsgauges.registry.item.ModItems
import org.bread_experts_group.rsgauges.registry.menu.ModCreativeTabs

object Registry {
	val logger: Logger = LogManager.getLogger("RsGauges Mod Registry")
	private val registerList: Array<RegistryProvider> = arrayOf(
		ModBlocks,
		ModItems,
		ModCreativeTabs
	)

	fun registerAll(modBus: IEventBus) {
		this.registerList.forEach {
			for (registry in it) {
				this.logger.info("Pushing registry [${registry.registryName}]")
				registry.register(modBus)
			}
		}
	}
}
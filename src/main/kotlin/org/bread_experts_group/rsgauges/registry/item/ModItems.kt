package org.bread_experts_group.rsgauges.registry.item

import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import org.bread_experts_group.rsgauges.registry.RegistryProvider

object ModItems : RegistryProvider(Registries.ITEM) {
	private val registry: DeferredRegister<Item> = this.getRegistry(Registries.ITEM)
	fun itemIterator(): Iterator<DeferredItem<Item>> = object : Iterator<DeferredItem<Item>> {
		val registryIterator = this@ModItems.registry.entries.iterator()
		override fun hasNext(): Boolean = this.registryIterator.hasNext()
		override fun next(): DeferredItem<Item> {
			val holder = this.registryIterator.next()
			return DeferredItem.createItem(holder.id)
		}
	}

	fun <T : Item> registerItem(id: String, item: () -> T): DeferredItem<T> {
		val holder = this.registry.register(id, item)
		return DeferredItem.createItem(holder.id)
	}

	fun registerItem(id: String, properties: Item.Properties = Item.Properties()): DeferredItem<Item> {
		return this.registerItem(id) { Item(properties) }
	}
}
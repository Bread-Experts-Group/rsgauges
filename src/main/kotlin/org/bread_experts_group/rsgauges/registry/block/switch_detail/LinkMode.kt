package org.bread_experts_group.rsgauges.registry.block.switch_detail

import net.minecraft.util.Mth
import java.util.*

enum class LinkMode(val index : Int) {
	AS_STATE(0x0),
	ACTIVATE(0x1),
	DEACTIVATE(0x2),
	TOGGLE(0x3),
	INV_STATE(0x4);

	companion object {
		val VALUES: Array<LinkMode> = entries.toTypedArray()
		val BY_INDEX: Array<LinkMode> = Arrays.stream(this.VALUES) // what even is this man
			.sorted(Comparator.comparingInt { it.index })
			.toArray {
				arrayListOf<LinkMode>().also { array -> repeat(this.VALUES.size) {
					array.add(entries[it])
				}}.toTypedArray()
			}
		fun fromInt(value: Int): LinkMode = BY_INDEX[Mth.clamp(value, 0, VALUES.size - 1)]
	}
}
package architecture.abnormalities.init

import architecture.abnormalities.core.Abnormalities
import architecture.goldenboughs_lib.api.ModByteBufCodecs
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import net.minecraft.core.UUIDUtil
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.syncher.EntityDataSerializer
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.*

object AbnormalitiesEntityDataSerializers {
	@JvmField
	val REGISTRY: DeferredRegister<EntityDataSerializer<*>> =
		Abnormalities.modRegister(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS)

	@JvmField
	val ENTITY_ID: DeferredHolder<EntityDataSerializer<*>, EntityDataSerializer<Int2ObjectMap<Map.Entry<Int, UUID>>>> =
		register(
			"entity_id",
			EntityDataSerializer.forValueType(
				ByteBufCodecs.map(
					{ Int2ObjectOpenHashMap() },
					ByteBufCodecs.INT,
					ModByteBufCodecs.entry(
						{ k, v -> AbstractMap.SimpleEntry(k, v) },
						ByteBufCodecs.INT,
						UUIDUtil.STREAM_CODEC
					)
				)
			)
		)

	@JvmField
	val JOINT_NAMES: DeferredHolder<EntityDataSerializer<*>, EntityDataSerializer<List<String>>> = register(
		"joint_names",
		EntityDataSerializer.forValueType(ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()))
	)

	private fun <T> register(
		name: String,
		serializer: EntityDataSerializer<T>
	): DeferredHolder<EntityDataSerializer<*>, EntityDataSerializer<T>> {
		return REGISTRY.register(name) { -> serializer }
	}
}

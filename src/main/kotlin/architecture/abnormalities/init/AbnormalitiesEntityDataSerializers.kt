package architecture.abnormalities.init

import architecture.abnormalities.core.Abnormalities
import architecture.goldenboughs_lib.api.ModByteBufCodecs
import io.netty.buffer.ByteBuf
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import net.minecraft.core.UUIDUtil
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.syncher.EntityDataSerializer
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.UUID
import java.util.function.BiFunction
import java.util.function.IntFunction

object AbnormalitiesEntityDataSerializers {
	@JvmField
	val REGISTRY: DeferredRegister<EntityDataSerializer<*>> =
		Abnormalities.modRegister(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS)

	@JvmField
	val ENTITY_ID: DeferredHolder<EntityDataSerializer<*>, EntityDataSerializer<Int2ObjectMap<Map.Entry<Int, UUID>>>> =
		register(
			"entity_id",
			EntityDataSerializer.forValueType(
				ByteBufCodecs.map<ByteBuf, Int2ObjectMap<MutableMap.MutableEntry<Int, UUID>>, Int, MutableMap.MutableEntry<Int, UUID>>(
					IntFunction { Int2ObjectOpenHashMap() },
					ByteBufCodecs.INT,
					ModByteBufCodecs.entry(
						BiFunction { k: Int, v: UUID -> java.util.AbstractMap.SimpleEntry(k, v) },
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
		return REGISTRY.register(name) { serializer }
	}
}

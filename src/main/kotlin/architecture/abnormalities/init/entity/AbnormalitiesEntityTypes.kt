package architecture.abnormalities.init.entity

import architecture.abnormalities.common.entity.abnormalities.TrainingRabbits
import architecture.abnormalities.core.Abnormalities
import architecture.abnormalities.datagen.i18n.ZhCn
import architecture.goldenboughs_lib.api.LcLevel
import architecture.goldenboughs_lib.util.LcLevelUtil
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object AbnormalitiesEntityTypes {
	@JvmField
	val REGISTRY: DeferredRegister<EntityType<*>> = Abnormalities.modRegister(BuiltInRegistries.ENTITY_TYPE)

	@JvmField
	val TRAINING_RABBITS: DeferredHolder<EntityType<*>, EntityType<TrainingRabbits>> = register(
		"training_rabbits",
		"训练兔兔",
		LcLevel.TETH,
		EntityType.Builder.of(::TrainingRabbits, MobCategory.MISC)
			.sized(0.625f, 1.375f)
			.eyeHeight(1f)
			.clientTrackingRange(8)
			.updateInterval(2)
			.canSpawnFarFromPlayer()
	)

	fun register(bus: IEventBus) {
		REGISTRY.register(bus)
		ProjectileEntityTypes.register(bus)
		OrdealsEntityTypes.register(bus)
	}

	private fun <I : Entity> register(
		name: String, zhName: String,
		lcLevel: LcLevel,
		sup: EntityType.Builder<I>
	): DeferredHolder<EntityType<*>, EntityType<I>> {
		return register(name, zhName, lcLevel) { sup.build(name) }
	}

	private fun <T : Entity> register(
		name: String, zhName: String,
		lcLevel: LcLevel,
		sup: Supplier<EntityType<T>>
	): DeferredHolder<EntityType<*>, EntityType<T>> {
		val holder = REGISTRY.register(name, sup)
		LcLevelUtil.lcLevel(lcLevel, holder)
		ZhCn.addI18nEntityTypeText(zhName, holder)
		return holder
	}
}

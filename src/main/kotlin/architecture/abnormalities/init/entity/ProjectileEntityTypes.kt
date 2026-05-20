package architecture.abnormalities.init.entity

import architecture.abnormalities.common.entity.ordeals.violet.FruitOfUnderstanding
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

object ProjectileEntityTypes {
	@JvmField
	val REGISTRY: DeferredRegister<EntityType<*>> = Abnormalities.modRegister(BuiltInRegistries.ENTITY_TYPE)

	@JvmField
	val FRUIT_OF_UNDERSTANDING_BULLET: DeferredHolder<EntityType<*>, EntityType<FruitOfUnderstanding.FruitBullet>> =
		register(
			"fruit_bullet",
			"“理解”",
			LcLevel.TETH,
			EntityType.Builder.of(FruitOfUnderstanding.FruitBullet::new, MobCategory.MISC)
				.sized(0.3f, 0.3f)
				.clientTrackingRange(4)
				.updateInterval(10)
		)

	fun init(bus: IEventBus) {
		REGISTRY.register(bus)
	}

	private fun <I : Entity> register(
		name: String, zhName: String, lcLevel: LcLevel, sup: EntityType.Builder<I>
	): DeferredHolder<EntityType<*>, EntityType<I>> {
		return register(name, zhName, lcLevel) { sup.build(name) }
	}

	private fun <T : Entity> register(
		name: String, zhName: String, lcLevel: LcLevel, sup: Supplier<EntityType<T>>
	): DeferredHolder<EntityType<*>, EntityType<T>> {
		val holder = REGISTRY.register<T>(name, sup)
		LcLevelUtil.lcLevel(lcLevel, holder)
		ZhCn.addI18nEntityTypeText(zhName, holder)
		return holder
	}
}

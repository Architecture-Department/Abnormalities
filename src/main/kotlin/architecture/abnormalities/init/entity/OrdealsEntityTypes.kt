package architecture.abnormalities.init.entity

import architecture.abnormalities.common.entity.ordeals.violet.FruitOfUnderstanding
import architecture.abnormalities.common.entity.ordeals.violet.GrantUsLove
import architecture.abnormalities.core.Abnormalities
import architecture.abnormalities.datagen.i18n.ZhCn
import architecture.goldenboughs_lib.api.LcLevel
import architecture.goldenboughs_lib.util.LcLevelUtil
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.block.Blocks
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object OrdealsEntityTypes {
	@JvmField
	val REGISTRY: DeferredRegister<EntityType<*>> = Abnormalities.modRegister(BuiltInRegistries.ENTITY_TYPE)

	//region 紫罗兰
	@JvmField
	val FRUIT_OF_UNDERSTANDING: DeferredHolder<EntityType<*>, EntityType<FruitOfUnderstanding>> = register(
		"fruit_of_understanding",
		"“理解的果实”",
		LcLevel.TETH,
		EntityType.Builder.of(::FruitOfUnderstanding, MobCategory.MISC)
			.sized(1.7f, 1.7f)
			.eyeHeight(1.0f)
			.clientTrackingRange(8)
			.updateInterval(3)
	)

	@JvmField
	val GRANT_US_LOVE: DeferredHolder<EntityType<*>, EntityType<GrantUsLove>> = register(
		"grant_us_love",
		"“请给我们爱！！！”",
		LcLevel.HE,
		EntityType.Builder.of(::GrantUsLove, MobCategory.MISC)
			.fireImmune()
			.immuneTo(Blocks.POWDER_SNOW)
			.canSpawnFarFromPlayer()
			.sized(2.0f, 5f)
			.eyeHeight(2.5f)
			.clientTrackingRange(8)
			.updateInterval(2)
	)
	//endregion

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
		val holder = REGISTRY.register(name, sup)
		LcLevelUtil.lcLevel(lcLevel, holder)
		ZhCn.addI18nEntityTypeText(zhName, holder)
		return holder
	}
}

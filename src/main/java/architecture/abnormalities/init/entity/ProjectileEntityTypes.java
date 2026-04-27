package architecture.abnormalities.init.entity;

import architecture.abnormalities.common.entity.ordeals.violet.FruitOfUnderstanding;
import architecture.abnormalities.core.Abnormalities;
import architecture.abnormalities.datagen.i18n.ZhCn;
import architecture.goldenboughs_lib.api.LcLevel;
import architecture.goldenboughs_lib.util.LcLevelUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ProjectileEntityTypes {
	public static final DeferredRegister<EntityType<?>> REGISTRY = Abnormalities.modRegister(BuiltInRegistries.ENTITY_TYPE);

	public static final DeferredHolder<EntityType<?>, EntityType<FruitOfUnderstanding.FruitBullet>> FRUIT_OF_UNDERSTANDING_BULLET = register(
		"fruit_bullet",
		"“理解”",
		LcLevel.TETH,
		EntityType.Builder.<FruitOfUnderstanding.FruitBullet>of(FruitOfUnderstanding.FruitBullet::new, MobCategory.MISC)
			.sized(0.3F, 0.3F)
			.clientTrackingRange(4)
			.updateInterval(10));

	static void init(IEventBus bus) {
		REGISTRY.register(bus);
	}

	private static <I extends Entity> DeferredHolder<EntityType<?>, EntityType<I>> register(
		String name, String zhName, LcLevel lcLevel, EntityType.Builder<I> sup
	) {
		return register(name, zhName, lcLevel, () -> sup.build(name));
	}

	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(
		String name, String zhName, LcLevel lcLevel, Supplier<EntityType<T>> sup
	) {
		DeferredHolder<EntityType<?>, EntityType<T>> holder = REGISTRY.register(name, sup);
		LcLevelUtil.lcLevel(lcLevel, holder);
		ZhCn.addI18nEntityTypeText(zhName, holder);
		return holder;
	}
}

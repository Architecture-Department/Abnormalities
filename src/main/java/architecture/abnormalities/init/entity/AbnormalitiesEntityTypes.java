package architecture.abnormalities.init.entity;

import architecture.abnormalities.common.entity.abnormalities.TrainingRabbits;
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

public final class AbnormalitiesEntityTypes {
	public static final DeferredRegister<EntityType<?>> REGISTRY = Abnormalities.modRegister(BuiltInRegistries.ENTITY_TYPE);

	public static final DeferredHolder<EntityType<?>, EntityType<TrainingRabbits>> TRAINING_RABBITS = register(
		"training_rabbits",
		"训练兔兔",
		LcLevel.TETH,
		EntityType.Builder.of(TrainingRabbits::new, MobCategory.MISC)
			.sized(0.625F, 1.375F)
			.eyeHeight(1F)
			.clientTrackingRange(8)
			.updateInterval(2)
			.canSpawnFarFromPlayer());

	public static void init(IEventBus bus) {
		REGISTRY.register(bus);
		ProjectileEntityTypes.init(bus);
		OrdealsEntityTypes.init(bus);
	}

	private static <I extends Entity> DeferredHolder<EntityType<?>, EntityType<I>> register(String name, String zhName,
	                                                                                        LcLevel lcLevel,
	                                                                                        EntityType.Builder<I> sup) {
		return register(name, zhName, lcLevel, () -> sup.build(name));
	}

	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, String zhName,
	                                                                                        LcLevel lcLevel,
	                                                                                        Supplier<EntityType<T>> sup) {
		DeferredHolder<EntityType<?>, EntityType<T>> holder = REGISTRY.register(name, sup);
		LcLevelUtil.lcLevel(lcLevel, holder);
		ZhCn.addI18nEntityTypeText(zhName, holder);
		return holder;
	}
}

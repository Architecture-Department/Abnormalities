package architecture.abnormalities.core;

import architecture.abnormalities.init.AbnormalitiesCreativeModeTabs;
import architecture.abnormalities.init.AbnormalitiesEntityDataSerializers;
import architecture.abnormalities.init.AbnormalitiesSoundEvents;
import architecture.abnormalities.init.AbnormalitiesSpawnEggItems;
import architecture.abnormalities.init.entity.AbnormalitiesEntityTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Mod(Abnormalities.ID)
public final class Abnormalities {

	public static final String ID = "abnormalities";
	public static final String NAME = "Abnormalities";
	public static final Logger LOGGER = LogManager.getLogger(ID);

	public Abnormalities(IEventBus modEventBus, ModContainer modContainer) {
		NeoForge.EVENT_BUS.register(this);
		AbnormalitiesEntityDataSerializers.REGISTRY.register(modEventBus);
		AbnormalitiesCreativeModeTabs.REGISTRY.register(modEventBus);
		AbnormalitiesSoundEvents.REGISTRY.register(modEventBus);
		AbnormalitiesEntityTypes.init(modEventBus);
		AbnormalitiesSpawnEggItems.REGISTRY.register(modEventBus);
	}

	@Contract("_ -> new")
	public static @NotNull ResourceLocation modRl(final String name) {
		return ResourceLocation.fromNamespaceAndPath(ID, name);
	}

	@Contract(pure = true)
	public static @NotNull String modRlText(final String name) {
		return ID + ":" + name;
	}

	public static <T> @NotNull DeferredRegister<T> modRegister(Registry<T> registry) {
		return DeferredRegister.create(registry, ID);
	}

	public static <T> @NotNull DeferredRegister<T> modRegister(ResourceKey<Registry<T>> registry) {
		return DeferredRegister.create(registry, ID);
	}

	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
		LOGGER.info("HELLO from server starting");
	}
}

package architecture.abnormalities.core

import architecture.abnormalities.init.AbnormalitiesCreativeModeTabs
import architecture.abnormalities.init.AbnormalitiesEntityDataSerializers
import architecture.abnormalities.init.AbnormalitiesSoundEvents
import architecture.abnormalities.init.AbnormalitiesSpawnEggItems
import architecture.abnormalities.init.entity.AbnormalitiesEntityTypes
import architecture.goldenboughs_lib.util.LibUtil.rlOf
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.server.ServerStartingEvent
import net.neoforged.neoforge.registries.DeferredRegister
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.jetbrains.annotations.Contract
import thedarkcolour.kotlinforforge.neoforge.forge.LOADING_CONTEXT
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(Abnormalities.ID)
@EventBusSubscriber(modid = Abnormalities.ID)
object Abnormalities {
	const val ID: String = "abnormalities"
	const val NAME: String = "Abnormalities"

	@JvmField
	val LOGGER: Logger = LogManager.getLogger(ID)

	init {
		val modContainer = LOADING_CONTEXT.activeContainer
		val modBus = MOD_BUS

		AbnormalitiesEntityDataSerializers.REGISTRY.register(modBus)
		AbnormalitiesCreativeModeTabs.REGISTRY.register(modBus)
		AbnormalitiesSoundEvents.REGISTRY.register(modBus)
		AbnormalitiesEntityTypes.init(modBus)
		AbnormalitiesSpawnEggItems.REGISTRY.register(modBus)
	}

	@SubscribeEvent
	fun onServerStarting(event: ServerStartingEvent) {
		LOGGER.info("HELLO from server starting")
	}

	@JvmStatic
	@Contract("_ -> new")
	fun modRl(name: String): ResourceLocation {
		return rlOf(ID, name)
	}

	@JvmStatic
	@Contract(pure = true)
	fun modRlText(name: String): String {
		return "$ID:$name"
	}

	@JvmStatic
	fun <T> modRegister(registry: Registry<T>): DeferredRegister<T> {
		return DeferredRegister.create<T>(registry, ID)
	}

	@JvmStatic
	fun <T> modRegister(registry: ResourceKey<Registry<T>>): DeferredRegister<T> {
		return DeferredRegister.create<T>(registry, ID)
	}
}

package architecture.abnormalities.core

import architecture.abnormalities.init.AbnormalitiesCreativeModeTabs
import architecture.abnormalities.init.AbnormalitiesEntityDataSerializers
import architecture.abnormalities.init.AbnormalitiesSoundEvents
import architecture.abnormalities.init.AbnormalitiesSpawnEggItems
import architecture.abnormalities.init.entity.AbnormalitiesEntityTypes
import architecture.abnormalities.util.AbnormalitiesUtil
import architecture.abnormalities.util.AbnormalitiesUtil.LOGGER
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.server.ServerStartingEvent
import thedarkcolour.kotlinforforge.neoforge.forge.LOADING_CONTEXT
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(AbnormalitiesUtil.ID)
@EventBusSubscriber(modid = AbnormalitiesUtil.ID)
object Abnormalities {
	@SubscribeEvent
	fun onServerStarting(event: ServerStartingEvent) {
		LOGGER.info("HELLO from server starting")
	}

	init {
		val modContainer = LOADING_CONTEXT.activeContainer
		val modBus = MOD_BUS

		AbnormalitiesEntityDataSerializers.REGISTRY.register(modBus)
		AbnormalitiesCreativeModeTabs.REGISTRY.register(modBus)
		AbnormalitiesSoundEvents.REGISTRY.register(modBus)
		AbnormalitiesEntityTypes.register(modBus)
		AbnormalitiesSpawnEggItems.REGISTRY.register(modBus)
	}
}

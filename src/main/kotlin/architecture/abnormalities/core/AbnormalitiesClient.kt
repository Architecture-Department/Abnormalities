package architecture.abnormalities.core

import net.minecraft.client.Minecraft
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.client.gui.ConfigurationScreen
import net.neoforged.neoforge.client.gui.IConfigScreenFactory
import thedarkcolour.kotlinforforge.neoforge.forge.LOADING_CONTEXT
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(value = Abnormalities.ID, dist = [Dist.CLIENT])
@EventBusSubscriber(modid = Abnormalities.ID, value = [Dist.CLIENT])
object AbnormalitiesClient {
	init {
		val modContainer = LOADING_CONTEXT.activeContainer
		val modBus = MOD_BUS

		modContainer.registerExtensionPoint(
			IConfigScreenFactory::class.java,
			IConfigScreenFactory(::ConfigurationScreen)
		)
	}

	@SubscribeEvent
	fun onClientSetup(event: FMLClientSetupEvent) {
		Abnormalities.LOGGER.info("HELLO FROM CLIENT SETUP")
		Abnormalities.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().user.name)
	}
}

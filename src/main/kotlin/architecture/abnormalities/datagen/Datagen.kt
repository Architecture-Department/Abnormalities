package architecture.abnormalities.datagen

import architecture.abnormalities.core.Abnormalities
import architecture.abnormalities.datagen.i18n.ZhCn
import architecture.abnormalities.datagen.tag.DatagenEntityTag
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.data.event.GatherDataEvent

/**
 * 数据生成主类
 */
@EventBusSubscriber(modid = Abnormalities.ID)
object Datagen {
	@SubscribeEvent
	fun gatherData(event: GatherDataEvent) {
		val generator = event.generator
		val output = generator.packOutput
		val completableFuture = event.lookupProvider
		val existingFileHelper = event.existingFileHelper

		// 服务端数据生成
		buildServer(event, generator, ModDatagenDatapackBuiltinEntries(output, completableFuture, RegistrySetBuilder()))
		buildServer(event, generator, DatagenSoundDefinitionsProvider(output, existingFileHelper))
		buildServer(event, generator, DatagenEntityTag(output, completableFuture, existingFileHelper))

		// 客户端数据生成
		buildClient(event, generator, ZhCn(output))
		buildClient(event, generator, DatagenItemModel(output, existingFileHelper))
	}

	private fun <T : DataProvider> buildClient(
		event: GatherDataEvent,
		generator: DataGenerator,
		provider: T
	): T {
		return generator.addProvider(event.includeClient(), provider)
	}

	private fun <T : DataProvider> buildServer(
		event: GatherDataEvent,
		generator: DataGenerator,
		provider: T
	): T {
		return generator.addProvider(event.includeServer(), provider)
	}
}

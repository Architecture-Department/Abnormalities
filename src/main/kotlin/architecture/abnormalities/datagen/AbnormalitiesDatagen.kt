package architecture.abnormalities.datagen

import architecture.abnormalities.datagen.i18n.ZhCn
import architecture.abnormalities.datagen.tag.DatagenEntityTag
import architecture.abnormalities.util.AbnormalitiesUtil
import architecture.goldenboughs_lib.util.datagen.buildClient
import architecture.goldenboughs_lib.util.datagen.buildServer
import net.minecraft.core.RegistrySetBuilder
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.data.event.GatherDataEvent

/**
 * 数据生成主类
 */
@EventBusSubscriber(modid = AbnormalitiesUtil.ID)
object AbnormalitiesDatagen {
	@SubscribeEvent
	fun gatherData(event: GatherDataEvent) {
		val generator = event.generator
		val output = generator.packOutput
		val completableFuture = event.lookupProvider
		val existingFileHelper = event.existingFileHelper

		// 服务端数据生成
		event.buildServer(ModDatagenDatapackBuiltinEntries(output, completableFuture, RegistrySetBuilder()))
		event.buildServer(DatagenSoundDefinitionsProvider(output, existingFileHelper))
		event.buildServer(DatagenEntityTag(output, completableFuture, existingFileHelper))

		// 客户端数据生成
		event.buildClient(ZhCn(output))
		event.buildClient(DatagenItemModel(output, existingFileHelper))
	}
}

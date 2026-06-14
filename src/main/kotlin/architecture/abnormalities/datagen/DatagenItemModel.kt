package architecture.abnormalities.datagen

import architecture.abnormalities.init.AbnormalitiesSpawnEggItems
import architecture.abnormalities.util.AbnormalitiesUtil
import architecture.goldenboughs_lib.util.datagen.ItemModelUtil.withExistingParent
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

/**
 * 物品模型数据生成器
 * 用于为模组中的物品生成对应的模型文件
 */
class DatagenItemModel(
	output: PackOutput,
	existingFileHelper: ExistingFileHelper
) : ItemModelProvider(output, AbnormalitiesUtil.ID, existingFileHelper) {

	override fun registerModels() {
		withExistingParent(pathPrefix = "item/spawn_egg/", registry = AbnormalitiesSpawnEggItems.REGISTRY)
	}
}

package architecture.abnormalities.datagen

import architecture.abnormalities.core.Abnormalities
import architecture.abnormalities.init.AbnormalitiesSpawnEggItems
import architecture.goldenboughs_lib.util.client.DatagenItemModelUtil.withExistingParent
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
) : ItemModelProvider(output, Abnormalities.ID, existingFileHelper) {

	override fun registerModels() {
		withExistingParent(AbnormalitiesSpawnEggItems.REGISTRY, "item/spawn_egg/")
	}
}

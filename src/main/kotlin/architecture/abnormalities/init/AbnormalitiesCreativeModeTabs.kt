package architecture.abnormalities.init

import architecture.abnormalities.core.Abnormalities
import architecture.abnormalities.datagen.i18n.ZhCn
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

/**
 * 创造模式物品栏
 */
object AbnormalitiesCreativeModeTabs {
	@JvmField
	val REGISTRY: DeferredRegister<CreativeModeTab> = Abnormalities.modRegister(BuiltInRegistries.CREATIVE_MODE_TAB)

	@JvmField
	val SPAWN_EGG: DeferredHolder<CreativeModeTab, CreativeModeTab> = register(
		"spwan_egg", "异想体 | 刷怪蛋"
	) { name, zhCn ->
		createCreativeModeTab(name, zhCn, { _, output ->
			addRegistryItem(AbnormalitiesSpawnEggItems.REGISTRY, output)
		}, Supplier {
			AbnormalitiesSpawnEggItems.GRANT_US_LOVE_SPAWN_EGG.get().defaultInstance
		})
	}

	private fun register(
		name: String,
		zhCn: String,
		builder: (String, String) -> CreativeModeTab.Builder
	): DeferredHolder<CreativeModeTab, CreativeModeTab> {
		return REGISTRY.register(name) { builder(name, zhCn).build() }
	}

	private fun createCreativeModeTab(
		name: String,
		zhCn: String,
		displayItemsGenerator: CreativeModeTab.DisplayItemsGenerator,
		icon: Supplier<ItemStack>,
		withTabsBefore: ResourceKey<CreativeModeTab>
	): CreativeModeTab.Builder {
		return createCreativeModeTab(name, zhCn, displayItemsGenerator, icon)
			.withTabsBefore(withTabsBefore)
	}

	private fun createCreativeModeTab(
		name: String,
		zhCn: String,
		displayItemsGenerator: CreativeModeTab.DisplayItemsGenerator,
		icon: Supplier<ItemStack>
	): CreativeModeTab.Builder {
		return createCreativeModeTab(name, zhCn, displayItemsGenerator)
			.icon(icon)
	}

	private fun createCreativeModeTab(
		name: String,
		zhCn: String,
		displayItemsGenerator: CreativeModeTab.DisplayItemsGenerator
	): CreativeModeTab.Builder {
		val key = "itemGroup.${Abnormalities.ID}.$name"
		ZhCn.addI18nText(zhCn, key)
		return CreativeModeTab.builder()
			.title(Component.translatable(key))
			.displayItems(displayItemsGenerator)
	}

	private fun addRegistryItem(registry: DeferredRegister.Items, output: CreativeModeTab.Output) {
		registry.entries.forEach { entry -> output.accept(entry.get()) }
	}
}

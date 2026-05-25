package architecture.abnormalities.init

import architecture.abnormalities.common.item.ModEggItem
import architecture.abnormalities.core.Abnormalities
import architecture.abnormalities.datagen.i18n.ZhCn
import architecture.abnormalities.init.entity.OrdealsEntityTypes
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.item.Item
import net.minecraft.world.item.SpawnEggItem
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier
import architecture.abnormalities.core.AbnormalitiesConstants

object AbnormalitiesSpawnEggItems {
	@JvmField
	val REGISTRY: DeferredRegister.Items = DeferredRegister.createItems(AbnormalitiesConstants.ID)

	@JvmField
	val GRANT_US_LOVE_SPAWN_EGG: DeferredItem<SpawnEggItem> = register(
		"grant_us_love_spawn_egg", "“请给我们爱！！！”刷怪蛋", OrdealsEntityTypes.GRANT_US_LOVE
	)

	@JvmField
	val FRUIT_OF_UNDERSTANDING_EGG: DeferredItem<SpawnEggItem> = register(
		"fruit_of_understanding_spawn_egg", "“理解的果实”刷怪蛋", OrdealsEntityTypes.FRUIT_OF_UNDERSTANDING
	)

	fun init(bus: IEventBus) {
		REGISTRY.register(bus)
	}

	private fun register(
		id: String,
		zhName: String,
		entityType: Supplier<out EntityType<out Mob>>
	): DeferredItem<SpawnEggItem> {
		return register(id, zhName, entityType, Item.Properties())
	}

	private fun register(
		id: String,
		zhName: String,
		entityType: Supplier<out EntityType<out Mob>>,
		properties: Item.Properties
	): DeferredItem<SpawnEggItem> {
		return register(id, zhName, { itemProperties -> ModEggItem(entityType, properties) }, properties)
	}

	private fun <I : SpawnEggItem> register(
		id: String,
		zhName: String,
		item: (Item.Properties) -> I,
		properties: Item.Properties
	): DeferredItem<I> {
		val deferredItem = REGISTRY.registerItem(id, item, properties)
		ZhCn.addI18nItemText(zhName, deferredItem)
		return deferredItem
	}
}

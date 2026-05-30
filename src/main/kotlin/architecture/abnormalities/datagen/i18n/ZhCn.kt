package architecture.abnormalities.datagen.i18n

import architecture.abnormalities.core.AbnormalitiesConstants
import architecture.abnormalities.init.tag.AbnormalitiesEntityTags
import architecture.goldenboughs_lib.datagen.i18n.DatagenI18n
import net.minecraft.data.PackOutput
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.neoforged.fml.loading.FMLEnvironment
import java.util.function.Supplier

class ZhCn(output: PackOutput) : DatagenI18n(output, AbnormalitiesConstants.ID, "zh_cn") {

	companion object {
		private val ITEMS: MutableMap<Supplier<out Item>, String> = HashMap()
		private val SOUND_EVENT: MutableMap<Supplier<out SoundEvent>, String> = HashMap()
		private val ENTITY: MutableMap<Supplier<out EntityType<*>>, String> = HashMap()
		private val MAP: MutableMap<String, String> = HashMap()

		fun addI18nText(zhCn: String, key: String) {
			if (!FMLEnvironment.production) {
				MAP[key] = zhCn
			}
		}

		fun addI18nEntityTypeText(zhName: String, supplier: Supplier<out EntityType<*>>) {
			if (!FMLEnvironment.production) {
				ENTITY[supplier] = zhName
			}
		}

		fun addI18nItemText(zhName: String, deferredItem: Supplier<out Item>) {
			if (!FMLEnvironment.production) {
				ITEMS[deferredItem] = zhName
			}
		}

		fun addI18nSoundEventText(zhName: String, supplier: Supplier<out SoundEvent>) {
			if (!FMLEnvironment.production) {
				SOUND_EVENT[supplier] = zhName
			}
		}
	}

	override fun addTranslations() {
		addPackDescription(AbnormalitiesConstants.ID, "异想体")
		addItemList(ITEMS)
		addEntityList(ENTITY)
		addSoundEventList(SOUND_EVENT)
		MAP.forEach { (key, value) -> add(key, value) }

		add(AbnormalitiesEntityTags.ABNORMALITIES, "异想体")
		add(AbnormalitiesEntityTags.ORDEALS, "考验")
		add(AbnormalitiesEntityTags.ORDEALS_VIOLET, "紫罗兰色的考验")
		add(AbnormalitiesEntityTags.ORDEALS_AMBER, "琥珀色的考验")
		add(AbnormalitiesEntityTags.ORDEALS_GREEN, "绿色的考验")
		add(AbnormalitiesEntityTags.ORDEALS_CRIMSON, "血色的考验")
		add(AbnormalitiesEntityTags.THE_SWEEPERS, "清道夫")
	}
}

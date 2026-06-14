package architecture.abnormalities.init

import architecture.abnormalities.datagen.i18n.ZhCn
import architecture.abnormalities.util.AbnormalitiesUtil
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvent
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object AbnormalitiesSoundEvents {
	@JvmField
	val REGISTRY: DeferredRegister<SoundEvent> = AbnormalitiesUtil.modRegister(BuiltInRegistries.SOUND_EVENT)

	@JvmField
	val VIOLET_NOON_DOWN: DeferredHolder<SoundEvent, SoundEvent> = registerForHolder(
		"violet_noon_down", "“请给我们爱！！！”：下砸", "entity.violet.grant_us_love.down"
	)

	@JvmField
	val VIOLET_NOON_ATK: DeferredHolder<SoundEvent, SoundEvent> = registerForHolder(
		"violet_noon_atk", "“请给我们爱！！！”：攻击", "entity.violet.grant_us_love.atk"
	)

	@JvmField
	val VIOLET_NOON_idle: DeferredHolder<SoundEvent, SoundEvent> = registerForHolder(
		"violet_noon_idle", "“请给我们爱！！！”：触手蠕动", "entity.violet.grant_us_love.idle"
	)

	@JvmField
	val VIOLET_NOON_DEATH: DeferredHolder<SoundEvent, SoundEvent> = registerForHolder(
		"violet_noon_death", "“请给我们爱！！！”：死亡", "entity.violet.grant_us_love.death"
	)

	@JvmField
	val VIOLET_DAWN_WALK: DeferredHolder<SoundEvent, SoundEvent> = registerForHolder(
		"violet_dawn_walk", "“紫罗兰黎明”：蠕动", "entity.violet.fruit_of_understanding.walk"
	)

	@JvmField
	val VIOLET_DAWN_SUICIDE: DeferredHolder<SoundEvent, SoundEvent> = registerForHolder(
		"violet_dawn_suicide", "“紫罗兰黎明”：自爆", "entity.violet.fruit_of_understanding.suicide"
	)

	@JvmField
	val VIOLET_DAWN_DEAD: DeferredHolder<SoundEvent, SoundEvent> = registerForHolder(
		"violet_dawn_dead", "“紫罗兰黎明”：死亡", "entity.violet.fruit_of_understanding.dead"
	)

	private fun registerForHolder(id: String, zhName: String, location: String): DeferredHolder<SoundEvent, SoundEvent> {
		val register =
			REGISTRY.register(id) { -> SoundEvent.createVariableRangeEvent(AbnormalitiesUtil.modRl(location)) }
		ZhCn.addI18nSoundEventText(zhName, register)
		return register
	}
}

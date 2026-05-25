package architecture.abnormalities.datagen

import architecture.abnormalities.core.Abnormalities
import architecture.abnormalities.init.AbnormalitiesSoundEvents
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.common.data.SoundDefinition
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider
import architecture.abnormalities.core.AbnormalitiesConstants

class DatagenSoundDefinitionsProvider(
	output: PackOutput,
	existingFileHelper: ExistingFileHelper
) : SoundDefinitionsProvider(output, AbnormalitiesConstants.ID, existingFileHelper) {

	companion object {
		@JvmStatic
		fun getSubtitle(soundEvent: SoundEvent): String {
			return getSubtitle(soundEvent.location)
		}

		@JvmStatic
		fun getSubtitle(location: ResourceLocation): String {
			return "sound." + location.toLanguageKey()
		}
	}

	override fun registerSounds() {
		add(AbnormalitiesSoundEvents.VIOLET_NOON_DOWN.value(), 0.5f, 0.5f, 1, 8)
		add(AbnormalitiesSoundEvents.VIOLET_NOON_ATK.value(), 0.5f, 0.5f, 1, 8)
		add(AbnormalitiesSoundEvents.VIOLET_NOON_idle.value(), 0.5f, 0.5f, 1, 8)
		add(AbnormalitiesSoundEvents.VIOLET_NOON_DEATH.value(), 0.5f, 0.5f, 1, 8)
		add(AbnormalitiesSoundEvents.VIOLET_DAWN_WALK.value(), 0.5f, 0.5f, 1, 8)
		add(AbnormalitiesSoundEvents.VIOLET_DAWN_SUICIDE.value(), 0.5f, 0.5f, 1, 8)
		add(AbnormalitiesSoundEvents.VIOLET_DAWN_DEAD.value(), 0.5f, 0.5f, 1, 8)
	}

	private fun add(soundEvent: SoundEvent, volume: Float, pitch: Float, weight: Int, attenuationDistance: Int) {
		add(soundEvent, 1, volume, pitch, weight, attenuationDistance)
	}

	private fun add(
		soundEvent: SoundEvent,
		amount: Int,
		volume: Float,
		pitch: Float,
		weight: Int,
		attenuationDistance: Int
	) {
		val location = soundEvent.location
		assert(amount > 0) { "sound : $location amount must be greater than 0" }
		val definition = SoundDefinition.definition()
		for (i in 0 until amount) {
			definition.with(
				SoundDefinition.Sound.sound(
					location.withSuffix(if (i > 0) i.toString() else ""),
					SoundDefinition.SoundType.SOUND
				)
					.volume(volume)
					.pitch(pitch)
					.weight(weight)
					.attenuationDistance(attenuationDistance)
			)
		}
		add(
			soundEvent, definition
				.subtitle(getSubtitle(location))
				.replace(true)
		)
	}
}

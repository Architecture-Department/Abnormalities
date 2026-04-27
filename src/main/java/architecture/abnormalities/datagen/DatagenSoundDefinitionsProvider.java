package architecture.abnormalities.datagen;

import architecture.abnormalities.core.Abnormalities;
import architecture.abnormalities.init.AbnormalitiesSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public final class DatagenSoundDefinitionsProvider extends SoundDefinitionsProvider {
	public DatagenSoundDefinitionsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, Abnormalities.ID, existingFileHelper);
	}

	public static @NotNull String getSubtitle(SoundEvent soundEvent) {
		return getSubtitle(soundEvent.getLocation());
	}

	public static @NotNull String getSubtitle(ResourceLocation location) {
		return "sound." + location.toLanguageKey();
	}

	@Override
	public void registerSounds() {
		add(AbnormalitiesSoundEvents.VIOLET_NOON_DOWN.value(), 0.5f, 0.5f, 1, 8);
		add(AbnormalitiesSoundEvents.VIOLET_NOON_ATK.value(), 0.5f, 0.5f, 1, 8);
		add(AbnormalitiesSoundEvents.VIOLET_NOON_idle.value(), 0.5f, 0.5f, 1, 8);
		add(AbnormalitiesSoundEvents.VIOLET_NOON_DEATH.value(), 0.5f, 0.5f, 1, 8);
		add(AbnormalitiesSoundEvents.VIOLET_DAWN_WALK.value(), 0.5f, 0.5f, 1, 8);
		add(AbnormalitiesSoundEvents.VIOLET_DAWN_SUICIDE.value(), 0.5f, 0.5f, 1, 8);
		add(AbnormalitiesSoundEvents.VIOLET_DAWN_DEAD.value(), 0.5f, 0.5f, 1, 8);
	}

	private void add(SoundEvent soundEvent, float volume, float pitch, int weight, int attenuationDistance) {
		add(soundEvent, 1, volume, pitch, weight, attenuationDistance);
	}

	private void add(SoundEvent soundEvent, int amount, float volume, float pitch, int weight, int attenuationDistance) {
		ResourceLocation location = soundEvent.getLocation();
		assert amount > 0 : "sund : " + location + " amount must be greater than 0";
		SoundDefinition definition = SoundDefinition.definition();
		for (int i = 0; i < amount; i++) {
			definition.with(SoundDefinition.Sound.sound(location.withSuffix(i > 0 ? String.valueOf(i) : ""), SoundDefinition.SoundType.SOUND)
				.volume(volume)
				.pitch(pitch)
				.weight(weight)
				.attenuationDistance(attenuationDistance)
			);
		}
		add(soundEvent, definition
			.subtitle(getSubtitle(location))
			.replace(true)
		);
	}
}
